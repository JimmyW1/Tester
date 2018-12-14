package com.test.logic.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by CuncheW1 on 2017/8/8.
 */

/**
 * 用于兼容李凯的中行测试程序,后续请使用CaseAttributes注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestDetailCaseAnnotation {
    int id();
    String itemDetailName() default "";
    String resDialogTitle() default "";
    String APIName() default "";
}
