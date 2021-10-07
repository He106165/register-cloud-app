package com.censoft.otherlogin.Alipay.service;

import com.censoft.common.core.domain.R;
import com.censoft.otherlogin.Alipay.domain.AlipayUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 支付宝信息Service接口
 *
 * @author cendev
 * @date 2020-10-21
 */
@Service
public interface IAlipayUserInfoService
{
    /**
     * 查询支付宝信息
     *
     * @param id 支付宝信息ID
     * @return 支付宝信息
     */
    public AlipayUserInfo selectAlipayUserInfoById(String id);

    /**
     * 查询支付宝信息列表
     *
     * @param alipayUserInfo 支付宝信息
     * @return 支付宝信息集合
     */
    public List<AlipayUserInfo> selectAlipayUserInfoList(AlipayUserInfo alipayUserInfo);

    /**
     * 新增支付宝信息
     *
     * @param alipayUserInfo 支付宝信息
     * @return 结果
     */
    public int insertAlipayUserInfo(AlipayUserInfo alipayUserInfo);

    /**
     * 修改支付宝信息
     *
     * @param alipayUserInfo 支付宝信息
     * @return 结果
     */
    public int updateAlipayUserInfo(AlipayUserInfo alipayUserInfo);

    /**
     * 批量删除支付宝信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAlipayUserInfoByIds(String ids);

    /**
     * 删除支付宝信息信息
     *
     * @param id 支付宝信息ID
     * @return 结果
     */
    public int deleteAlipayUserInfoById(String id);


    String AlipayLogin(Map map, HttpServletResponse response, RedirectAttributes attr ,int proInfoTimeOut);

    R insertAlipayUser(Map map, HttpServletResponse response);

    String bindAlipayInfo(Map map, HttpServletResponse response, RedirectAttributes attr);


    public boolean sendMsg(String phone);
}
