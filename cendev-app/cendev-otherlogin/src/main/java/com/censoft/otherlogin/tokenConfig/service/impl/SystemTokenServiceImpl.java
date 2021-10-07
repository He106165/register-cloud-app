package com.censoft.otherlogin.tokenConfig.service.impl;

import com.censoft.common.core.text.Convert;
import com.censoft.otherlogin.tokenConfig.domain.SystemToken;
import com.censoft.otherlogin.tokenConfig.mapper.SystemTokenMapper;
import com.censoft.otherlogin.tokenConfig.service.ISystemTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 令牌管理Service业务层处理
 *
 * @author cendev
 * @date 2020-10-20
 */
@Service
@PropertySource({"classpath:application.properties"})
public class SystemTokenServiceImpl implements ISystemTokenService {
    @Autowired
    private SystemTokenMapper systemTokenMapper;



    /**
     * 查询令牌管理
     *
     * @param id 令牌管理ID
     * @return 令牌管理
     */
    @Override
    public SystemToken selectSystemTokenById(String id) {

        return systemTokenMapper.selectSystemTokenById(id);
    }

    /**
     * 查询令牌管理列表
     *
     * @param systemToken 令牌管理
     * @return 令牌管理
     */
    @Override
    public List<SystemToken> selectSystemTokenList(SystemToken systemToken) {
        return systemTokenMapper.selectSystemTokenList(systemToken);
    }

    /**
     * 新增令牌管理
     *
     * @param systemToken 令牌管理
     * @return 结果
     */
    @Override
    public int insertSystemToken(SystemToken systemToken) {
        return systemTokenMapper.insertSystemToken(systemToken);
    }

    /**
     * 修改令牌管理
     *
     * @param systemToken 令牌管理
     * @return 结果
     */
    @Override
    public int updateSystemToken(SystemToken systemToken) {
        return systemTokenMapper.updateSystemToken(systemToken);
    }

    /**
     * 删除令牌管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSystemTokenByIds(String ids) {
        return systemTokenMapper.deleteSystemTokenByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除令牌管理信息
     *
     * @param id 令牌管理ID
     * @return 结果
     */
    public int deleteSystemTokenById(String id) {
        return systemTokenMapper.deleteSystemTokenById(id);
    }

}
