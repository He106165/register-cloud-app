package com.censoft.demo.service;

import com.censoft.demo.pojo.User;

import java.util.List;

public interface UserService {


    List<User> selectAll();

    User getOne(Integer id);

    void add(User user);

    void update(User user);

    void delete(Integer id);
}


