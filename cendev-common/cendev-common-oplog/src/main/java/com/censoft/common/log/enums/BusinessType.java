package com.censoft.common.log.enums;

/**
 * 业务操作类型
 * 
 * @author censoft
 */
public enum BusinessType
{
    /**
     * 查询
     * */
    QUERY,

    /**
     * 登陆
     * */
    LOGIN,

    /**
     * 退出
     * */
    LOINGOUT,

    /**
     * 其它
     */
    OTHER,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 清空
     */
    CLEAN,
}
