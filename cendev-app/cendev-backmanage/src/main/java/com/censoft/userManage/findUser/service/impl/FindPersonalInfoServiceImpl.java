package com.censoft.userManage.findUser.service.impl;

import com.censoft.Util.DesNewUtils;
import com.censoft.Util.ResultStatusEnum;
import com.censoft.userManage.personalUser.domain.ResultUtil;
import java.util.Date;
import java.util.List;
import com.censoft.common.utils.DateUtils;
import javafx.scene.control.IndexRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.censoft.userManage.findUser.mapper.FindPersonalInfoMapper;
import com.censoft.userManage.findUser.domain.FindPersonalInfo;
import com.censoft.userManage.findUser.service.IFindPersonalInfoService;
import com.censoft.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 找回个人用户信息Service业务层处理
 *
 * @author cendev
 * @date 2020-10-20
 */
@Service
public class FindPersonalInfoServiceImpl implements IFindPersonalInfoService {
    @Autowired
    private FindPersonalInfoMapper findPersonalInfoMapper;

    /**
     * 查询找回个人用户信息
     *
     * @param id 找回个人用户信息ID
     * @return 找回个人用户信息
     */
    @Override
    public FindPersonalInfo selectFindPersonalInfoById(String id) {
        return findPersonalInfoMapper.selectFindPersonalInfoById(id);
    }

    /**
     * 查询找回个人用户信息列表
     *
     * @param findPersonalInfo 找回个人用户信息
     * @return 找回个人用户信息
     */
    @Override
    public List<FindPersonalInfo> selectFindPersonalInfoList(FindPersonalInfo findPersonalInfo) {
        return findPersonalInfoMapper.selectFindPersonalInfoList(findPersonalInfo);
    }

    /**
     * 新增找回个人用户信息
     *
     * @param findPersonalInfo 找回个人用户信息
     * @return 结果
     */
    @Override
    public int insertFindPersonalInfo(FindPersonalInfo findPersonalInfo) {
        findPersonalInfo.setCreateTime(DateUtils.getNowDate());
        return findPersonalInfoMapper.insertFindPersonalInfo(findPersonalInfo);
    }

    /**
     * 修改找回个人用户信息
     *
     * @param findPersonalInfo 找回个人用户信息
     * @return 结果
     */
    @Override
    public int updateFindPersonalInfo(FindPersonalInfo findPersonalInfo) {
        findPersonalInfo.setUpdateTime(DateUtils.getNowDate());
        return findPersonalInfoMapper.updateFindPersonalInfo(findPersonalInfo);
    }

    /**
     * 删除找回个人用户信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFindPersonalInfoByIds(String ids) {
        return findPersonalInfoMapper.deleteFindPersonalInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除找回个人用户信息信息
     *
     * @param id 找回个人用户信息ID
     * @return 结果
     */
    public int deleteFindPersonalInfoById(String id) {
        return findPersonalInfoMapper.deleteFindPersonalInfoById(id);
    }

    /*
    人工找回个人信息
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ResultUtil findUser(FindPersonalInfo findPersonalInfo) {
        //根据姓名 证件号 查询info表里是否有该用户信息
        if (findPersonalInfoMapper.selectUserInfo(findPersonalInfo)) {
            findPersonalInfo.setUpdateTime(DateUtils.getNowDate());
            int row = findPersonalInfoMapper.updatePersonalUserInfo(findPersonalInfo);
            if (row > 0) {
                //查询人工找回表中 用户设置的密码
//                String findPassWord = findPersonalInfoMapper.selectFindPassword(findPersonalInfo);
//                findPersonalInfo.setPassword(findPassWord);
//                修改个人用户登录表密码
                int s = 0;
                try {
                    int rw = findPersonalInfoMapper.updatePersonalUserLogin(findPersonalInfo);
                    s = findPersonalInfoMapper.updateFindStatus(findPersonalInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (s >= 1) {
                } else {
                    return ResultUtil.result(ResultStatusEnum.S_10039.getStatus(), ResultStatusEnum.S_10039.getMassage());
                }
            } else {
                return ResultUtil.result(ResultStatusEnum.S_10038.getStatus(), ResultStatusEnum.S_10038.getMassage());
            }
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10036.getStatus(), ResultStatusEnum.S_10036.getMassage());
        }
        return ResultUtil.success();
    }

    //向驳回表中添加驳回信息 并将状态改为 2
    @Override
    public ResultUtil inserBohui(FindPersonalInfo findPersonalInfo) {
        findPersonalInfo.setCreateTime(DateUtils.getNowDate());
        int row = findPersonalInfoMapper.inserBohui(findPersonalInfo);
        return ResultUtil.success(row);
    }


}
