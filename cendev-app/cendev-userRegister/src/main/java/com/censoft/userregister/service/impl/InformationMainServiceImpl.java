package com.censoft.userregister.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.censoft.cendevbackmanage.feign.UserInterfaceServer;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.common.utils.StringUtils;
import com.censoft.system.domain.SysDictData;
import com.censoft.userregister.dao.InformationMainMapper;
import com.censoft.userregister.domain.*;
import com.censoft.userregister.service.InformationMainService;
import com.censoft.userregister.util.DictUtils;
import com.censoft.userregister.util.ResultStatusEnum;
import com.censoft.userregister.util.ResultUtil;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @创建人:wqgeng
 * @创建时间:2020-12-1715:36
 * @描述:信息维护实现类
 */
@Service
@Transactional
public class InformationMainServiceImpl implements InformationMainService {
    @Resource
    private InformationMainMapper informationMainMapper;
    @Autowired
    private UserInterfaceServer userInterfaceServer;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //基本信息回显
    @Override
    public ResultUtil<PersonalUserInfo> queryInfo(PersonalUserInfo personalUserInfo) {

        PersonalUserInfo infoList = informationMainMapper.queryInfo(personalUserInfo);

        return ResultUtil.success(infoList);
    }

    //基本信息修改
    @Override
    public ResultUtil updateInfo(PersonalUserInfo personalUserInfo) {
        int row = informationMainMapper.updateInfo(personalUserInfo);
        if (row <= 0) {
            return ResultUtil.result(ResultStatusEnum.S_10025.getStatus(), ResultStatusEnum.S_10025.getMassage());
        }
        return ResultUtil.success();
    }

    //新增、修改扩展信息
    @Override
    public ResultUtil saveOrUpdate(PersonalExtendInfo personalExtendInfo) {
        if (StringUtils.isBlank(personalExtendInfo.getUserId())) {
            return ResultUtil.result(ResultStatusEnum.S_10026.getStatus(), ResultStatusEnum.S_10026.getMassage());
        }
        //查询数据库中是否存在该用户的扩展信息
        int row = informationMainMapper.selectUserInfo(personalExtendInfo);
        //判断 当前用户 是否填写扩展信息，填写则为修改，未填写为新增
        int s = 0;
        if (row == 0) {
            personalExtendInfo.setId(UUID.randomUUID().toString().replace("-", ""));
            personalExtendInfo.setCreateTime(new Date());
            s = informationMainMapper.save(personalExtendInfo);
        } else {

            return update(personalExtendInfo);
        }
        return ResultUtil.success(s);
    }

    //修改 扩展信息
    private ResultUtil update(PersonalExtendInfo personalExtendInfo) {
        personalExtendInfo.setUpdateTime(new Date());
        int u = informationMainMapper.update(personalExtendInfo);
        return ResultUtil.success(u);
    }

    //查询扩展信息
    @Override
    public ResultUtil queryExtendInfo(PersonalExtendInfo personalExtendInfo) {
        PersonalExtendInfo personal = informationMainMapper.queryExtendInfo(personalExtendInfo);
        return ResultUtil.success(personal);
    }

    //查询用户是否填写了扩展信息
    @Override
    public ResultUtil queryExtendCount(PersonalExtendInfo personalExtendInfo) {
        int row = informationMainMapper.selectUserInfo(personalExtendInfo);
        if (row == 0) {
            return ResultUtil.result(ResultStatusEnum.S_10027.getStatus(), ResultStatusEnum.S_10027.getMassage());
        }
        return ResultUtil.success();
    }

    // 新增留学经历信息
    @Override
    public ResultUtil insertAbroafdstudyInfo(PersonalAbroafdstudyInfo personalAbroafdstudyInfo) {
        personalAbroafdstudyInfo.setId(UUID.randomUUID().toString().replace("-", ""));
        personalAbroafdstudyInfo.setCreateTime(new Date());
        int row = informationMainMapper.insertAbroafdstudyInfo(personalAbroafdstudyInfo);
        if (row == 0) {
            return ResultUtil.result(ResultStatusEnum.S_10028.getStatus(), ResultStatusEnum.S_10028.getMassage());
        }
        return ResultUtil.success();
    }

    // 查询留学经历信息
    @Override
    public ResultUtil queryAbroafdstudyInfo(PersonalAbroafdstudyInfo personalAbroafdstudyInfo) {
        List<PersonalAbroafdstudyInfo> list = informationMainMapper.queryAbroafdstudyInfo(personalAbroafdstudyInfo);
        return ResultUtil.success(list);
    }

    //  根据行id查询留学信息用于编辑回显
    @Override
    public ResultUtil queryAbroafdstudyInfoById(PersonalAbroafdstudyInfo personalAbroafdstudyInfo) {
        PersonalAbroafdstudyInfo infoById = informationMainMapper.queryAbroafdstudyInfoById(personalAbroafdstudyInfo.getId());
        return ResultUtil.success(infoById);
    }

