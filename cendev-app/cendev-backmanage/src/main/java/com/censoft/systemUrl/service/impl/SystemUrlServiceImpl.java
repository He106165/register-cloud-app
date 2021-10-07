package com.censoft.systemUrl.service.impl;

import cn.hutool.core.util.IdUtil;
import com.censoft.common.core.text.Convert;
import com.censoft.common.utils.DateUtils;
import com.censoft.systemUrl.domain.SystemUrl;
import com.censoft.systemUrl.mapper.SystemUrlMapper;
import com.censoft.systemUrl.service.ISystemUrlService;
import com.censoft.userManage.personalUser.domain.PersonalUserInfo;
import com.censoft.userManage.personalUser.mapper.PersonalUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 系统地址Service业务层处理
 *
 * @author cendev
 * @date 2021-01-25
 */
@Service
public class SystemUrlServiceImpl implements ISystemUrlService {
    @Autowired
    private SystemUrlMapper systemUrlMapper;

    private PersonalUserInfoMapper personalUserInfoMapper;

    /**
     * 查询系统地址
     *
     * @param id 系统地址ID
     * @return 系统地址
     */
    @Override
    public SystemUrl selectSystemUrlById(String id) {
        return systemUrlMapper.selectSystemUrlById(id);
    }

    /**
     * 查询系统地址列表
     *
     * @param systemUrl 系统地址
     * @return 系统地址
     */
    @Override
    public List<SystemUrl> selectSystemUrlList(SystemUrl systemUrl) {
        return systemUrlMapper.selectSystemUrlList(systemUrl);
    }

    /**
     * 新增系统地址
     *
     * @param systemUrl 系统地址
     * @return 结果
     */
    @Override
    public int insertSystemUrl(SystemUrl systemUrl) {
        //版本号 创建时间 创建人 删除标记
        //版本号获取
        systemUrl.setVersion(0L);
        //获取删除标记(默认为0,证明没有删除,1代表已经删除)
        systemUrl.setDelFlag(0L);
        //创建时间
        systemUrl.setCreateTime(DateUtils.getNowDate());
        systemUrl.setId(IdUtil.fastSimpleUUID());



        return systemUrlMapper.insertSystemUrl(systemUrl);
    }


    /**
     * 修改系统地址
     *
     * @param systemUrl 系统地址
     * @return 结果
     */
    @Override
    public int updateSystemUrl(SystemUrl systemUrl) {
        //修改时间 版本号+1(是否相等,相等成功则加1)
        systemUrl.setUpdateTime(DateUtils.getNowDate());
        return systemUrlMapper.updateSystemUrl(systemUrl);
    }

    /**
     * 删除系统地址对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSystemUrlByIds(String ids) {
        //删除标记s
        //修改时间   修改人

        return systemUrlMapper.deleteSystemUrlByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除系统地址信息
     *
     * @param id 系统地址ID
     * @return 结果
     */
    public int deleteSystemUrlById(String id,SystemUrl systemUrl) {
        systemUrl.setUpdateTime(DateUtils.getNowDate());

        return systemUrlMapper.deleteSystemUrlById(id,systemUrl);
    }
}
