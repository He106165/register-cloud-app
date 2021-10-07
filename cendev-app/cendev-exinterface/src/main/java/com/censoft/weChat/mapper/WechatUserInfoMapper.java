package com.censoft.weChat.mapper;

import com.censoft.weChat.domain.PersonalUserInfo;
import com.censoft.weChat.domain.WechatUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 微信信息Mapper接口
 *
 * @author cendev
 * @date 2020-10-21
 */
@Mapper
public interface WechatUserInfoMapper
{
    /**
     * 查询微信信息
     *
     * @param id 微信信息ID
     * @return 微信信息
     */
    public WechatUserInfo selectWechatUserInfoById(String id);

    /**
     * 查询微信信息列表
     *
     * @param wechatUserInfo 微信信息
     * @return 微信信息集合
     */
    public List<WechatUserInfo> selectWechatUserInfoList(WechatUserInfo wechatUserInfo);

    /**
     * 新增微信信息
     *
     * @param wechatUserInfo 微信信息
     * @return 结果
     */
    public int insertWechatUserInfo(WechatUserInfo wechatUserInfo);

    /**
     * 修改微信信息
     *
     * @param wechatUserInfo 微信信息
     * @return 结果
     */
    public int updateWechatUserInfo(WechatUserInfo wechatUserInfo);

    /**
     * 删除微信信息
     *
     * @param id 微信信息ID
     * @return 结果
     */
    public int deleteWechatUserInfoById(String id);

    /**
     * 批量删除微信信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWechatUserInfoByIds(String[] ids);

    /**
     * @Description 个人用户登陆方法（前提是获取到用户Id）
     * @Parm
     * @return
     **/
    public PersonalUserInfo personallogin(@Param("userId") String userId);
    /**
     * @Description 个人用户登陆方法（前提是获取到用户Id）
     * @Parm
     * @return
     **/

    public WechatUserInfo selectWechatUserInfoByIdAndUserType(@Param("id")String id,@Param("userType")String userType);

}