    @Override
    public ResultUtil updateAbroafdstudyInfo(PersonalAbroafdstudyInfo personalAbroafdstudyInfo) {
        int row = informationMainMapper.updateAbroafdstudyInfo(personalAbroafdstudyInfo);
        if (row == 0) {
            return ResultUtil.result(ResultStatusEnum.S_10029.getStatus(), ResultStatusEnum.S_10029.getMassage());
        }
        return ResultUtil.success();
    }

    //根据id删除留学信息
    @Override
    public ResultUtil deleteAbroafdstudyInfoById(PersonalAbroafdstudyInfo personalAbroafdstudyInfo) {
        int row = informationMainMapper.deleteAbroafdstudyInfoById(personalAbroafdstudyInfo.getId());
        if (row == 0) {
            return ResultUtil.result(ResultStatusEnum.S_10030.getStatus(), ResultStatusEnum.S_10030.getMassage());
        }
        return ResultUtil.success();
    }

    //查询实名信息
    @Override
    public ResultUtil queryRealNameInformation(PersonalUserInfo personalUserInfo) {
        List<PersonalUserInfo> infoList = informationMainMapper.queryRealNameInformation(personalUserInfo);
        return ResultUtil.success(infoList);
    }

    //实名认证接口
    @Override
    public ResultUtil authentication(Authentication authentication) {
        if (StringUtils.isBlank(authentication.getUserName()) && StringUtils.isBlank(authentication.getCardNo())) {
            return ResultUtil.result(ResultStatusEnum.S_10031.getStatus(), ResultStatusEnum.S_10031.getMassage());
        }
        String s = informationMainMapper.selectAuth(authentication);
        if (("2").equals(s)) {
            //添加实名认证
            boolean b = userInterfaceServer.certTwoData(authentication.getUserName(), authentication.getCardNo());
            if (b) {
                int row = informationMainMapper.updateAuthLevel(authentication);
                if (row > 0) {
                    //修改当前登录用户的redis信息并将该用户的其他redis信息删除
                    //获取当前登录用户redis的key
                    String loginName = authentication.getLoginName();
                    //修改当前用户的redis中的信息
                    ValueOperations<String, String> forValue = redisTemplate.opsForValue();
                    //将redis里边的数据取出 转为json对象形式
                    String ss = forValue.get(loginName);
                    JSONObject redisJson = JSON.parseObject(ss);
                    redisJson.put("authlevel","3");
                    redisJson.put("isLabourReal","0");
                    //将redis的值更改后重新放入 redis中
                    forValue.set(loginName, redisJson.toJSONString());

                    //将redis中的用户数据清空
                    //根据证件号查询用户信息
                    PersonalUserLogin pp = informationMainMapper.selectCardInfo(authentication);
                    //删除redis信息
                    if (StringUtils.isNotBlank(pp.getPhone())&& !pp.getPhone().equals(loginName)) {
                        redisUtils.delete(pp.getPhone());
                    }
                    if (StringUtils.isNotBlank(pp.getEmail())&& !pp.getEmail().equals(loginName)) {
                        redisUtils.delete(pp.getEmail());
                    }
                    if (StringUtils.isNotBlank(pp.getIdnumber())&& !pp.getIdnumber().equals(loginName)) {
                        redisUtils.delete(pp.getIdnumber());
                    }
                    if (StringUtils.isNotBlank(pp.getName())&& !pp.getName().equals(loginName)) {
                        redisUtils.delete(pp.getName());
                    }
                } else {
                    return ResultUtil.result(ResultStatusEnum.S_10031.getStatus(), ResultStatusEnum.S_10031.getMassage());
                }
            } else {
                //如果是实名认证未通过，将数据库 字段 是否需要进行实名认证改为 1  是
                int ss = informationMainMapper.updateIsLabourReal(authentication);
                if (ss > 0) {
                    return ResultUtil.result(ResultStatusEnum.S_10033.getStatus(), ResultStatusEnum.S_10033.getMassage());
                } else {
                    throw new RuntimeException("实名认证异常");
                }

            }
        } else if (("3").equals(s)) {
            return ResultUtil.result(ResultStatusEnum.S_10034.getStatus(), ResultStatusEnum.S_10034.getMassage());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10032.getStatus(), ResultStatusEnum.S_10032.getMassage());
        }

        return ResultUtil.success();
    }
    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        List<SysDictData> dictDatas = DictUtils.getDictCache(dictType);
        if (StringUtils.isNotEmpty(dictDatas))
        {
            return dictDatas;
        }
        dictDatas = informationMainMapper.selectDictDataByType(dictType);
        if (StringUtils.isNotEmpty(dictDatas))
        {
            DictUtils.setDictCache(dictType, dictDatas);
            return dictDatas;
        }
        return null;
    }
}
