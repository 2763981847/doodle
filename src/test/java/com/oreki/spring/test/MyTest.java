package com.oreki.spring.test;

import com.oreki.spring.bean.config.SpringConfig;
import com.oreki.spring.bean.controller.UserController;
import com.oreki.spring.ioc.ApplicationContext;
import com.oreki.spring.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

/**
 * @author : Fu QiuJie
 * @since : 2022/12/22 9:52
 */
@Slf4j
public class MyTest {

    @Test
    public void testIoc() {
        ApplicationContext applicationContext = new ApplicationContext(SpringConfig.class);
        UserController userController = applicationContext.getBean(UserController.class);
        userController.login();
    }

    @Test
    public void test() throws IOException {
        URL url = ClassUtil.getClassLoader().getResource("com/oreki/core");
        if (url == null) {
            log.warn("url为空");
            return;
        }
        log.info(url.getFile());
        File file = new File(url.getFile());
        Path basePath = file.toPath();
        log.info(basePath.toString());
        Files.walk(basePath)
                .filter(path -> path.toFile().getName().endsWith(".class"))
                .forEach(path -> log.info("com.oreki.core" + path.toString().replace(basePath.toString(), "").replace('\\', '.').replace(".class", "")));
    }

    @Test
    public void testGetClasses() {
        Set<Class<?>> classes = ClassUtil.getClasses("com.oreki.core");
        for (Class<?> aClass : classes) {
            log.info(aClass.getName());
        }
    }
}
