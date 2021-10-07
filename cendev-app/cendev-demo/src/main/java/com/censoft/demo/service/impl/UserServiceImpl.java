package com.censoft.demo.service.impl;

import com.censoft.demo.mapper.UserMapper;
import com.censoft.demo.pojo.User;
import com.censoft.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public User getOne(Integer id) {
        return userMapper.getOne(id);
    }

    @Override
    public void add(User user) {
        userMapper.add(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }
}
