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
 * @创建时间:2020-12-1513:17
 * @描述:使用策略模式验证社会信用码唯一
 */
@Component("unifiedsocialcreditcode")
public class VerifyUnifiedsocialcreditcodeOnlyImpl implements StrategyService {
    @Resource
    private OnlyVerifyMapper onlyVerifyMapper;

    @Override
    public ResultUtil verifyStrategy(WechatBindParam wechatBindParam) {
        //对于机构用户 社会信用码的唯一性的校验
        if (StringUtils.isNotBlank(wechatBindParam.getUnifiedsocialcreditcode())) {
            int UnifiedsocialcreditcodeRow = onlyVerifyMapper.countUnifiedsocialcreditcode(wechatBindParam.getUnifiedsocialcreditcode());
            if (UnifiedsocialcreditcodeRow > 0) {
                return ResultUtil.result(ResultStatusEnum.S_10011.getStatus(), ResultStatusEnum.S_10011.getMassage());
            }
        }else {
            return ResultUtil.result(ResultStatusEnum.S_10014.getStatus(), ResultStatusEnum.S_10014.getMassage());
        }
        return ResultUtil.success();
    }
}
