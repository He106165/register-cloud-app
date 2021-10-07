package com.censoft.userregister.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SpzMapper {

    String querySpz(String s);
}
