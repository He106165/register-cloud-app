package com.censoft.userregister.service.impl;

import com.censoft.userregister.dao.SpzMapper;
import com.censoft.userregister.domain.SpzDomain;
import com.censoft.userregister.service.SpzService;
import com.censoft.userregister.util.ResultUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpzServiceImpl implements SpzService {
    @Autowired
    private SpzMapper spzMapper;

    @Override
    public ResultUtil querySpz(SpzDomain spzDomain) {
        String spz = spzDomain.getSpz();
        String[] split = spz.split("/");
        List<String> spzlist = new ArrayList<>();

        for (int i = 1; i < split.length; i++) {
            String s = split[i];
            spzlist.add(s);
        }
//        [ue1ac, ue1ac, ue1ac]
        System.out.println(spzlist);
        //将集合中的数据作为参数查询数据库返回path
        List<String> spzPathList = new ArrayList<>();
        //判断字符串中是否含有汉字
        String regex = "[\u4e00-\u9fa5]";
        Pattern pattern = Pattern.compile(regex);
        for (String s : spzlist) {
            Matcher matcher = pattern.matcher(s);
            //如果含有汉字
            if (matcher.find()){

            }
            String  spzPa =  spzMapper.querySpz(s);
            spzPathList.add(spzPa);
        }
        return ResultUtil.success(spzPathList);
    }
}
