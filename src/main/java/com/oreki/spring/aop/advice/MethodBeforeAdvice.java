package com.oreki.spring.aop.advice;

import java.lang.reflect.Method;

/**
 * @author Fu Qiujie
 * @since 2022/12/26
 */
public interface MethodBeforeAdvice extends Advice {
    /**
     * 前置方法
     *
     * @param clazz
     * @param method
     * @param args
     * @throws Throwable
     */
    void before(Class<?> clazz, Method method, Object[] args) throws Throwable;
}
