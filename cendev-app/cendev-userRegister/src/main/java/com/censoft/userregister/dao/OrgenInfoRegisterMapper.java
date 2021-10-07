package com.censoft.userregister.dao;

import com.censoft.userregister.domain.OrgenUserInfo;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

/**
 *@创建人:wqgeng
 *@创建时间:2020-11-2111:11
 *@描述:机构注册mapper
 */
@Mapper
public interface OrgenInfoRegisterMapper {

    int addSaveOrgenUserInfo(OrgenUserInfo orgenUserInfo);

    List<Map<String,String>> queryPerManType();

    List<Map<String,String>> queryDepermentCode();

    List<Map<String,String>> queryConsulateInfo();

    List<Map<String,String>> queryperManLine();
}
