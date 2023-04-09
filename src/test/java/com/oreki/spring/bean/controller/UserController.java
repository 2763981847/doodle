package com.oreki.spring.bean.controller;

import com.oreki.spring.bean.service.UserService;
import com.oreki.spring.core.annotation.Controller;
import com.oreki.spring.ioc.annotation.Autowired;
import com.oreki.spring.ioc.annotation.Qualifier;

/**
 * @author : Fu QiuJie
 * @since : 2022/12/22 16:31
 */
@Controller
public class UserController {
    @Autowired
    @Qualifier("UserServiceImpl2")
    private UserService userService;

    public void login() {
        userService.login();
    }
}
