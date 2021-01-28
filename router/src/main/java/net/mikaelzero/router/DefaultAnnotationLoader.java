package net.mikaelzero.router;

import java.util.List;
import java.util.ServiceLoader;

/**
 * 使用ServiceLoader加载注解配置
 * <p>
 * Created by jzj on 2018/4/28.
 */
public class DefaultAnnotationLoader implements AnnotationLoader {

    public static final AnnotationLoader INSTANCE = new DefaultAnnotationLoader();

    @Override
    public <T extends UriHandler> void load(T handler, Class<? extends AnnotationInit<T>> initClass) {
//        List<? extends AnnotationInit<T>> services = getAllServices(initClass);
//        for (AnnotationInit<T> service : services) {
//            service.init(handler);
//        }
    }
}
