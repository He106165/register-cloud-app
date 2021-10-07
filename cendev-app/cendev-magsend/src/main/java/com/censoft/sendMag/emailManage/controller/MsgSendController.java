package com.censoft.sendMag.emailManage.controller;

import com.censoft.common.core.controller.BaseController;
import com.censoft.common.core.domain.R;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.sendMag.emailManage.Util.MsgSend;
import com.censoft.sendMag.emailManage.domain.MailSendInfo;
import com.censoft.sendMag.emailManage.domain.MailSendLog;
import com.censoft.sendMag.emailManage.domain.NoteSendInfo;
import com.censoft.sendMag.emailManage.domain.NoteSendLog;
import com.censoft.sendMag.emailManage.service.IMailSendInfoService;
import com.censoft.sendMag.emailManage.service.IMailSendLogService;
import com.censoft.sendMag.emailManage.service.INoteSendInfoService;
import com.censoft.sendMag.emailManage.service.INoteSendLogService;
import jdk.internal.dynalink.support.NameCodec;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 邮件发送 提供者
 *
 * @author cendev
 * @date 2020-10-21
 */
@RestController
@RequestMapping("MsgSendInfo")
@PropertySource({"classpath:application.properties"})
public class MsgSendController extends BaseController
{

	@Autowired
	private IMailSendInfoService mailSendInfoService;

	@Autowired
	private IMailSendLogService iMailSendLogService;

	@Autowired
	private INoteSendInfoService iNoteSendInfoService;

	@Autowired
	private INoteSendLogService iNoteSendLogService;


	@Value("${MSGTOKEN}")
	private String MSGTOKEN;

	@Value("${GETTOKEN_URL}")
	private String GETTOKEN_URL;

	@Value("${NAME}")
	private String NAME;

	@Value("${PASSWORD}")
	private String PASSWORD;

	@Value("${TOKEN_TIME}")
	private String TOKEN_TIME;

	@Value("${MSG_SEND_URL}")
	private String MSG_SEND_URL;

	@Autowired
	private RedisUtils redisUtils;

	private final String  phoneNumFlag = "phoneSendFlagMin";

	// 5分钟最多发 3条
	private final Integer sendNum = 3 ;
	// 规定redis 中 消息发送总数的有效时长
	private final Long  sendMin = 300L ;

	private final String  emilNumFlag = "emilSendFlagMin";

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public MailSendInfo get(@PathVariable("id") String id)
	{
		return mailSendInfoService.selectMailSendInfoById(id);

	}

	/**
	 * 查询邮件发送列表
	 */
	@GetMapping("list")
	public R list(MailSendInfo mailSendInfo)
	{
		startPage();
        return result(mailSendInfoService.selectMailSendInfoList(mailSendInfo));
	}


	/**
	 * 新增保存邮件发送
	 */
	@PostMapping("save")
	public R addSave(@RequestBody MailSendInfo mailSendInfo)
	{
		return toAjax(mailSendInfoService.insertMailSendInfo(mailSendInfo));
	}

