package com.oreki.bean.controller;

import com.oreki.bean.service.UserService;
import com.oreki.core.annotation.Component;
import com.oreki.core.annotation.Controller;
import com.oreki.ioc.annotation.Autowired;

/**
 * @author : Fu QiuJie
 * @since : 2022/12/22 16:31
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    public void login() {
        userService.login();
    }
}
