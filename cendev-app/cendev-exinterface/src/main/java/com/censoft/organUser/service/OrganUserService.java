package com.censoft.organUser.service;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrganUserService {


    public Map registerOrganUser(Map map);

    Map organUserLogin(Map map);

    Map updateOrganUser(Map map);

}
