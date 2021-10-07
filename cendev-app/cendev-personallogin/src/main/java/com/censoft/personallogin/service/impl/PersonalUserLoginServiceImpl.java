package com.censoft.personallogin.service.impl;

import com.censoft.common.core.text.Convert;
import com.censoft.common.utils.DateUtils;
import com.censoft.common.utils.security.Md5Utils;
import com.censoft.personallogin.domain.PersonalUserInfo;
import com.censoft.personallogin.domain.PersonalUserLogin;
import com.censoft.personallogin.mapper.PersonalUserLoginMapper;
import com.censoft.personallogin.service.IPersonalUserLoginService;
import com.censoft.personallogin.service.ToRedisService;
import com.censoft.personallogin.util.loginUtil;
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


    @Autowired
    private ToRedisService toRedisService;
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

    @Override
    public PersonalUserInfo login(PersonalUserLogin personalUserLogin,String username) {

        PersonalUserInfo login = toRedisService.getPersonUserInfo(username);
        if(login==null){
            //执行登陆方法，获取个人登陆信息,如果取不到，更换其他方式
            login = personalUserLoginMapper.login(personalUserLogin);
            //为空，优先查询用户名，一下方式不查询邮箱，（邮箱一定会被上次过滤掉）
            if(login==null){
                loginUtil.reset(personalUserLogin);
                personalUserLogin.setName(username);
                personalUserLogin.setLoginType("");
                login = personalUserLoginMapper.login(personalUserLogin);
            }
            //还为空，查询手机号
            if(login==null){
                loginUtil.reset(personalUserLogin);
                personalUserLogin.setPhone(username);
                personalUserLogin.setLoginType("ph");
                login = personalUserLoginMapper.login(personalUserLogin);
            }
            //为空，身份证号
            if(login==null){
                loginUtil.reset(personalUserLogin);
                personalUserLogin.setIdnumber(username);
                personalUserLogin.setLoginType("");
                login = personalUserLoginMapper.login(personalUserLogin);
            }
            return login==null ? null : login;
        }
        return login;



    }

    /**
     * 登陆成功后，查询个人信息
     * **/
    @Override
    public PersonalUserInfo getUserInfoById(String id){
        return personalUserLoginMapper.getUserInfoById(id);
    }

    /**
     * @param userId
     * @param userType
     * @param passWord
     * @return
     * @Description 用户修改密码(个人 / 机构)
     * @Parm
     */
    @Override
    public int updateUserPwd(String userId, String userType, String passWord,String updateBy) {
        return personalUserLoginMapper.updateUserPwd(userId,userType,passWord,updateBy);
    }

    public static void main(String[] args) {

        System.out.println(Md5Utils.hash("123321"));
    }

}
