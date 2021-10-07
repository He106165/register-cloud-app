package com.censoft.otherlogin.orgenUser.service.impl;

import com.censoft.common.core.text.Convert;
import com.censoft.common.utils.DateUtils;
import com.censoft.otherlogin.orgenUser.domain.OrgenUserInfo;
import com.censoft.otherlogin.orgenUser.domain.OrgenUserLogin;
import com.censoft.otherlogin.orgenUser.mapper.OrgenUserLoginMapper;
import com.censoft.otherlogin.orgenUser.service.IOrgenUserLoginService;
import com.censoft.otherlogin.orgenUser.service.ToRedisOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构用户登录Service业务层处理
 *
 * @author cendev
 * @date 2020-10-21
 */
@Service
public class OrgenUserLoginServiceImpl implements IOrgenUserLoginService
{
    @Autowired
    private OrgenUserLoginMapper orgenUserLoginMapper;

    @Autowired
    private ToRedisOrgService toRedisOrgService;
    /**
     * 查询机构用户登录
     *
     * @param id 机构用户登录ID
     * @return 机构用户登录
     */
    @Override
    public OrgenUserLogin selectOrgenUserLoginById(String id)
    {
        return orgenUserLoginMapper.selectOrgenUserLoginById(id);
    }

    /**
     * 查询机构用户登录列表
     *
     * @param orgenUserLogin 机构用户登录
     * @return 机构用户登录
     */
    @Override
    public List<OrgenUserLogin> selectOrgenUserLoginList(OrgenUserLogin orgenUserLogin)
    {
        return orgenUserLoginMapper.selectOrgenUserLoginList(orgenUserLogin);
    }

    /**
     * 新增机构用户登录
     *
     * @param orgenUserLogin 机构用户登录
     * @return 结果
     */
    @Override
    public int insertOrgenUserLogin(OrgenUserLogin orgenUserLogin)
    {
        orgenUserLogin.setCreateTime(DateUtils.getNowDate());
        return orgenUserLoginMapper.insertOrgenUserLogin(orgenUserLogin);
    }

    /**
     * 修改机构用户登录
     *
     * @param orgenUserLogin 机构用户登录
     * @return 结果
     */
    @Override
    public int updateOrgenUserLogin(OrgenUserLogin orgenUserLogin)
    {
        orgenUserLogin.setUpdateTime(DateUtils.getNowDate());
        return orgenUserLoginMapper.updateOrgenUserLogin(orgenUserLogin);
    }

    /**
     * 删除机构用户登录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOrgenUserLoginByIds(String ids)
    {
        return orgenUserLoginMapper.deleteOrgenUserLoginByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除机构用户登录信息
     *
     * @param id 机构用户登录ID
     * @return 结果
     */
    public int deleteOrgenUserLoginById(String id)
    {
        return orgenUserLoginMapper.deleteOrgenUserLoginById(id);
    }

    /**
     * 登陆方法
     * 获取第一条
     * */
    @Override
    public OrgenUserInfo login (String username,String deptCode){
        OrgenUserInfo userInfo = toRedisOrgService.getUserInfo(username+"##"+deptCode);
        if ( userInfo==null ) {
            OrgenUserLogin orgenUserLogin = checkLoginMethod(username);
            orgenUserLogin.setDepermentCode(deptCode);
            userInfo = orgenUserLoginMapper.login(orgenUserLogin);
        }
        // 兼容用户名方式登录，后续可能会删除
        if ( userInfo == null ) {
            OrgenUserLogin orgenUserLogin = new OrgenUserLogin();
            orgenUserLogin.setDepermentCode(deptCode);
            orgenUserLogin.setName(username);
            userInfo = orgenUserLoginMapper.login(orgenUserLogin);
        }
        return userInfo;
    }
    /**
     * 判断登陆方式
     * 支持联系人邮箱、统一信用代码、用户名
     * */
    public OrgenUserLogin checkLoginMethod(String username){
        OrgenUserLogin orgenUserLogin = new OrgenUserLogin();
        if (username.indexOf("@") > 0) {
            orgenUserLogin.setHostPersionEmail(username);
        }
        else {
            orgenUserLogin.setUnifiedsocialcreditcode(username);
        }
        //删除了
//        else {
//            orgenUserLogin.setName(username);
//        }
        return orgenUserLogin;
    }


    private boolean isValid(String businessCode) {
        if ((businessCode.equals("")) || businessCode.length() != 18) {
            return false;
        }
        String baseCode = "0123456789ABCDEFGHJKLMNPQRTUWXY";
        char[] baseCodeArray = baseCode.toCharArray();
        Map<Character, Integer> codes = new HashMap<Character, Integer>();
        for (int i = 0; i < baseCode.length(); i++) {
            codes.put(baseCodeArray[i], i);
        }
        char[] businessCodeArray = businessCode.toCharArray();
        Character check = businessCodeArray[17];
        if (baseCode.indexOf(check) == -1) {
            return false;
        }
        int[] wi = { 1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28 };
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            Character key = businessCodeArray[i];
            if (baseCode.indexOf(key) == -1) {
                return false;
            }
            sum += (codes.get(key) * wi[i]);
        }
        int value = 31 - sum % 31;
        return value == codes.get(check);
    }

}
