package com.censoft.tokenConfig.mapper;

import com.censoft.tokenConfig.domain.SystemToken;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 令牌管理Mapper接口
 *
 * @author cendev
 * @date 2020-10-20
 */
@Mapper
public interface SystemTokenMapper {
    /**
     * 查询令牌管理
     *
     * @param id 令牌管理ID
     * @return 令牌管理
     */
    public SystemToken selectSystemTokenById(String id);

    /**
     * 查询令牌管理列表
     *
     * @param systemToken 令牌管理
     * @return 令牌管理集合
     */
    public List<SystemToken> selectSystemTokenList(SystemToken systemToken);

    /**
     * 新增令牌管理
     *
     * @param systemToken 令牌管理
     * @return 结果
     */
    public int insertSystemToken(SystemToken systemToken);

    /**
     * 修改令牌管理
     *
     * @param systemToken 令牌管理
     * @return 结果
     */
    public int updateSystemToken(SystemToken systemToken);

    /**
     * 删除令牌管理
     *
     * @param id 令牌管理ID
     * @return 结果
     */
    public int deleteSystemTokenById(String id);

    /**
     * 批量删除令牌管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSystemTokenByIds(String[] ids);
}
