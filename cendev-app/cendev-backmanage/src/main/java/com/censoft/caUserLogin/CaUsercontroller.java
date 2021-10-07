package com.censoft.caUserLogin;


import com.censoft.common.core.controller.BaseController;
import com.censoft.common.core.domain.R;
import com.censoft.common.log.enums.OperatorType;
import com.censoft.insideUserManage.insideRole.domain.InsideRole;
import com.censoft.insideUserManage.insideRole.service.IInsideRoleService;
import com.censoft.insideUserManage.insideSystem.domain.InsideSystemInfo;
import com.censoft.insideUserManage.insideSystem.service.IInsideSystemInfoService;
import com.censoft.insideUserManage.insideUser.service.IInsideUserInfoService;
import com.censoft.otherlogin.feign.RemoteTokenServer;
import com.censoft.system.domain.SysUser;
import com.censoft.system.feign.RemoteUserService;
import com.censoft.userManage.consulateUser.domain.ConsulateInfo;
import com.censoft.userManage.consulateUser.service.IConsulateInfoService;
import com.censoft.userManage.fcenterUser.domain.FcenterUserInfo;
import com.censoft.userManage.fcenterUser.service.IFcenterUserInfoService;
import com.censoft.userManage.gridUser.service.IGridUserInfoService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.HelperClient;
import util.HelperResult;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@RestController
@RequestMapping("caUserLogin")
public class CaUsercontroller extends BaseController {

    @Autowired
    private  IInsideUserInfoService insideUserInfoService;

    @Autowired
    private IConsulateInfoService iConsulateInfoService;

    @Autowired
    private CaUserService caUserService;

    @Autowired
    private RemoteTokenServer tokenService;

    @Autowired
    private RemoteUserService userService;

    @Autowired
    private IInsideRoleService insideRoleService;

    @Autowired
    private IInsideSystemInfoService iInsideSystemInfoService;

    @Autowired
    private IFcenterUserInfoService iFcenterUserInfoService;

    @Autowired
    private IGridUserInfoService iGridUserInfoService;

    /**
    * @Description CA普通用户登陆{ 网格用户 、分中心用户、 教育处用户 }
    * @Parm
    * @return
    **/
    @PostMapping("caLogin")
    public R caLogin(@RequestBody LoginFrom from){
        if( from.getUsername() == null ) return R.error("用户名不能为空");

        JSONObject userinfo;
        //先查询网格用户
        userinfo = JSONObject.fromObject(iGridUserInfoService.getGridUserByName(from.getUsername()));
        OperatorType  operatorType=OperatorType.GRID;

        //再查询教育处用户
        if(userinfo.size()==0){
            ConsulateInfo userByName = iConsulateInfoService.getUserByName(from.getUsername());
            userinfo = JSONObject.fromObject(userByName);
            operatorType=OperatorType.EDUCATION;
        }

        //再查询分中心用户
        if(userinfo.size()==0){
            FcenterUserInfo userInfoByName = iFcenterUserInfoService.getUserInfoByName(from.getUsername());
            userinfo = JSONObject.fromObject(userInfoByName);
            operatorType=OperatorType.FCENTER;
        }

        if(userinfo.size()==0){
            return R.error("用户不存在");
        }
        //token 服务导航页面可能会跳转 注册系统，需要token
        String toekn= tokenService.getToken(from.getUsername());

        userinfo.put("userType",operatorType.toString());
        return R.ok(caUserService.saveToRides(toekn,userinfo,operatorType));
    }



    /**
     * @Description CA内部用户登陆
     * @Parm
     * @return
     **/
    @PostMapping("caAdminLogin")
    public R caAdminLogin(@RequestBody LoginFrom from){
        if( from.getUsername() == null ) return R.error("用户名不能为空");

        JSONObject userinfo;
        //先查询内部用户
        userinfo = JSONObject.fromObject(userService.selectSysUserByUsername(from.getUsername()));
        OperatorType  operatorType=OperatorType.INSIDE;
        if(userinfo.size()==0){
            return R.error("用户不存在");
        }
        //token 服务导航页面可能会跳转 注册系统，需要token
        String toekn= tokenService.getToken(from.getUsername());

        userinfo.put("userType",operatorType.toString());
        return R.ok(caUserService.saveToRides(toekn,userinfo,operatorType));
    }




