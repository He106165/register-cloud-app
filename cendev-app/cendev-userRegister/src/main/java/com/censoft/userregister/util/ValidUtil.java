package com.censoft.userregister.util;

import java.util.Iterator;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class ValidUtil {
/**
 *@创建人:wqgeng
 *@创建时间:2020-11-2318:49
 *@描述:后台校验信息读取
 */
public ValidUtil() {
}

    public static ResultUtil getDefaultMsg(BindingResult result) {
        return ResultUtil.result(400, getTips(result));
    }

    public static String getTips(BindingResult result) {
        String msg = "";

        ObjectError err;
        for(Iterator var2 = result.getAllErrors().iterator(); var2.hasNext(); msg = msg + "[" + err.getDefaultMessage() + "]") {
            err = (ObjectError)var2.next();
        }

        return msg;
    }
}
