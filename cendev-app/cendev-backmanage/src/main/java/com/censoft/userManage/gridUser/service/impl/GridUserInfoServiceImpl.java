package com.censoft.userManage.gridUser.service.impl;

import com.censoft.common.core.text.Convert;
import com.censoft.common.utils.DateUtils;
import com.censoft.userManage.gridUser.domain.GridUserInfo;
import com.censoft.userManage.gridUser.mapper.GridUserInfoMapper;
import com.censoft.userManage.gridUser.service.IGridUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 网格用户信息Service业务层处理
 *
 * @author cendev
 * @date 2020-12-29
 */
@Service
public class GridUserInfoServiceImpl implements IGridUserInfoService
{
    @Autowired
    private GridUserInfoMapper gridUserInfoMapper;

    /**
     * 查询网格用户信息
     *
     * @param id 网格用户信息ID
     * @return 网格用户信息
     */
    @Override
    public GridUserInfo selectGridUserInfoById(Long id)
    {
        return gridUserInfoMapper.selectGridUserInfoById(id);
    }

    /**
     * 查询网格用户信息列表
     *
     * @param gridUserInfo 网格用户信息
     * @return 网格用户信息
     */
    @Override
    public List<GridUserInfo> selectGridUserInfoList(GridUserInfo gridUserInfo)
    {
        return gridUserInfoMapper.selectGridUserInfoList(gridUserInfo);
    }

    /**
     * 新增网格用户信息
     *
     * @param gridUserInfo 网格用户信息
     * @return 结果
     */
    @Override
    public int insertGridUserInfo(GridUserInfo gridUserInfo)
    {
        gridUserInfo.setCreateTime(DateUtils.getNowDate());
        return gridUserInfoMapper.insertGridUserInfo(gridUserInfo);
    }

    /**
     * 修改网格用户信息
     *
     * @param gridUserInfo 网格用户信息
     * @return 结果
     */
    @Override
    public int updateGridUserInfo(GridUserInfo gridUserInfo)
    {
        gridUserInfo.setUpdateTime(DateUtils.getNowDate());
        return gridUserInfoMapper.updateGridUserInfo(gridUserInfo);
    }

    /**
     * 删除网格用户信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGridUserInfoByIds(String ids)
    {
        return gridUserInfoMapper.deleteGridUserInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除网格用户信息信息
     *
     * @param id 网格用户信息ID
     * @return 结果
     */
    public int deleteGridUserInfoById(Long id)
    {
        return gridUserInfoMapper.deleteGridUserInfoById(id);
    }

    /**
     * @Description 网格用户登陆,根据用户名
     * @Parm
     * @return
     **/
    @Override
    public GridUserInfo getGridUserByName(String name){
        return gridUserInfoMapper.getGridUserByName(name);
    }


    /**
     * @Description 业务系统之间传递网格用户数据
     * @Parm
     * @return
     **/
    @Override
    public Map getGridUserById(Long id){
        return gridUserInfoMapper.getGridUserById(id);
    }
}
