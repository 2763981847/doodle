package com.oreki.spring.aop.advice;

import java.lang.reflect.Method;

/**
 * @author Fu Qiujie
 * @since 2022/12/26
 */
public interface AfterReturningAdvice extends Advice {
    /**
     * 返回后方法
     *
     * @param clazz
     * @param returnValue
     * @param method
     * @param args
     * @throws Throwable
     */
    void afterReturning(Class<?> clazz, Object returnValue, Method method, Object[] args) throws Throwable;
}
