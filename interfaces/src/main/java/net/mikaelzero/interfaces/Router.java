package net.mikaelzero.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: MikaelZero
 * @CreateDate: 1/26/21 4:25 PM
 * @Description:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Router {
    String path();
}
