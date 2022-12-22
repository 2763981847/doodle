package com.oreki.ioc;

import com.oreki.core.BeanContainer;
import com.oreki.core.annotation.ComponentScan;
import com.oreki.ioc.annotation.Autowired;
import com.oreki.util.ClassUtil;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

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
        this.beanContainer = BeanContainer.getInstance();
        ComponentScan componentScan = configClass.getDeclaredAnnotation(ComponentScan.class);
        beanContainer.loadBeans(componentScan.value());
        doIoc();
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
                        Class<?> fieldType = field.getType();
                        Object fieldValue = getClassInstance(fieldType);
                        ClassUtil.setField(field, TargetBean, fieldValue);
                    });
        });
    }

    private <T> T getClassInstance(Class<T> fieldType) {
        return Optional.ofNullable(beanContainer.getBean(fieldType))
                .orElseGet(() -> {
                    Class<?> implClass = beanContainer.getClassesBySuper(fieldType).stream()
                            .findFirst().orElse(null);
                    return fieldType.cast(beanContainer.getBean(implClass));
                });
    }
}
