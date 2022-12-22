package com.oreki.bean.service.impl;

import com.oreki.bean.service.UserService;
import com.oreki.core.annotation.Component;
import com.oreki.core.annotation.Service;

/**
 * @author : Fu QiuJie
 * @since : 2022/12/22 16:33
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void login() {
        System.out.println("-----用户登录中----");
    }
}
