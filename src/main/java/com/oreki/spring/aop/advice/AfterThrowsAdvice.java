package com.oreki.spring.aop.advice;

import java.lang.reflect.Method;

/**
 * @author Fu Qiujie
 * @since 2022/12/26
 */
public interface AfterThrowsAdvice extends Advice{
    /**
     *  抛出异常后方法
     * @param clazz
     * @param method
     * @param args
     * @param e
     */
    void afterThrowing(Class<?> clazz, Method method, Object[] args, Throwable e);
}
