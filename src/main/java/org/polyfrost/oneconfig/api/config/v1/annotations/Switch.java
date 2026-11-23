package org.polyfrost.oneconfig.api.config.v1.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Minimal stand-in for OneConfig's {@code @Switch} option annotation. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Switch {
    String title();

    String description() default "";

    String icon() default "";

    String category() default "General";

    String subcategory() default "General";
}
