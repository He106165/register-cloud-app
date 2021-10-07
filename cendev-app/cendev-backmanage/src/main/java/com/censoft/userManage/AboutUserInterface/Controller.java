package com.censoft.userManage.AboutUserInterface;

import com.censoft.Util.BackManageMethod;
import com.censoft.common.core.controller.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("userInterface")
@PropertySource({"classpath:application.properties"})
public class Controller extends BaseController{

    @Value("${CERTIFICATION}")
    private String CERTIFICATION;

    @PostMapping("certFourData")
    public boolean editSave(@RequestBody CertUserInfo personalUserInfo)
    {
        return personalUserInfo==null  ? false : BackManageMethod.certFourData(personalUserInfo,CERTIFICATION);
    }
    @PostMapping("certTwoData")
    public boolean certTwoData(@RequestParam("userName") String userName,@RequestParam("cardNo") String cardNo)
    {
        return BackManageMethod.certTwoData(userName,cardNo,CERTIFICATION);
    }
}
