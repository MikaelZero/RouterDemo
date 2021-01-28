package net.mikaelzero.compile;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.sun.tools.javac.code.Symbol;

import net.mikaelzero.interfaces.Const;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


public abstract class BaseProcessor extends AbstractProcessor {

    protected Filer filer;
    protected Types types;
    protected Elements elements;
    Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        types = processingEnvironment.getTypeUtils();
        elements = processingEnvironment.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager = processingEnv.getMessager();
        return false;
    }

    /**
     * 从字符串获取TypeElement对象
     */
    public TypeElement typeElement(String className) {
        return elements.getTypeElement(className);
    }

    /**
     * 从字符串获取TypeMirror对象
     */
    public TypeMirror typeMirror(String className) {
        return typeElement(className).asType();
    }

    /**
     * 从字符串获取ClassName对象
     */
    public ClassName className(String className) {
        return ClassName.get(typeElement(className));
    }


    public boolean isSubType(TypeMirror type, String className) {
        return type != null && types.isSubtype(type, typeMirror(className));
    }

    public boolean isSubType(Element element, String className) {
        return element != null && isSubType(element.asType(), className);
    }

    /**
     * 非抽象类
     */
    public boolean isConcreteType(Element element) {
        return element instanceof TypeElement && !element.getModifiers().contains(
                Modifier.ABSTRACT);
    }

    /**
     * 非抽象子类
     */
    public boolean isConcreteSubType(Element element, String className) {
        return isConcreteType(element) && isSubType(element, className);
    }

    public boolean isActivity(Element element) {
        return isConcreteSubType(element, Const.ACTIVITY_CLASS);
    }

    /**
     * 创建Handler。格式：<code>"com.demo.TestActivity"</code> 或 <code>new TestHandler()</code>
     */
    public CodeBlock buildHandler(boolean isActivity, Symbol.ClassSymbol cls) {
        CodeBlock.Builder b = CodeBlock.builder();
        if (isActivity) {
            b.add("$S", cls.className());
        } else {
            b.add("new $T()", cls);
        }
        return b.build();
    }

    public void buildHandlerInitClass(CodeBlock code, String genClassName, String handlerClassName, String interfaceName) {
        MethodSpec methodSpec = MethodSpec.methodBuilder(Const.INIT_METHOD)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(className(handlerClassName), "handler")
                .addCode(code)
                .build();
        TypeSpec typeSpec = TypeSpec.classBuilder(genClassName)
                .addSuperinterface(className(interfaceName))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodSpec)
                .build();
        try {
            JavaFile.builder(Const.GEN_PKG, typeSpec)
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
