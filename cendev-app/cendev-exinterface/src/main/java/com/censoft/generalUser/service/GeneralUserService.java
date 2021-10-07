package com.censoft.generalUser.service;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface GeneralUserService {

    public Map checkGeneralUser(Map map);
    public Integer checkUserEmail(Map map);
    public Integer checkUserPhone(Map map);
    public Integer checkUserName(Map map);
    //验证个人昵称的唯一性
    public Integer checkUserNickName(String string);

    public Map registerGeneralUser(Map map);

    Map generalUserLogin(Map map);

    Map updateGeneralUser(Map map);

    Map registeUserGroup(Map map);

    Map showUserStatusGroup(Map map);
}
