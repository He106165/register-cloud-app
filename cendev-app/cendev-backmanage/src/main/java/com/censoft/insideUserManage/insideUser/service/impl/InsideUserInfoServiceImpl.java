package com.censoft.insideUserManage.insideUser.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.censoft.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.censoft.insideUserManage.insideUser.mapper.InsideUserInfoMapper;
import com.censoft.insideUserManage.insideUser.domain.InsideUserInfo;
import com.censoft.insideUserManage.insideUser.service.IInsideUserInfoService;
import com.censoft.common.core.text.Convert;

/**
 * 内部用户Service业务层处理
 *
 * @author cendev
 * @date 2020-10-16
 */
@Service
public class InsideUserInfoServiceImpl implements IInsideUserInfoService {
    @Autowired
    private InsideUserInfoMapper insideUserInfoMapper;

    /**
     * 查询内部用户
     *
     * @param insideUserId 内部用户ID
     * @return 内部用户
     */
    @Override
    public InsideUserInfo selectInsideUserInfoById(String insideUserId) {
        return insideUserInfoMapper.selectInsideUserInfoById(insideUserId);
    }

    /**
     * 查询内部用户列表
     *
     * @param insideUserInfo 内部用户
     * @return 内部用户
     */
    @Override
    public List<InsideUserInfo> selectInsideUserInfoList(InsideUserInfo insideUserInfo) {
        return insideUserInfoMapper.selectInsideUserInfoList(insideUserInfo);
    }

    /**
     * 新增内部用户
     *
     * @param insideUserInfo 内部用户
     * @return 结果
     */
    @Override
    public int insertInsideUserInfo(InsideUserInfo insideUserInfo) {
        insideUserInfo.setInsideUserId(IdUtil.fastSimpleUUID());
        insideUserInfo.setCreateTime(DateUtils.getNowDate());
        return insideUserInfoMapper.insertInsideUserInfo(insideUserInfo);
    }

    /**
     * 修改内部用户
     *
     * @param insideUserInfo 内部用户
     * @return 结果
     */
    @Override
    public int updateInsideUserInfo(InsideUserInfo insideUserInfo) {
        insideUserInfo.setUpdateTime(DateUtils.getNowDate());
        return insideUserInfoMapper.updateInsideUserInfo(insideUserInfo);
    }

    /**
     * 删除内部用户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteInsideUserInfoByIds(String ids) {
        return insideUserInfoMapper.deleteInsideUserInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除内部用户信息
     *
     * @param insideUserId 内部用户ID
     * @return 结果
     */
    public int deleteInsideUserInfoById(String insideUserId) {
        return insideUserInfoMapper.deleteInsideUserInfoById(insideUserId);
    }


    /**
     *  根据用户名获取第一条数据 （登陆）
     * */
    @Override
    public InsideUserInfo getUserByName(String userName){
        return insideUserInfoMapper.getUserByName(userName);
    }
}
