package com.bbd.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Liuweibo
 * @version Id: Audience.java, v0.1 2017/10/20 Liuweibo Exp $$
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Audience {
}
    
    