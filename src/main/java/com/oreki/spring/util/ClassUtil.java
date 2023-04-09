package com.oreki.spring.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : Fu QiuJie
 * @since : 2022/12/20 15:01
 */
@Slf4j
public class ClassUtil {
    /**
     * file 形式 url 协议
     */
    public static final String FILE_PROTOCOL = "file";

    /**
     * jar 形式 url 协议
     */
    public static final String JAR_PROTOCOL = "jar";

    /**
     * 获取classLoader
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获取class
     */
    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("load class error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 实例化class
     */
    public static Object newInstance(String className) {
        try {
            Class<?> clazz = loadClass(className);
            return clazz.newInstance();
        } catch (Exception e) {
            log.error("newInstance error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 实例化class
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            log.error("newInstance error", e);
            return null;
        }
    }

    /**
     * 获取包下的所有class集合
     */
    public static Set<Class<?>> getClasses(String basePackage) {
        URL url = getClassLoader().getResource(basePackage.replace('.', '/'));
        if (null == url) {
            throw new RuntimeException("无法获取项目路径文件");
        }
        if (url.getProtocol().equalsIgnoreCase(FILE_PROTOCOL)) {
            File file = new File(url.getFile());
            Path basePath = file.toPath();
            try {
                return Files.walk(basePath)
                        .filter(path -> path.toFile().getName().endsWith(".class"))
                        .map(path -> getClassByPath(path, basePath, basePackage))
                        .collect(Collectors.toSet());
            } catch (IOException e) {
                log.error("getClasses error", e);
                throw new RuntimeException(e);
            }
        } else if (url.getProtocol().equalsIgnoreCase(JAR_PROTOCOL)) {
            //todo
            return null;
        }
        return null;
    }

    /**
     * 根据classPath获取class对象
     */
    public static Class<?> getClassByPath(Path classPath, Path basePath, String basePackage) {
        String className = basePackage + classPath.toString()
                .replace(basePath.toString(), "").replace('\\', '.').replace(".class", "");
        return loadClass(className);
    }

    /**
     * 设置类的属性值
     */
    public static void setField(Field field, Object target, Object value) {
        setField(field, target, value, true);
    }


    /**
     * 设置类的属性值
     */
    public static void setField(Field field, Object target, Object value, boolean accessible) {
        field.setAccessible(accessible);
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            log.error("setField error", e);
            throw new RuntimeException(e);
        }
    }

    public static boolean isAnyAnnotationPresent(Class<?> clazz, Class<? extends Annotation>... annotationClass) {
        for (Class<? extends Annotation> annotation : annotationClass) {
            if (clazz.isAnnotationPresent(annotation)) {
                return true;
            }
        }
        return false;
    }


}
