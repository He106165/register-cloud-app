package com.censoft.weChat.service.impl;

import com.censoft.common.utils.StringUtils;
import com.censoft.util.ResultStatusEnum;
import com.censoft.util.ResultUtil;
import com.censoft.weChat.domain.WechatBindParam;
import com.censoft.weChat.mapper.OnlyVerifyMapper;
import com.censoft.weChat.service.StrategyService;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @创建人:wqgeng
 * @创建时间:2020-12-1513:25
 * @描述:使用策略模式校验(国内企业)的邮箱唯一性
 */
@Component("hostpersonEmail")
public class VerifyhostpersionEmailOnlyImpl implements StrategyService {
    @Resource
    private OnlyVerifyMapper onlyVerifyMapper;

    @Override
    public ResultUtil verifyStrategy(WechatBindParam wechatBindParam) {
        //对于机构用户 邮箱的唯一性的校验
        if (StringUtils.isNotBlank(wechatBindParam.getHostPersonEmail())) {
            int countEmailCodeRow = onlyVerifyMapper.countEmailCode(wechatBindParam.getHostPersonEmail());
            if (countEmailCodeRow > 0) {
                return ResultUtil.result(ResultStatusEnum.S_10012.getStatus(), ResultStatusEnum.S_10012.getMassage());
            }
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10014.getStatus(), ResultStatusEnum.S_10014.getMassage());
        }
        return ResultUtil.success();
    }
}
