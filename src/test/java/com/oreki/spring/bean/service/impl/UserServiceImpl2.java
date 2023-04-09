package com.oreki.spring.bean.service.impl;

import com.oreki.spring.core.annotation.Service;

/**
 * @author Fu Qiujie
 * @since 2023/4/9
 */
@Service
public class UserServiceImpl2 extends UserServiceImpl {
    @Override
    public void login() {
        System.out.println("-----用户登录中-2----");
    }
}
