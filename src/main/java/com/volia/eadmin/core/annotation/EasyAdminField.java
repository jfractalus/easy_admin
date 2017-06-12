package com.volia.eadmin.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EasyAdminField {
    String humanName() default "";
    boolean richText() default false;
    boolean visible() default true;
    boolean editable() default true;
}
