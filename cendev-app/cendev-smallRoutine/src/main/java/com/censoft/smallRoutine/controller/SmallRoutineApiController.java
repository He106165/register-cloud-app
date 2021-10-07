package com.censoft.smallRoutine.controller;


import com.censoft.smallRoutine.domain.BEducations;
import com.censoft.smallRoutine.domain.BOverseasEducationRank;
import com.censoft.smallRoutine.domain.BOverseasEducationRecruit;
import com.censoft.smallRoutine.service.IBOverseasEducationRankService;
import com.censoft.smallRoutine.service.IBOverseasEducationRecruitService;
import com.censoft.smallRoutine.service.SmallRoutineApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import net.sf.json.JSONObject;

/**
 *  小程序接口Api提供
* */

@Controller
@RequestMapping("/BEducations")
public class SmallRoutineApiController {


    @Autowired
     private  SmallRoutineApiService smallRoutineApiService;
    @Autowired
    private IBOverseasEducationRankService ibOverseasEducationRankService;
    @Autowired
    private IBOverseasEducationRecruitService ibOverseasEducationRecruitService;
    /**
     * 如果都没有输入就显示所有列表
     * 留学地址（国家、地区） 显示该地区国家的
     * 按照专业信息来查
     *  按照学校层次来查
     *      返回的院校列表信息
     *
     *
     *  三个参数   地址，专业信息，学校层次
     */
    @PostMapping(value = "/findAllSchools")
    @ResponseBody
    public  List<BEducations> findAllSchools(String address,String info,String leave,String id) {
         List<BEducations>  BList= smallRoutineApiService.findAllSchools(address,info,leave,id);
        return BList;
     }

    /**
     * 通过院校的id获取院校的详细信息列表
     * @param id
     * @return
     */
   @PostMapping(value = "/findSchoolById")
   @ResponseBody
    public Map selectSchoolById(String id) {
       // 1. 判断id ==null
        Map map = new HashMap();
       if (id == null) {
           map.put("code", "-1");
           map.put("msg", "院校id不能为空");
           return map;
       }

    List<BEducations> BaseInfoList = smallRoutineApiService.findAllSchools(null, null, null, id);
       BOverseasEducationRank bOverseasEducationRank = new BOverseasEducationRank();
         //通过院校的id来获取院校排名的id
        bOverseasEducationRank.setPid(id);
        //通过院校排名的id得到排名的列表
       List<BOverseasEducationRank> bOverseasEducationRanks = ibOverseasEducationRankService.selectBOverseasEducationRankList(bOverseasEducationRank);
       BOverseasEducationRecruit bOverseasEducationRecruit = new BOverseasEducationRecruit();
       // 通过院校的id来获取招生信息的Id
       bOverseasEducationRecruit.setPid(id);
       // 通过招生信息的id得到招生详情的列表
        List<BOverseasEducationRecruit> bOverseasEducationRecruits=ibOverseasEducationRecruitService.selectBOverseasEducationRecruitList(bOverseasEducationRecruit);
       map.put("BList",BaseInfoList.size() == 0 ?  null : BaseInfoList.get(0));
       map.put("bOverseasEducationRanks", bOverseasEducationRanks);
       map.put("bOverseasEducationRecruits", bOverseasEducationRecruits);

       return map;

   }
    /**
     *  获取海外教育资源的查询数据
     */
//    @PostMapping("getSelectData")
//    @ResponseBody
//    public JSONObject getSelectData(){
//        JSONObject result=new JSONObject();
//        List<String> address = new ArrayList<>();
//        address.add("北京");
//        address.add("上海");
//        address.add("武汉");
//        address.add("石家庄");
//
//        List<String> specialtys = new ArrayList<>();
//        specialtys.add("计算机科学与技术");
//        specialtys.add("土木工程");
//        specialtys.add("英语");
//        specialtys.add("会计学");
//
//        List<String> levea = new ArrayList<>();
//        levea.add("1级");
//        levea.add("2级");
//        levea.add("3级");
//        levea.add("4级");
//
//        result.put("addressInfo",address);
//        result.put("specialtyInfo",specialtys);
//        result.put("leveaInfo",levea);
//        return result;
//    }

}




