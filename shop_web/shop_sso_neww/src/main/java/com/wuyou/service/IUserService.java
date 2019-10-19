package com.wuyou.service;

import com.wuyou.entity.User;

/**
 * @author Forest
 * @Date 2019/10/18
 */
public interface IUserService {
    int register(User user);

    User getByUsername(String username);

    int updatePasswordByUsername(User user);
}
