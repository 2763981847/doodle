package com.oreki.spring.bean.service.impl;

import com.oreki.spring.bean.service.UserService;
import com.oreki.spring.core.annotation.Service;

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
