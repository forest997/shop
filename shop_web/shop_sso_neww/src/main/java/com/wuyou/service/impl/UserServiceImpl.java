package com.wuyou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuyou.dao.UserMapper;
import com.wuyou.entity.User;
import com.wuyou.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Forest
 * @Date 2019/10/18
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int register(User user) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", user.getUsername());
        int result = userMapper.selectCount(queryWrapper);
        if(result>0){
            return -1;
        }
        return userMapper.insert(user);
    }

    @Override
    public User getByUsername(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public int updatePasswordByUsername(User user) {
        return userMapper.updateById(user);
    }

}
