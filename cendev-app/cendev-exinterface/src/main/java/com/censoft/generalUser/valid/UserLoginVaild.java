package com.censoft.generalUser.valid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserLoginVaild {

    @Pattern(regexp = "[^%&',;=?$<>!#^*()_\\-+:\\x22]+",message = "用户名格式不正确")
    @Size(min = 0,max = 50,message = "用户名不能超过50位")
    private String userName;



    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$",message = "密码格式错误")
    private String password;


    @Pattern(regexp = "^[A-z0-9\\u4e00-\\u9fa5]*$",message = "系统标识有特殊字符")
    @NotBlank(message = "系统唯一标识不能为空")
    @Size(min = 10,max = 20,message = "系统唯一标识格式不正确")
    //    SYSTEMCODE:系统唯一标识
    private String sysuqid;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSysuqid() {
        return sysuqid;
    }

    public void setSysuqid(String sysuqid) {
        this.sysuqid = sysuqid;
    }
}
