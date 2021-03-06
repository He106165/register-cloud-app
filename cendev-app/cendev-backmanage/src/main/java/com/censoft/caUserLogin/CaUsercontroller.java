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
    * @Description CA??????????????????{ ???????????? ????????????????????? ??????????????? }
    * @Parm
    * @return
    **/
    @PostMapping("caLogin")
    public R caLogin(@RequestBody LoginFrom from){
        if( from.getUsername() == null ) return R.error("?????????????????????");

        JSONObject userinfo;
        //?????????????????????
        userinfo = JSONObject.fromObject(iGridUserInfoService.getGridUserByName(from.getUsername()));
        OperatorType  operatorType=OperatorType.GRID;

        //????????????????????????
        if(userinfo.size()==0){
            ConsulateInfo userByName = iConsulateInfoService.getUserByName(from.getUsername());
            userinfo = JSONObject.fromObject(userByName);
            operatorType=OperatorType.EDUCATION;
        }

        //????????????????????????
        if(userinfo.size()==0){
            FcenterUserInfo userInfoByName = iFcenterUserInfoService.getUserInfoByName(from.getUsername());
            userinfo = JSONObject.fromObject(userInfoByName);
            operatorType=OperatorType.FCENTER;
        }

        if(userinfo.size()==0){
            return R.error("???????????????");
        }
        //token ????????????????????????????????? ?????????????????????token
        String toekn= tokenService.getToken(from.getUsername());

        userinfo.put("userType",operatorType.toString());
        return R.ok(caUserService.saveToRides(toekn,userinfo,operatorType));
    }



    /**
     * @Description CA??????????????????
     * @Parm
     * @return
     **/
    @PostMapping("caAdminLogin")
    public R caAdminLogin(@RequestBody LoginFrom from){
        if( from.getUsername() == null ) return R.error("?????????????????????");

        JSONObject userinfo;
        //?????????????????????
        userinfo = JSONObject.fromObject(userService.selectSysUserByUsername(from.getUsername()));
        OperatorType  operatorType=OperatorType.INSIDE;
        if(userinfo.size()==0){
            return R.error("???????????????");
        }
        //token ????????????????????????????????? ?????????????????????token
        String toekn= tokenService.getToken(from.getUsername());

        userinfo.put("userType",operatorType.toString());
        return R.ok(caUserService.saveToRides(toekn,userinfo,operatorType));
    }




    /**
     * ???????????????????????????????????????
     * */
    @GetMapping("getUserSystemByRole")
    public List<InsideSystemInfo> getUserSystemByRole() throws Exception {
        if(getUserType()!=null && !getUserType().equals("INSIDE")) return null;

        //????????????????????????
        String loginName = getLoginName();
        if(loginName ==null)  throw new Exception("????????????");
        SysUser sysUser = userService.selectSysUserByUsername(loginName);

        //???????????????????????????
        Set<String> system =new HashSet<>();
        //??????
        List<InsideSystemInfo> res =null;

        //??????????????????ID??????
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
            //???????????????????????????????????? ????????? Ids??????  ???????????????????????????
             res = iInsideSystemInfoService.getSystemListInfoByIds(systemIds);
        }
        return res;
    }

    /**
     * ????????????CA??????????????????
     * ?????????????????????????????????
     * ???????????????????????????????????? ???string ??????
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
     * @Description ??????CA??????????????????????????????backmanage???
     * @Parm
     * @return
     **/
    @PostMapping("CAVerifyLogin")
    public R CAVerifyLogin(HttpServletRequest resq) throws  Exception {
        String date = getDate(resq);
        JSONObject jsonObject = JSONObject.fromObject(date);
        String signString = jsonObject.get("signString").toString();
        if(signString == null ){
            return R.error(-7,"??????????????????");
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
            return R.ok("??????");
        }else if(ret==-1) {
            return R.error(-1,"???????????????");
        }else if(ret==-2) {
            return R.error(ret, "??????????????????");
        }else if(ret==-3) {
            return R.error(ret, "????????????????????????");
        }else if(ret==-4) {
            return R.error(ret, "???????????????");
        }else if(ret==-5) {
            return R.error(ret, "???????????????");
        }else {
            return R.error(-6, "????????????");
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
