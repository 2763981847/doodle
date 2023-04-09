package com.oreki.spring.core;


import com.oreki.spring.core.annotation.Component;
import com.oreki.spring.core.annotation.Controller;
import com.oreki.spring.core.annotation.Repository;
import com.oreki.spring.core.annotation.Service;
import com.oreki.spring.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author : Fu QiuJie
 * @since : 2022/12/20 15:45
 */
@Slf4j
public class BeanContainer {
    private final Map<Class<?>, Object> beanMap;

    /**
     * 私有构造方法，保证单例
     */
    private BeanContainer() {
        beanMap = new ConcurrentHashMap<>();
    }

    /**
     * 根据类型获取bean实例
     */
    public <T> T getBean(Class<T> clazz) {
        if (null == clazz) {
            return null;
        }
        return clazz.cast(beanMap.get(clazz));
    }

    /**
     * 添加bean实例到beanMap
     */
    public <T> void addBean(Class<T> clazz, T bean) {
        beanMap.put(clazz, bean);
    }

    /**
     * bean 数量
     */
    public int size() {
        return beanMap.size();
    }

    /**
     * 移除一个 Bean 实例
     */
    public void removeBean(Class<?> clazz) {
        beanMap.remove(clazz);
    }

    /**
     * 获取所有 Bean 的 Class 集合
     */
    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * 加载包下的bean
     */
    public void loadBeans(String basePackage) {
        Set<Class<?>> classSet = ClassUtil.getClasses(basePackage);
        if (classSet == null) {
            throw new RuntimeException("load bean error");
        }
        classSet.stream()
                .filter(clazz -> ClassUtil.isAnyAnnotationPresent(clazz, Component.class, Controller.class, Service.class, Repository.class))
                .forEach(clazz -> beanMap.put(clazz, ClassUtil.newInstance(clazz)));
    }

    /**
     * 获取有特定注解的bean的Class集合
     */
    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        return beanMap.keySet().stream()
                .filter(clazz -> clazz.isAnnotationPresent(annotation))
                .collect(Collectors.toSet());
    }

    /**
     * 获取子类或实现类的bean的Class集合
     */
    public Set<Class<?>> getClassesBySuper(Class<?> superClazz) {
        if (null == superClazz) {
            return Collections.emptySet();
        }
        return beanMap.keySet().stream()
                .filter(clazz -> superClazz.isAssignableFrom(clazz) && !clazz.equals(superClazz))
                .collect(Collectors.toSet());
    }


    /**
     * 对外暴露获取实例方法
     */
    public static BeanContainer getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * 使用静态内部类的方法实现单例
     */
    private static class InstanceHolder {
        private static final BeanContainer INSTANCE = new BeanContainer();
    }

}
