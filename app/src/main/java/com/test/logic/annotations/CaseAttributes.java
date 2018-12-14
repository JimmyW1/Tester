package com.test.logic.annotations;

/**
 * Created by CuncheW1 on 2017/8/8.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * visableLevel:
 * 此注解使用在测试case函数之上，标注此case的显示等级，然后应用可以根据测试配置显示等级。
 * 等级分为1-10级，数字越小等级越高越容易被显示出来。
 * 没有添加此注解的case函数默认等级为10即最低等级
 * 比如：
 * 当前设置中显示等级为5，则case函数上注解VisableLevel为1-5的case将能被显示或者执行，注解为6-10的将不被显示或者执行。
 *
 * isSupportAutoTest:
 * 此注解使用在测试case函数之上，表示支持自动测试，此case的布尔类型返回值将被视为case执行的结果
 * 没有标注此注解表示不支持自动测试
 *
 * description:
 * case描述，比如测试case目的，预期结果等。
 * 也可以不描述通过在程序中调用BaseModule的getDescriptionTv.show*方法动态显示想描述的类容；
 * 也可以两种方式并用，并用结果为先显示description设置的内容然后在这基础上增加getDescriptionTV.show*方法显示的类容;
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CaseAttributes {
    int visableLevel() default 10;
    boolean isSupportAutoTest() default false;
    String description() default "";
    int descriptRid() default -1;
}