	/**
	 * 修改保存邮件发送
	 */
	@PostMapping("update")
	public R editSave(@RequestBody MailSendInfo mailSendInfo)
	{
		return toAjax(mailSendInfoService.updateMailSendInfo(mailSendInfo));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{
		return toAjax(mailSendInfoService.deleteMailSendInfoByIds(ids));
	}

	@RequestMapping(value = "SendMsg", method = {RequestMethod.POST})
	@ResponseBody
	public boolean SendMsg(@RequestParam Map map){
		if(map.size()==0) return false;
		// 验证码 写死的一些东西 flag用于标记 不重置发送主题
		map.put("type","3");
		if(map.get("sendType")!=null && (map.get("sendType").equals("1") || map.get("sendType").equals("3")) && map.get("flag") == null){
			map.put("subjectTitle","动态验证码");
		}
		try{
			// 增加短信邮箱次数校验（redis 实现）
			if( !isUpperBound(map) ) return false;
            //获取token
            String token = redisUtils.get(MSGTOKEN);
            if(token==null){
                token = MsgSend.getToken(GETTOKEN_URL,NAME,PASSWORD);
                redisUtils.set(MSGTOKEN,token,Long.valueOf(TOKEN_TIME));
            }
            if(map.get("flag") != null){
				map.put("emailContent", msgContent(map.get("name").toString(), map.get("password").toString()));
			}
			map.remove("password");
            map.remove("flag");
			map.remove("name");
			map.remove("name");
			System.out.println(JSONObject.fromObject(map));
			//发送短信邮件
            boolean b = MsgSend.sendMsg(JSONObject.fromObject(map).toString(), token,MSG_SEND_URL);

            //日志记录
            MailSendInfo mailSendInfo=new MailSendInfo();
            MailSendLog mailSendLog=new MailSendLog();
            NoteSendInfo noteSendInfo=new NoteSendInfo();
            NoteSendLog noteSendLog=new NoteSendLog();
            MsgSend.initData(mailSendInfo,mailSendLog,noteSendInfo,noteSendLog,b,map);
            String type =map.containsKey("sendType")? map.get("sendType").toString() : null;
            if( type!=null && type.equals("3")){
                iNoteSendInfoService.insertNoteSendInfo(noteSendInfo);
                iNoteSendLogService.insertNoteSendLog(noteSendLog);
            }else if( type!=null && type.equals("2") ){
                iMailSendLogService.insertMailSendLog(mailSendLog);
                mailSendInfoService.insertMailSendInfo(mailSendInfo);
            }else {
				iNoteSendInfoService.insertNoteSendInfo(noteSendInfo);
				iNoteSendLogService.insertNoteSendLog(noteSendLog);
				iMailSendLogService.insertMailSendLog(mailSendLog);
				mailSendInfoService.insertMailSendInfo(mailSendInfo);
			}
        }catch (Exception e){
			e.printStackTrace();
		    return false;
        }
		return true;
	}


	// 消息发送判断发送次数是不是达到上限
	public boolean isUpperBound(Map map){
		// 判断手机号
		if(map.get("phoneTo")!=null){
			String phoneNum = redisUtils.get(map.get("phoneTo") + phoneNumFlag);
			if(phoneNum !=null){
				int num = Integer.valueOf(phoneNum);
				// 判断是否达到上限
				if(num >= sendNum) return false;
				redisUtils.setIfTimeLive(map.get("phoneTo") + phoneNumFlag, num + 1 );
			}else{
				redisUtils.set(map.get("phoneTo") + phoneNumFlag,1,sendMin);
			}
		}
		// 处理邮箱
		if(map.get("mailTo") != null){
			String mailNum = redisUtils.get(map.get("mailTo") + emilNumFlag);
			if(mailNum != null){
				int num = Integer.valueOf(mailNum) ;
				// 判断是否达到上限
				if(num >= sendNum) return false;
				redisUtils.setIfTimeLive(map.get("mailTo") + emilNumFlag, num + 1 );
			}else{
				redisUtils.set(map.get("mailTo") + emilNumFlag,1,sendMin);
			}
		}
		return true;
	}


	public String msgContent(String name,String password){
			return name+"： <br/> 您好，<br/>" +
					"感谢您选择教育部留学服务中心作为办理派出手续的服务机构。<br/>" +
					"您的初始登录密码为："+password+"，请使用CSC学号（12位数字）作为<br/>" +
					"用户名登录留学政务服务平台公派留学板块办理派出手续。<br/><hr>" +
					"<strong>此邮件为自动发送邮件，咨询邮箱为：chuguo1@cscse.edu.cn。<br/>" +
					"教育部留学服务中心出国处</strong>" +
					"<br/>各位留学人员：<br/>" +
					"   &emsp;大家好！当前，全球新冠肺炎疫情形势依然十分严峻，部分国家安全形势复杂多变，国际旅行仍存在较大风险。特别提醒广大留学人员，请在充分做好安全风险评估后，办理出国留学派出手续。<br/>" +
					"受教育部委托，教育部留学服务中心负责开展“平安留学”行前培训工作。根据教育部关于做好国家公派留学人员行前培训工作的指示，每位国家公派留学人员应于派出前接受培训。对于受疫情影响，未能在派出前参加线下和线上培训的公派留学人员，请按照通知要求自学完成留学安全、心理、健康、领保等培训内容。<br/>" +
					"&emsp;&emsp;留学梦报国志<br/>" +
					"&emsp;&emsp;航天英雄杨利伟：无论你在哪里，祖国都和你在一起http://www.cgpx.org/palx/406280/407639/index.html<br/>" +
					" " +
					"&emsp;&emsp;留学安全<br/>" +
					"&emsp;&emsp;中国人民公安大学教授 王大伟：出国留学平安讲座<br/>" +
					"&emsp;&emsp;第一讲：http://www.cgpx.org/palx/spkt/aqff/487197/index.html<br/>" +
					"&emsp;&emsp;第二讲：http://www.cgpx.org/palx/spkt/aqff/487193/index.html<br/>" +
					"&emsp;&emsp;第三讲：http://www.cgpx.org/palx/spkt/aqff/487189/index.html<br/>" +
					" " +
					"&emsp;&emsp;留学心理<br/>" +
					"&emsp;&emsp;清华大学教授 彭凯平：适应文化差异给留学带来的心理冲突、战胜留学心理问题的方法<br/>" +
					"&emsp;&emsp;第一讲：http://www.cgpx.org/palx/spkt/xljk/487206/index.html<br/>" +
					"&emsp;&emsp;第二讲：http://www.cgpx.org/palx/spkt/xljk/487203/index.html<br/>" +
					"&emsp;&emsp;第三讲：http://www.cgpx.org/palx/spkt/xljk/487200/index.html<br/>" +
					" " +
					"&emsp;&emsp;留学健康<br/>" +
					"&emsp;&emsp;北京大学第三医院院长、中国工程院院士乔杰：如何健康留学、如何成功留学！<br/>" +
					"&emsp;&emsp;http://www.cgpx.org/palx/spkt/rcbj/487209/index.html<br/>" +
					" " +
					"&emsp;&emsp;领事保护<br/>" +
					"&emsp;&emsp;请登陆中国领事服务网http://cs.mfa.gov.cn/ 领事保护板块http://cs.mfa.gov.cn/zggmzhw/lsbh/ 学习领事保护相关知识。<br/>" +
					" " +
					"&emsp;&emsp;疫情防控<br/>" +
					"&emsp;&emsp;当前，海外疫情形势依然严峻， 附上由教育部留学服务中心组织专家精心编制的《海外留学人员新型冠状病毒肺炎防控指南》：http://www.cgpx.org/palx/uiFramework/commonResource/zip/zip/zip/pdfjs-1/web/viewer.html?file=/palx/resource/cms/article/405798/473625/2020120813323149052.pdf ，希望广大留学人员保持高度的警惕性，不断加强日常科学防护，增强安全防范意识。<br/>" +
					"&emsp;如有关于线上培训的相关问题，请咨询xqpx@cscse.edu.cn。祝各位留学人员学有所成，健康平安！<br/>";
	}


}
