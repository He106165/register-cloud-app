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
 * @创建时间:2020-12-1509:58
 * @描述:使用策略模式验证手机号唯一
 */

@Component("phone")
public class VerifyPhoneOnlyImpl implements StrategyService {

    @Resource
    private OnlyVerifyMapper onlyVerifyMapper;

    @Override
    public ResultUtil verifyStrategy(WechatBindParam wechatBindParam) {
        //对于个人用户手机号唯一性的校验
        if (StringUtils.isNotBlank(wechatBindParam.getPhone())) {
            int phoneRow = onlyVerifyMapper.countPhone(wechatBindParam.getPhone());
            if (phoneRow > 0) {
                return ResultUtil.result(ResultStatusEnum.S_10008.getStatus(), ResultStatusEnum.S_10008.getMassage());
            }
            //邮箱不为空  手机号、证件号为空
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10014.getStatus(), ResultStatusEnum.S_10014.getMassage());
        }
        return ResultUtil.success();
    }
}
