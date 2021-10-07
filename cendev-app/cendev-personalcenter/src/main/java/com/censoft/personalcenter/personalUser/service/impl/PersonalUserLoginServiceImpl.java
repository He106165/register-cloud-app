package com.censoft.personalcenter.personalUser.service.impl;

import com.censoft.common.core.text.Convert;
import com.censoft.common.utils.DateUtils;
import com.censoft.personalcenter.personalUser.domain.PersonalUserLogin;
import com.censoft.personalcenter.personalUser.mapper.PersonalUserLoginMapper;
import com.censoft.personalcenter.personalUser.service.IPersonalUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 个人用户登录Service业务层处理
 *
 * @author cendev
 * @date 2020-10-21
 */
@Service
public class PersonalUserLoginServiceImpl implements IPersonalUserLoginService
{
    @Autowired
    private PersonalUserLoginMapper personalUserLoginMapper;

    /**
     * 查询个人用户登录
     *
     * @param id 个人用户登录ID
     * @return 个人用户登录
     */
    @Override
    public PersonalUserLogin selectPersonalUserLoginById(String id)
    {
        return personalUserLoginMapper.selectPersonalUserLoginById(id);
    }

    /**
     * 查询个人用户登录列表
     *
     * @param personalUserLogin 个人用户登录
     * @return 个人用户登录
     */
    @Override
    public List<PersonalUserLogin> selectPersonalUserLoginList(PersonalUserLogin personalUserLogin)
    {
        return personalUserLoginMapper.selectPersonalUserLoginList(personalUserLogin);
    }

    /**
     * 新增个人用户登录
     *
     * @param personalUserLogin 个人用户登录
     * @return 结果
     */
    @Override
    public int insertPersonalUserLogin(PersonalUserLogin personalUserLogin)
    {
        personalUserLogin.setCreateTime(DateUtils.getNowDate());
        return personalUserLoginMapper.insertPersonalUserLogin(personalUserLogin);
    }

    /**
     * 修改个人用户登录
     *
     * @param personalUserLogin 个人用户登录
     * @return 结果
     */
    @Override
    public int updatePersonalUserLogin(PersonalUserLogin personalUserLogin)
    {
        personalUserLogin.setUpdateTime(DateUtils.getNowDate());
        return personalUserLoginMapper.updatePersonalUserLogin(personalUserLogin);
    }

    /**
     * 删除个人用户登录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePersonalUserLoginByIds(String ids)
    {
        return personalUserLoginMapper.deletePersonalUserLoginByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除个人用户登录信息
     *
     * @param id 个人用户登录ID
     * @return 结果
     */
    public int deletePersonalUserLoginById(String id)
    {
        return personalUserLoginMapper.deletePersonalUserLoginById(id);
    }

    /**
     * @Description 修改联系方式
     * @Parm
     * @return
     **/
    @Override
    public int updateLoginupdataContentWay(PersonalUserLogin personalUserLogin){
        return personalUserLoginMapper.updateLoginupdataContentWay(personalUserLogin);
    }

}