    /**
     * 获取登陆用户应用系统的权限
     * */
    @GetMapping("getUserSystemByRole")
    public List<InsideSystemInfo> getUserSystemByRole() throws Exception {
        if(getUserType()!=null && !getUserType().equals("INSIDE")) return null;

        //只查询了内部用户
        String loginName = getLoginName();
        if(loginName ==null)  throw new Exception("登陆错误");
        SysUser sysUser = userService.selectSysUserByUsername(loginName);

        //用来给角色集合去重
        Set<String> system =new HashSet<>();
        //结果
        List<InsideSystemInfo> res =null;

        //获取应用系统ID集合
        if(sysUser.getInsideRole()!=null && sysUser.getInsideRole()!=""){
            List<InsideRole> insideRoles = insideRoleService.selectInsideRoleListByRoles(sysUser.getInsideRole());
            for (InsideRole insideRole : insideRoles) {
                if(insideRole.getSystemList()!=null && insideRole.getSystemList()!=""){
                    String[] split = insideRole.getSystemList().split(",");
                    for (String s : split) {
                        system.add(s);
                    }
                }
            }
        }
        if(system.size()!=0){
            String[] systemIds=
                    Arrays.copyOf(system.toArray(), system.size(), String[].class);
            //写一个查询应用系统的方法 ，根据 Ids获取  传递给服务导航页面
             res = iInsideSystemInfoService.getSystemListInfoByIds(systemIds);
        }
        return res;
    }

    /**
     * 获取当前CA用户登陆信息
     * 判断当前用户的用户类型
     * 根据用户类型获取用户信息 转string 传递
     * */
    @GetMapping("getCALoginUserInfo")
    public String getCALoginUserInfo() throws Exception {
        Long userId = getCurrentUserId();
        String userType = getUserType();
        if(userType !=null && userType.equals("INSIDE")){
            Map map= userService.getInsideUserById(userId);
            map.put("userType","INSIDE");
            return JSONObject.fromObject(map).toString();

        }else if(userType !=null && userType.equals("GRID")){
            Map map= iGridUserInfoService.getGridUserById(userId);
            map.put("userType","GRID");
            return JSONObject.fromObject(map).toString();
        }
        else if(userType !=null && userType.equals("EDUCATION")){
            Map map= iConsulateInfoService.getConUserById(userId);
            map.put("userType","EDUCATION");
            return JSONObject.fromObject(map).toString();
        } else if(userType != null && userType.equals("FCENTER")){
            Map map= iFcenterUserInfoService.getFcenterUserById(userId);
            map.put("userType","FCENTER");
            return JSONObject.fromObject(map).toString();
        }
        return null;
    }



    /**
     * @Description 测试CA登陆校验，之后会挪到backmanage中
     * @Parm
     * @return
     **/
    @PostMapping("CAVerifyLogin")
    public R CAVerifyLogin(HttpServletRequest resq) throws  Exception {
        String date = getDate(resq);
        JSONObject jsonObject = JSONObject.fromObject(date);
        String signString = jsonObject.get("signString").toString();
        if(signString == null ){
            return R.error(-7,"证书内容为空");
        }

        byte[] signbyte = signString.getBytes();
//        String IP="192.168.2.237";
        String IP="192.168.2.139";
        int port = 5555;
        int timeout = 5000;
        HelperClient helpclient = new HelperClient();
        HelperResult result = new HelperResult();
        helpclient.init(IP, port, timeout);
        helpclient.verifyCertificate(signbyte, result, false);
        int ret = result.getResult();
        if (ret==0) {
            return R.ok("成功");
        }else if(ret==-1) {
            return R.error(-1,"证书已过期");
        }else if(ret==-2) {
            return R.error(ret, "证书尚未生效");
        }else if(ret==-3) {
            return R.error(ret, "证书签名验证失败");
        }else if(ret==-4) {
            return R.error(ret, "证书已作废");
        }else if(ret==-5) {
            return R.error(ret, "签名不正确");
        }else {
            return R.error(-6, "证书无效");
        }
    }

    public String getDate(HttpServletRequest request){
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }


}
