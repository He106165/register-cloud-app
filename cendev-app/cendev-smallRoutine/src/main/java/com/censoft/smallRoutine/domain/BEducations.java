package com.censoft.smallRoutine.domain;

import com.censoft.common.annotation.Excel;

import java.io.Serializable;

/**
 * 这是一个封装查询的类
 *
 */
public class BEducations implements Serializable {

  /**
     * 院校的id
     */
    private  String  id;
    /** 所在国 */
    @Excel(name = "所在国")
    private String country;

    /** 学校简介 */
    @Excel(name = "学校简介")
    private String schoolProfile;
    /** 学校地址 */
    @Excel(name = "学校地址")
    private String schoolAddress;
    /** 证件号 */
    @Excel(name = "证件号")
    private String cardNo;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSchoolProfile() {
        return schoolProfile;
    }

    public void setSchoolProfile(String schoolProfile) {
        this.schoolProfile = schoolProfile;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public BEducations() {
    }

    public BEducations(String id, String pid, String country, String schoolProfile, String schoolAddress) {
        this.id = id;
        this.country = country;
        this.schoolProfile = schoolProfile;
        this.schoolAddress = schoolAddress;
    }

    @Override
    public String toString() {
        return "BEducations{" +
                "id='" + id + '\'' +
                ", country='" + country + '\'' +
                ", schoolProfile='" + schoolProfile + '\'' +
                ", schoolAddress='" + schoolAddress + '\'' +
                '}';
    }
}
