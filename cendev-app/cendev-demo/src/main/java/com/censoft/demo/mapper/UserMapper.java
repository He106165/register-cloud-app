package com.censoft.demo.mapper;

import com.censoft.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface UserMapper {

   void add(User user);
    void update(User user);
    void delete(Integer id);
    List<User> selectAll();

    User getOne(Integer id);
}
