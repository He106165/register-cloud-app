package com.censoft.otherlogin.Alipay.mapper;

import com.censoft.otherlogin.Alipay.domain.AlipayUserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 支付宝信息Mapper接口
 *
 * @author cendev
 * @date 2020-10-21
 */
@Mapper
public interface AlipayUserInfoMapper
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
     * 删除支付宝信息
     *
     * @param id 支付宝信息ID
     * @return 结果
     */
    public int deleteAlipayUserInfoById(String id);

    /**
     * 批量删除支付宝信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAlipayUserInfoByIds(String[] ids);

    List<Map> AlipayUserLogin(Map map);

    Integer insertAlipayUser(AlipayUserInfo alipayUser);


    Integer insertPersionalUserLogin(Map map);

    Integer insertPersionalUserInfo(Map map);

    public boolean sendMsg(String phone);

}
