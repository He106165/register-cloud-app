package com.censoft.weChat.service.impl;

import com.censoft.common.utils.StringUtils;
import com.censoft.util.ResultStatusEnum;
import com.censoft.util.ResultUtil;
import com.censoft.weChat.domain.WechatBindParam;
import com.censoft.weChat.mapper.OnlyVerifyMapper;
import com.censoft.weChat.service.OnlyVerifyService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @创建人:wqgeng
 * @创建时间:2020-12-1415:59
 * @描述:用户唯一性校验
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OnlyVerifyServiceImpl implements OnlyVerifyService {
    @Resource
    private OnlyVerifyMapper onlyVerifyMapper;

    @Override
    public ResultUtil onlyVerify(WechatBindParam wechatBindParam) {
        if (("00").equals(wechatBindParam.getUserType())) {
            //手机号不为空  邮箱、证件号为空
            if (StringUtils.isNotBlank(wechatBindParam.getPhone()) && StringUtils.isBlank(wechatBindParam.getEmail()) && StringUtils.isBlank(wechatBindParam.getCardNo())) {
                int phoneRow = onlyVerifyMapper.countPhone(wechatBindParam.getPhone());
                if (phoneRow > 0) {
                    return ResultUtil.result(ResultStatusEnum.S_10008.getStatus(), ResultStatusEnum.S_10008.getMassage());
                }
                //邮箱不为空  手机号、证件号为空
            } else if (StringUtils.isNotBlank(wechatBindParam.getEmail()) && StringUtils.isBlank(wechatBindParam.getPhone()) && StringUtils.isBlank(wechatBindParam.getCardNo())) {
                int emailRow = onlyVerifyMapper.countEmail(wechatBindParam.getEmail());
                if (emailRow > 0) {
                    return ResultUtil.result(ResultStatusEnum.S_10009.getStatus(), ResultStatusEnum.S_10009.getMassage());
                }
                //证件号不为空  手机号、邮箱为空
            } else if (StringUtils.isNotBlank(wechatBindParam.getCardNo()) && StringUtils.isBlank(wechatBindParam.getPhone()) && StringUtils.isBlank(wechatBindParam.getEmail())) {
                int cardNoRow = onlyVerifyMapper.countCardNo(wechatBindParam.getCardNo());
                if (cardNoRow > 0) {
                    return ResultUtil.result(ResultStatusEnum.S_10010.getStatus(), ResultStatusEnum.S_10010.getMassage());
                }
            } else {
                return ResultUtil.result(ResultStatusEnum.S_10014.getStatus(), ResultStatusEnum.S_10014.getMassage());
            }
        } else if (("01").equals(wechatBindParam.getUserType())) {
            //社会信用码不为空 邮箱为空
            if (StringUtils.isNotBlank(wechatBindParam.getUnifiedsocialcreditcode()) && StringUtils.isBlank(wechatBindParam.getEmail())) {
                int UnifiedsocialcreditcodeRow = onlyVerifyMapper.countUnifiedsocialcreditcode(wechatBindParam.getUnifiedsocialcreditcode());
                if (UnifiedsocialcreditcodeRow > 0) {
                    return ResultUtil.result(ResultStatusEnum.S_10011.getStatus(), ResultStatusEnum.S_10011.getMassage());
                }
                //邮箱不为空 社会信用码为空
            } else if (StringUtils.isNotBlank(wechatBindParam.getEmail()) && StringUtils.isBlank(wechatBindParam.getUnifiedsocialcreditcode())) {
                int countEmailCodeRow = onlyVerifyMapper.countEmailCode(wechatBindParam.getEmail());
                if (countEmailCodeRow > 0) {
                    return ResultUtil.result(ResultStatusEnum.S_10012.getStatus(), ResultStatusEnum.S_10012.getMassage());
                }
            } else {
                return ResultUtil.result(ResultStatusEnum.S_10014.getStatus(), ResultStatusEnum.S_10014.getMassage());
            }
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10013.getStatus(), ResultStatusEnum.S_10013.getMassage());
        }
        return ResultUtil.success();

    }
}
