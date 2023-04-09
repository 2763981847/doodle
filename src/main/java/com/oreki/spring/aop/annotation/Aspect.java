package com.oreki.spring.aop.annotation;

import java.lang.annotation.*;

/**
 * @author Fu Qiujie
 * @since 2022/12/26
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 目标代理类的范围
     */
    Class<? extends Annotation> target();
}
