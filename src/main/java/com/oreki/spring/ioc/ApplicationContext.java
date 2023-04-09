package com.oreki.spring.ioc;

import com.oreki.spring.core.BeanContainer;
import com.oreki.spring.core.annotation.ComponentScan;
import com.oreki.spring.ioc.annotation.Autowired;
import com.oreki.spring.ioc.annotation.Qualifier;
import com.oreki.spring.util.ClassUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author : Fu QiuJie
 * @since : 2022/12/22 15:58
 */
public class ApplicationContext {
    private final BeanContainer beanContainer;

    public ApplicationContext(String basePackage) {
        this.beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans(basePackage);
        doIoc();
    }


    public ApplicationContext(Class<?> configClass) {
        this(configClass.getDeclaredAnnotation(ComponentScan.class).value());
    }

    public <T> T getBean(Class<T> clazz) {
        return beanContainer.getBean(clazz);
    }

    private void doIoc() {
        Set<Class<?>> classSet = beanContainer.getClasses();
        classSet.forEach(clazz -> {
            Object TargetBean = beanContainer.getBean(clazz);
            Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Autowired.class))
                    .forEach(field -> {
                        Object fieldValue = getFieldValue(field);
                        ClassUtil.setField(field, TargetBean, fieldValue);
                    });
        });
    }

    private Object getFieldValue(Field field) {
        Class<?> fieldType = field.getType();
        Object fieldValue = beanContainer.getBean(fieldType);
        //找不到Bean,尝试找子类和实现类的Bean
        if (fieldValue == null) {
            Stream<Class<?>> implStream = beanContainer.getClassesBySuper(fieldType).stream();
            if (field.isAnnotationPresent(Qualifier.class)) {
                String beanName = field.getAnnotation(Qualifier.class).value();
                implStream = implStream.filter(clazz -> clazz.getSimpleName().equals(beanName));
            }
            Class<?> implClass = implStream.findFirst().orElse(null);
            fieldValue = beanContainer.getBean(implClass);
        }
        return fieldValue;
    }

}
