package com.censoft.userManage.fcenterLocal.mapper;

import com.censoft.userManage.fcenterLocal.domain.FcenterLocalInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 分中心用户地域信息Mapper接口
 *
 * @author cendev
 * @date 2020-11-19
 */
@Mapper
public interface FcenterLocalInfoMapper
{
    /**
     * 查询分中心用户地域信息
     *
     * @param id 分中心用户地域信息ID
     * @return 分中心用户地域信息
     */
    public FcenterLocalInfo selectFcenterLocalInfoById(String id);

    /**
     * 查询分中心用户地域信息列表
     *
     * @param fcenterLocalInfo 分中心用户地域信息
     * @return 分中心用户地域信息集合
     */
    public List<FcenterLocalInfo> selectFcenterLocalInfoList(FcenterLocalInfo fcenterLocalInfo);

    /**
     * 新增分中心用户地域信息
     *
     * @param fcenterLocalInfo 分中心用户地域信息
     * @return 结果
     */
    public int insertFcenterLocalInfo(FcenterLocalInfo fcenterLocalInfo);

    /**
     * 修改分中心用户地域信息
     *
     * @param fcenterLocalInfo 分中心用户地域信息
     * @return 结果
     */
    public int updateFcenterLocalInfo(FcenterLocalInfo fcenterLocalInfo);

    /**
     * 删除分中心用户地域信息
     *
     * @param id 分中心用户地域信息ID
     * @return 结果
     */
    public int deleteFcenterLocalInfoById(String id);

    /**
     * 批量删除分中心用户地域信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFcenterLocalInfoByIds(String[] ids);
}
