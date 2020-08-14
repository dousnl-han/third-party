package com.dousnl.annontion;

import java.lang.annotation.*;

/**
 * TODO
 *
 * @version 1.0
 * @author: hanliangliang
 * @date: 2020/7/9 15:44
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EsIndex {

    String value() default "create_index";
}
