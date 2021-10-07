package com.censoft.otherlogin.AwxLogin.service;

import com.censoft.common.core.domain.R;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *@创建人:wqgeng
 *@创建时间:2021-02-0411:39
 *@描述:个人用户微信信息
 */
public interface WXLoginService {

    String pcLoginByWeiXin(Map map, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr, Integer integer);

    boolean WechatSendMsg(String phone);

    String completeInfo(Map map, HttpServletResponse response, RedirectAttributes attr);

    String bangdingWechat(Map map, HttpServletResponse response,String token);
}
