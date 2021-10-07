package com.censoft.weChat.service.impl;

import com.censoft.common.utils.MD5Format;
import com.censoft.common.utils.StringUtils;
import com.censoft.util.CheckUtils;
import com.censoft.util.DesNewUtils;
import com.censoft.util.ResultStatusEnum;
import com.censoft.util.ResultUtil;
import com.censoft.weChat.domain.WechatBindParam;
import com.censoft.weChat.domain.WechatUserInfo;
import com.censoft.weChat.mapper.WechatBindUserInfoMapper;
import com.censoft.weChat.service.WechatBindUserInfoService;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @创建人:wqgeng
 * @创建时间:2020-12-1410:21
 * @描述:微信绑定用户接口实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WechatBindUserInfoServiceImpl implements WechatBindUserInfoService {

    @Resource
    private WechatBindUserInfoMapper wechatBindUserInfoMapper;

    @Override
    public ResultUtil bindUserInfo(WechatBindParam wechatBindParam) {
        //如果登录绑定的用户为 个人用户
        if (("00").equals(wechatBindParam.getUserType())) {
            //判断微信绑定用户的唯一标识unionId不能为空
            if (StringUtils.isBlank(wechatBindParam.getUnionId())) {
                return ResultUtil.result(ResultStatusEnum.S_10001.getStatus(), ResultStatusEnum.S_10001.getMassage());
            }
            //判断传递的参数中是否包含登录的账号信息  手机号、邮箱、身份证号
            if (StringUtils.isBlank(wechatBindParam.getPhone()) && StringUtils.isBlank(wechatBindParam.getEmail()) && StringUtils.isBlank(wechatBindParam.getCardNo())) {
                return ResultUtil.result(ResultStatusEnum.S_10002.getStatus(), ResultStatusEnum.S_10002.getMassage());
            }
            //根据用户唯一信息定位到用户，成功则绑定用户（微信登陆表添加数据）
            //根据传递的登录信息、密码 进行匹配  符合信息的向微信登录表添加数据
            if (StringUtils.isNotBlank(wechatBindParam.getPhone())) {
                //查询用户表判断该账号是否存在
                int row = wechatBindUserInfoMapper.countPhone(wechatBindParam.getPhone());
                if (row > 0) {
                    try {
                        //如果是登录信息为手机号  查询数据库中的手机号 所对应的密码进行解密操作
                        String p = wechatBindUserInfoMapper.queryPassword(wechatBindParam.getPhone());
                        //对密码进行解密(首先对用户传过来的密码进行加密，然后与数据库的加密后的密码进行比对)
                        String password = MD5Format.MD5(wechatBindParam.getPassword());
                        //判断密码 是否与数据库一直
                        if (p.equals(password)) {
                            //密码校验通过 向微信登录表中插入用户信息
                            // 查询用户信息
                            String userId = wechatBindUserInfoMapper.selectUserInfo(wechatBindParam.getPhone());
                            //向微信登录表中插入绑定信息
                            WechatUserInfo wechatUserInfo = new WechatUserInfo();
                            wechatUserInfo.setId(wechatBindParam.getUnionId());
                            wechatUserInfo.setUserId(userId);
                            wechatUserInfo.setUserType("00");
                            wechatUserInfo.setCreateTime(new Date());
                            int r = wechatBindUserInfoMapper.insertWechatBindUserInfo(wechatUserInfo);
                            if (r > 0) {
                                return ResultUtil.result(ResultStatusEnum.S_10004.getStatus(), ResultStatusEnum.S_10004.getMassage());
                            }
                            return ResultUtil.result(ResultStatusEnum.S_10005.getStatus(), ResultStatusEnum.S_10005.getMassage());

                        } else {
                            return ResultUtil.result(ResultStatusEnum.S_10003.getStatus(), ResultStatusEnum.S_10003.getMassage());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("数据库可能存在唯一性问题");
                    }
                } else {
                    return ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
                }
            } else if (StringUtils.isNotBlank(wechatBindParam.getEmail())) {
                //查询用户表判断该账号是否存在
                int row = wechatBindUserInfoMapper.countEmail(wechatBindParam.getEmail());
                if (row > 0) {
                    try {
                        //如果是登录信息为手机号  查询数据库中的手机号 所对应的密码进行解密操作
                        String emailPassword = wechatBindUserInfoMapper.queryEmailPassword(wechatBindParam.getEmail());
                        //对密码进行解密(首先对用户传过来的密码进行加密，然后与数据库的加密后的密码进行比对)
                        String password = MD5Format.MD5(wechatBindParam.getPassword());
                        //判断密码 是否与数据库一直
                        if (password.equals(emailPassword)) {
                            //密码校验通过 向微信登录表中插入用户信息
                            // 查询用户信息
                            String userId = wechatBindUserInfoMapper.selectUserInfoByEmail(wechatBindParam.getEmail());
                            //向微信登录表中插入绑定信息
                            WechatUserInfo wechatUserInfo = new WechatUserInfo();
                            wechatUserInfo.setId(wechatBindParam.getUnionId());
                            wechatUserInfo.setUserId(userId);
                            wechatUserInfo.setUserType("00");
                            wechatUserInfo.setCreateTime(new Date());
                            int r = wechatBindUserInfoMapper.insertWechatBindUserInfo(wechatUserInfo);
                            if (r > 0) {
                                return ResultUtil.result(ResultStatusEnum.S_10004.getStatus(), ResultStatusEnum.S_10004.getMassage());
                            }
                            return ResultUtil.result(ResultStatusEnum.S_10005.getStatus(), ResultStatusEnum.S_10005.getMassage());

                        } else {
                            return ResultUtil.result(ResultStatusEnum.S_10003.getStatus(), ResultStatusEnum.S_10003.getMassage());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("数据库可能存在唯一性问题");
                    }
                } else {
                    return ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
                }
            } else if (StringUtils.isNotBlank(wechatBindParam.getCardNo())) {
                //查询用户表判断该账号是否存在
                int row = wechatBindUserInfoMapper.countCardNo(wechatBindParam.getCardNo());
                if (row > 0) {
                    try {
                        //如果是登录信息为身份证号 查询数据库中的身份证号所对应的密码进行解密操作
                        String cardNoPassword = wechatBindUserInfoMapper.queryCardNoPassword(wechatBindParam.getCardNo());
                        //对密码进行解密(首先对用户传过来的密码进行加密，然后与数据库的加密后的密码进行比对)
                        String password = MD5Format.MD5(wechatBindParam.getPassword());
                        //判断密码 是否与数据库一直
                        if (password.equals(cardNoPassword)) {
                            //密码校验通过 向微信登录表中插入用户信息
                            // 查询用户信息
                            String userId = wechatBindUserInfoMapper.selectUserInfoByCardNo(wechatBindParam.getCardNo());
                            //向微信登录表中插入绑定信息
                            WechatUserInfo wechatUserInfo = new WechatUserInfo();
                            wechatUserInfo.setId(wechatBindParam.getUnionId());
                            wechatUserInfo.setUserId(userId);
                            wechatUserInfo.setUserType("00");
                            wechatUserInfo.setCreateTime(new Date());
                            int r = wechatBindUserInfoMapper.insertWechatBindUserInfo(wechatUserInfo);
                            if (r > 0) {
                                return ResultUtil.result(ResultStatusEnum.S_10004.getStatus(), ResultStatusEnum.S_10004.getMassage());
                            }
                            return ResultUtil.result(ResultStatusEnum.S_10005.getStatus(), ResultStatusEnum.S_10005.getMassage());

                        } else {
                            return ResultUtil.result(ResultStatusEnum.S_10003.getStatus(), ResultStatusEnum.S_10003.getMassage());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("数据库可能存在唯一性问题");
                    }
                } else {
                    return ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
                }
            }

        } else if (("01").equals(wechatBindParam.getUserType())) {
            //当登录人为机构用户
            //判断微信绑定用户的唯一标识unionId不能为空
            if (StringUtils.isBlank(wechatBindParam.getUnionId())) {
                return ResultUtil.result(ResultStatusEnum.S_10001.getStatus(), ResultStatusEnum.S_10001.getMassage());
            }
            //判断传递的参数中是否包含登录的账号信息 社会信用码 和邮箱
            if (StringUtils.isBlank(wechatBindParam.getUnifiedsocialcreditcode()) && StringUtils.isBlank(wechatBindParam.getHostPersonEmail())) {
                return ResultUtil.result(ResultStatusEnum.S_10015.getStatus(), ResultStatusEnum.S_10015.getMassage());
            }
            if (StringUtils.isNotBlank(wechatBindParam.getUnifiedsocialcreditcode())) {
                //查询用户表判断该账号是否存在(机构用户社会信用码)
                int row = wechatBindUserInfoMapper.countUnifiedsocialcreditcode(wechatBindParam.getUnifiedsocialcreditcode());
                if (row > 0) {
                    try {
                        //如果是登录信息为社会信用码  查询数据库中的社会信用码 所对应的密码进行解密操作
                        String unifiedsocialcreditcodePassword = wechatBindUserInfoMapper.queryUnifiedsocialcreditcodePassword(wechatBindParam.getUnifiedsocialcreditcode());
                        //对密码进行解密(首先对用户传过来的密码进行加密，然后与数据库的加密后的密码进行比对)
                        String password = MD5Format.MD5(wechatBindParam.getPassword());
                        //判断密码 是否与数据库一直
                        if (password.equals(unifiedsocialcreditcodePassword)) {
                            //密码校验通过 向微信登录表中插入用户信息
                            // 查询用户信息
                            String userId = wechatBindUserInfoMapper.selectUnifiedUserInfo(wechatBindParam.getUnifiedsocialcreditcode());
                            //向微信登录表中插入绑定信息
                            WechatUserInfo wechatUserInfo = new WechatUserInfo();
                            wechatUserInfo.setId(wechatBindParam.getUnionId());
                            wechatUserInfo.setUserId(userId);
                            wechatUserInfo.setUserType("01");
                            wechatUserInfo.setCreateTime(new Date());
                            int r = wechatBindUserInfoMapper.insertWechatBindUserInfo(wechatUserInfo);
                            if (r > 0) {
                                return ResultUtil.result(ResultStatusEnum.S_10004.getStatus(), ResultStatusEnum.S_10004.getMassage());
                            }
                            return ResultUtil.result(ResultStatusEnum.S_10005.getStatus(), ResultStatusEnum.S_10005.getMassage());

                        } else {
                            return ResultUtil.result(ResultStatusEnum.S_10003.getStatus(), ResultStatusEnum.S_10003.getMassage());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("数据库可能存在唯一性问题");
                    }
                } else {
                    return ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
                }
            } else if (StringUtils.isNotBlank(wechatBindParam.getHostPersonEmail())) {
                //查询用户表判断该账号是否存在(机构用户使用邮箱登录)
                int row = wechatBindUserInfoMapper.countOrgenEmail(wechatBindParam.getHostPersonEmail());
                if (row > 0) {
                    try {
                        //如果是登录信息为社会信用码  查询数据库中的社会信用码 所对应的密码进行解密操作
                        String emailPassword = wechatBindUserInfoMapper.emailPassword(wechatBindParam.getHostPersonEmail());
                        //对密码进行解密(首先对用户传过来的密码进行加密，然后与数据库的加密后的密码进行比对)
                        String password = MD5Format.MD5(wechatBindParam.getPassword());
                        //判断密码 是否与数据库一直
                        if (password.equals(emailPassword)) {
                            //密码校验通过 向微信登录表中插入用户信息
                            // 查询用户信息
                            String userId = wechatBindUserInfoMapper.selectemailP(wechatBindParam.getHostPersonEmail());
                            //向微信登录表中插入绑定信息
                            WechatUserInfo wechatUserInfo = new WechatUserInfo();
                            wechatUserInfo.setId(wechatBindParam.getUnionId());
                            wechatUserInfo.setUserId(userId);
                            wechatUserInfo.setUserType("01");
                            wechatUserInfo.setCreateTime(new Date());
                            int r = wechatBindUserInfoMapper.insertWechatBindUserInfo(wechatUserInfo);
                            if (r > 0) {
                                return ResultUtil.result(ResultStatusEnum.S_10004.getStatus(), ResultStatusEnum.S_10004.getMassage());
                            }
                            return ResultUtil.result(ResultStatusEnum.S_10005.getStatus(), ResultStatusEnum.S_10005.getMassage());

                        } else {
                            return ResultUtil.result(ResultStatusEnum.S_10003.getStatus(), ResultStatusEnum.S_10003.getMassage());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("数据库可能存在唯一性问题");
                    }
                } else {
                    return ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
                }
            }
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10006.getStatus(), ResultStatusEnum.S_10006.getMassage());
        }
        return ResultUtil.success();
    }

    @Override
    public ResultUtil bindUserInfoNew(WechatBindParam wechatBindParam) {
        //个人用户绑定
        if (("00").equals(wechatBindParam.getUserType())) {
            //判断微信绑定用户的唯一标识unionId不能为空
            if (StringUtils.isBlank(wechatBindParam.getUnionId())) {
                return ResultUtil.result(ResultStatusEnum.S_10001.getStatus(), ResultStatusEnum.S_10001.getMassage());
            }
            if (wechatBindUserInfoMapper.countUnionId(wechatBindParam.getUnionId())) {
                return ResultUtil.result(ResultStatusEnum.S_10017.getStatus(), ResultStatusEnum.S_10017.getMassage());
            }
            if (CheckUtils.isPhone(wechatBindParam.getWechatUserName())) {
                wechatBindParam.setPhone(wechatBindParam.getWechatUserName());
                //查询用户表判断该账号是否存在
                int row = wechatBindUserInfoMapper.countPhone(wechatBindParam.getPhone());
                if (row > 0) {
                    try {
                        //如果是登录信息为手机号  查询数据库中的手机号 所对应的密码进行解密操作
                        String p = wechatBindUserInfoMapper.queryPassword(wechatBindParam.getPhone());
                        //对密码进行解密(首先对用户传过来的密码进行加密，然后与数据库的加密后的密码进行比对)
                        String password = MD5Format.MD5(wechatBindParam.getPassword());
                        //判断密码 是否与数据库一直
                        if (p.equals(password)) {
                            //密码校验通过 向微信登录表中插入用户信息
                            // 查询用户信息
                            String userId = wechatBindUserInfoMapper.selectUserInfo(wechatBindParam.getPhone());
                            //向微信登录表中插入绑定信息
                            WechatUserInfo wechatUserInfo = new WechatUserInfo();
                            wechatUserInfo.setId(wechatBindParam.getUnionId());
                            wechatUserInfo.setUserId(userId);
                            wechatUserInfo.setUserType("00");
                            wechatUserInfo.setCreateTime(new Date());
                            int r = wechatBindUserInfoMapper.insertWechatBindUserInfo(wechatUserInfo);
                            if (r > 0) {
                                return ResultUtil.result(ResultStatusEnum.S_10004.getStatus(), ResultStatusEnum.S_10004.getMassage());
                            }
                            return ResultUtil.result(ResultStatusEnum.S_10005.getStatus(), ResultStatusEnum.S_10005.getMassage());

                        } else {
                            return ResultUtil.result(ResultStatusEnum.S_10003.getStatus(), ResultStatusEnum.S_10003.getMassage());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("登录方式为手机号，数据库可能存在唯一性问题");
                    }
                } else {
                    return ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
                }

            } else if (CheckUtils.isEmail(wechatBindParam.getWechatUserName())) {
                wechatBindParam.setEmail(wechatBindParam.getWechatUserName());
                //查询用户表判断该账号是否存在
                int row = wechatBindUserInfoMapper.countEmail(wechatBindParam.getEmail());
                if (row > 0) {
                    try {
                        //如果是登录信息为手机号  查询数据库中的手机号 所对应的密码进行解密操作
                        String emailPassword = wechatBindUserInfoMapper.queryEmailPassword(wechatBindParam.getEmail());
                        //对密码进行解密(首先对用户传过来的密码进行加密，然后与数据库的加密后的密码进行比对)
                        String password = MD5Format.MD5(wechatBindParam.getPassword());
                        //判断密码 是否与数据库一直
                        if (emailPassword.equals(password)) {
                            //密码校验通过 向微信登录表中插入用户信息
                            // 查询用户信息
                            String userId = wechatBindUserInfoMapper.selectUserInfoByEmail(wechatBindParam.getEmail());
                            //向微信登录表中插入绑定信息
                            WechatUserInfo wechatUserInfo = new WechatUserInfo();
                            wechatUserInfo.setId(wechatBindParam.getUnionId());
                            wechatUserInfo.setUserId(userId);
                            wechatUserInfo.setUserType("00");
                            wechatUserInfo.setCreateTime(new Date());
                            int r = wechatBindUserInfoMapper.insertWechatBindUserInfo(wechatUserInfo);
                            if (r > 0) {
                                return ResultUtil.result(ResultStatusEnum.S_10004.getStatus(), ResultStatusEnum.S_10004.getMassage());
                            }
                            return ResultUtil.result(ResultStatusEnum.S_10005.getStatus(), ResultStatusEnum.S_10005.getMassage());

                        } else {
                            return ResultUtil.result(ResultStatusEnum.S_10003.getStatus(), ResultStatusEnum.S_10003.getMassage());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("登录方式为邮箱，数据库可能存在唯一性问题");
                    }
                } else {
                    return ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
                }
            } else if (CheckUtils.isIDCard(wechatBindParam.getWechatUserName())) {
                wechatBindParam.setCardNo(wechatBindParam.getWechatUserName());
                //查询用户表判断该账号是否存在
                int row = wechatBindUserInfoMapper.countCardNo(wechatBindParam.getCardNo());
                if (row > 0) {
                    try {
                        //如果是登录信息为身份证号 查询数据库中的身份证号所对应的密码进行解密操作
                        String cardNoPassword = wechatBindUserInfoMapper.queryCardNoPassword(wechatBindParam.getCardNo());
                        //对密码进行解密(首先对用户传过来的密码进行加密，然后与数据库的加密后的密码进行比对)
                        String password = MD5Format.MD5(wechatBindParam.getPassword());
                        //判断密码 是否与数据库一直
                        if (cardNoPassword.equals(password)) {
                            //密码校验通过 向微信登录表中插入用户信息
                            // 查询用户信息
                            String userId = wechatBindUserInfoMapper.selectUserInfoByCardNo(wechatBindParam.getCardNo());
                            //向微信登录表中插入绑定信息
                            WechatUserInfo wechatUserInfo = new WechatUserInfo();
                            wechatUserInfo.setId(wechatBindParam.getUnionId());
                            wechatUserInfo.setUserId(userId);
                            wechatUserInfo.setUserType("00");
                            wechatUserInfo.setCreateTime(new Date());
                            int r = wechatBindUserInfoMapper.insertWechatBindUserInfo(wechatUserInfo);
                            if (r > 0) {
                                return ResultUtil.result(ResultStatusEnum.S_10004.getStatus(), ResultStatusEnum.S_10004.getMassage());
                            }
                            return ResultUtil.result(ResultStatusEnum.S_10005.getStatus(), ResultStatusEnum.S_10005.getMassage());

                        } else {
                            return ResultUtil.result(ResultStatusEnum.S_10003.getStatus(), ResultStatusEnum.S_10003.getMassage());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("登录方式为身份证，数据库可能存在唯一性问题");
                    }
                } else {
                    return ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
                }

            } else if (wechatBindUserInfoMapper.useName(wechatBindParam.getWechatUserName())) {
                try {
                    wechatBindParam.setName(wechatBindParam.getWechatUserName());
                    String namePassword = wechatBindUserInfoMapper.queryUseNamePaaword(wechatBindParam.getName());
                    //对密码进行解密
                    //对密码进行解密(首先对用户传过来的密码进行加密，然后与数据库的加密后的密码进行比对)
                    String password = MD5Format.MD5(wechatBindParam.getPassword());
                    if (namePassword.equals(password)) {
                        //根据用户名查询用户信息
                        String userId = wechatBindUserInfoMapper.selectUserInfoByName(wechatBindParam.getName());
                        WechatUserInfo wechatUserInfo = new WechatUserInfo();
                        wechatUserInfo.setId(wechatBindParam.getUnionId());
                        wechatUserInfo.setUserId(userId);
                        wechatUserInfo.setUserType("00");
                        wechatUserInfo.setCreateTime(new Date());
                        int r = wechatBindUserInfoMapper.insertWechatBindUserInfo(wechatUserInfo);
                        if (r > 0) {
                            return ResultUtil.result(ResultStatusEnum.S_10004.getStatus(), ResultStatusEnum.S_10004.getMassage());
                        }
                        return ResultUtil.result(ResultStatusEnum.S_10005.getStatus(), ResultStatusEnum.S_10005.getMassage());
                    } else {
                        return ResultUtil.result(ResultStatusEnum.S_10003.getStatus(), ResultStatusEnum.S_10003.getMassage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("登录方式为用户名，数据库可能存在唯一性问题");
                }
            } else {
                return ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
            }
        } else if (("01").equals(wechatBindParam.getUserType())) {
            //当登录人为机构用户
            //判断微信绑定用户的唯一标识unionId不能为空
            if (StringUtils.isBlank(wechatBindParam.getUnionId())) {
                return ResultUtil.result(ResultStatusEnum.S_10001.getStatus(), ResultStatusEnum.S_10001.getMassage());
            }
            if (wechatBindUserInfoMapper.countUnionIdOrg(wechatBindParam.getUnionId())) {
                return ResultUtil.result(ResultStatusEnum.S_10017.getStatus(), ResultStatusEnum.S_10017.getMassage());
            }
            if (CheckUtils.isEmail(wechatBindParam.getWechatUserName())) {
                wechatBindParam.setHostPersonEmail(wechatBindParam.getWechatUserName());
                //查询用户表判断该账号是否存在(机构用户使用邮箱登录)
                int row = wechatBindUserInfoMapper.countOrgenEmail(wechatBindParam.getHostPersonEmail());
                if (row > 0) {
                    try {
                        //如果是登录信息为社会信用码  查询数据库中的社会信用码 所对应的密码进行解密操作
                        String emailPassword = wechatBindUserInfoMapper.emailPassword(wechatBindParam.getHostPersonEmail());
                        //对密码进行解密
                        //对密码进行解密(首先对用户传过来的密码进行加密，然后与数据库的加密后的密码进行比对)
                        String password = MD5Format.MD5(wechatBindParam.getPassword());
                        //判断密码 是否与数据库一直
                        if (emailPassword.equals(password)) {
                            //密码校验通过 向微信登录表中插入用户信息
                            // 查询用户信息
                            String userId = wechatBindUserInfoMapper.selectemailP(wechatBindParam.getHostPersonEmail());
                            //向微信登录表中插入绑定信息
                            WechatUserInfo wechatUserInfo = new WechatUserInfo();
                            wechatUserInfo.setId(wechatBindParam.getUnionId());
                            wechatUserInfo.setUserId(userId);
                            wechatUserInfo.setUserType("01");
                            wechatUserInfo.setCreateTime(new Date());
                            int r = wechatBindUserInfoMapper.insertWechatBindUserInfo(wechatUserInfo);
                            if (r > 0) {
                                return ResultUtil.result(ResultStatusEnum.S_10004.getStatus(), ResultStatusEnum.S_10004.getMassage());
                            }
                            return ResultUtil.result(ResultStatusEnum.S_10005.getStatus(), ResultStatusEnum.S_10005.getMassage());

                        } else {
                            return ResultUtil.result(ResultStatusEnum.S_10003.getStatus(), ResultStatusEnum.S_10003.getMassage());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("数据库可能存在唯一性问题");
                    }
                } else {
                    return ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
                }
            } else {
                wechatBindParam.setUnifiedsocialcreditcode(wechatBindParam.getWechatUserName());
                //查询用户表判断该账号是否存在(机构用户社会信用码)
                int row = wechatBindUserInfoMapper.countUnifiedsocialcreditcode(wechatBindParam.getUnifiedsocialcreditcode());
                if (row > 0) {
                    try {
                        //如果是登录信息为社会信用码  查询数据库中的社会信用码 所对应的密码进行解密操作
                        String unifiedsocialcreditcodePassword = wechatBindUserInfoMapper.queryUnifiedsocialcreditcodePassword(wechatBindParam.getUnifiedsocialcreditcode());
                        //对密码进行解密(首先对用户传过来的密码进行加密，然后与数据库的加密后的密码进行比对)
                        String password = MD5Format.MD5(wechatBindParam.getPassword());
                        //判断密码 是否与数据库一直
                        if (unifiedsocialcreditcodePassword.equals(password)) {
                            //密码校验通过 向微信登录表中插入用户信息
                            // 查询用户信息
                            String userId = wechatBindUserInfoMapper.selectUnifiedUserInfo(wechatBindParam.getUnifiedsocialcreditcode());
                            //向微信登录表中插入绑定信息
                            WechatUserInfo wechatUserInfo = new WechatUserInfo();
                            wechatUserInfo.setId(wechatBindParam.getUnionId());
                            wechatUserInfo.setUserId(userId);
                            wechatUserInfo.setUserType("01");
                            wechatUserInfo.setCreateTime(new Date());
                            int r = wechatBindUserInfoMapper.insertWechatBindUserInfo(wechatUserInfo);
                            if (r > 0) {
                                return ResultUtil.result(ResultStatusEnum.S_10004.getStatus(), ResultStatusEnum.S_10004.getMassage());
                            }
                            return ResultUtil.result(ResultStatusEnum.S_10005.getStatus(), ResultStatusEnum.S_10005.getMassage());

                        } else {
                            return ResultUtil.result(ResultStatusEnum.S_10003.getStatus(), ResultStatusEnum.S_10003.getMassage());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("数据库可能存在唯一性问题");
                    }
                } else {
                    return ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
                }
            }

        } else {
            return ResultUtil.result(ResultStatusEnum.S_10016.getStatus(), ResultStatusEnum.S_10016.getMassage());
        }
        return ResultUtil.success();
    }
}