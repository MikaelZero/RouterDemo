package net.mikaelzero.compile;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.sun.tools.javac.code.Symbol;

import net.mikaelzero.interfaces.Const;
import net.mikaelzero.interfaces.Router;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedAnnotationTypes("net.mikaelzero.interfaces.Router")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class UriProcessor extends BaseProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        super.process(annotations, roundEnv);
        if (annotations == null || annotations.isEmpty()) {
            return false;
        }
        CodeBlock.Builder builder = CodeBlock.builder();
        for (Element element : roundEnv.getElementsAnnotatedWith(Router.class)) {
            if (!(element instanceof Symbol.ClassSymbol)) {
                continue;
            }
            boolean isActivity = isActivity(element);

            if (!isActivity) {
                continue;
            }

            Symbol.ClassSymbol cls = (Symbol.ClassSymbol) element;
            Router uri = cls.getAnnotation(Router.class);
            if (uri == null) {
                continue;
            }
            CodeBlock handler = buildHandler(isActivity, cls);

            String path = uri.path();
            builder.addStatement("handler.register($S, $L)", path, handler);
        }
        //由于随机生成类名，需要根据接口去找对应实现类，代码量较多，作为演示，取一个固定的名字
        buildHandlerInitClass(builder.build(), "UriAnnotationInitImpl", Const.URI_ANNOTATION_HANDLER_CLASS, Const.URI_ANNOTATION_INIT_CLASS);
        return true;
    }
}