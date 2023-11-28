package com.yuwen.rtiweb.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Administrator
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {
    String value() default "Default";
}
