package com.censoft.userManage.gridUser.mapper;

import com.censoft.userManage.gridUser.domain.GridUserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 网格用户信息Mapper接口
 *
 * @author cendev
 * @date 2020-12-29
 */
@Mapper
public interface GridUserInfoMapper
{
    /**
     * 查询网格用户信息
     *
     * @param id 网格用户信息ID
     * @return 网格用户信息
     */
    public GridUserInfo selectGridUserInfoById(Long id);

    /**
     * 查询网格用户信息列表
     *
     * @param gridUserInfo 网格用户信息
     * @return 网格用户信息集合
     */
    public List<GridUserInfo> selectGridUserInfoList(GridUserInfo gridUserInfo);

    /**
     * 新增网格用户信息
     *
     * @param gridUserInfo 网格用户信息
     * @return 结果
     */
    public int insertGridUserInfo(GridUserInfo gridUserInfo);

    /**
     * 修改网格用户信息
     *
     * @param gridUserInfo 网格用户信息
     * @return 结果
     */
    public int updateGridUserInfo(GridUserInfo gridUserInfo);

    /**
     * 删除网格用户信息
     *
     * @param id 网格用户信息ID
     * @return 结果
     */
    public int deleteGridUserInfoById(Long id);

    /**
     * 批量删除网格用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGridUserInfoByIds(String[] ids);

    /**
    * @Description 网格用户登陆,根据用户名
    * @Parm
    * @return
    **/
    public GridUserInfo getGridUserByName(String name);

    /**
     * @Description 业务系统之间传递网格用户数据
     * @Parm
     * @return
     **/
    public Map getGridUserById(Long id);
}
