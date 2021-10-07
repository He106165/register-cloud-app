package com.censoft.personallogin.service;

import com.censoft.personallogin.domain.PersonalUserInfo;
import com.censoft.personallogin.domain.PersonalUserLogin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 个人用户登录Service接口
 *
 * @author cendev
 * @date 2020-10-21
 */
@Service
public interface IPersonalUserLoginService
{
    /**
     * 查询个人用户登录
     *
     * @param id 个人用户登录ID
     * @return 个人用户登录
     */
    public PersonalUserLogin selectPersonalUserLoginById(String id);

    /**
     * 查询个人用户登录列表
     *
     * @param personalUserLogin 个人用户登录
     * @return 个人用户登录集合
     */
    public List<PersonalUserLogin> selectPersonalUserLoginList(PersonalUserLogin personalUserLogin);

    /**
     * 新增个人用户登录
     *
     * @param personalUserLogin 个人用户登录
     * @return 结果
     */
    public int insertPersonalUserLogin(PersonalUserLogin personalUserLogin);

    /**
     * 修改个人用户登录
     *
     * @param personalUserLogin 个人用户登录
     * @return 结果
     */
    public int updatePersonalUserLogin(PersonalUserLogin personalUserLogin);

    /**
     * 批量删除个人用户登录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePersonalUserLoginByIds(String ids);

    /**
     * 删除个人用户登录信息
     *
     * @param id 个人用户登录ID
     * @return 结果
     */
    public int deletePersonalUserLoginById(String id);

    /**
     * 个人用户登陆
     * */
    public PersonalUserInfo login(PersonalUserLogin personalUserLogin,String name);

    /**
     * 登陆成功后，查询个人信息
     * **/
    public PersonalUserInfo getUserInfoById(String id);


    /**
    * @Description 用户修改密码(个人/机构)
    * @Parm
    * @return
    **/
    public int updateUserPwd(@Param("userId") String userId, @Param("userType") String userType, @Param("passWord") String passWord,@Param("updateBy")String updataBy);
}
