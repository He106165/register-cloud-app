package com.censoft.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberToCarrierMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;



public class PhoneUtil {
    private static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    private static PhoneNumberToCarrierMapper carrierMapper = PhoneNumberToCarrierMapper.getInstance();

    private static PhoneNumberOfflineGeocoder geocoder = PhoneNumberOfflineGeocoder.getInstance();

    /**
     * check phone
     *
     * @param phoneNumber eg" 18012345678"
     * @param countryCode eg "86"
     * @return the result "true" or "false"
     */
    public static boolean checkPhoneNumber(String phoneNumber, Integer countryCode) {

        long phone = Long.parseLong(phoneNumber);

        Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
        pn.setCountryCode(countryCode);
        pn.setNationalNumber(phone);

        return phoneNumberUtil.isValidNumber(pn);
    }

    /**
     * check phone that it brings country code
     *
     * @param phoneNumber eg" +8618012345678"
     * @return the result "true" or "false"
     * @throws NumberParseException handle phone that it can't resolve
     */
    public static boolean checkPhoneNumberBringCountryCode(String phoneNumber) throws NumberParseException {
        Phonenumber.PhoneNumber cn = phoneNumberUtil.parse(phoneNumber, "CN");
        return phoneNumberUtil.isValidNumber(cn);
    }



    public  static void main(String[]args) throws NumberParseException{
        boolean ss = (boolean) PhoneUtil.checkPhoneNumberBringCountryCode("+85283079901");
        //美国  001 9167400000
        boolean meiguo = (boolean) PhoneUtil.checkPhoneNumber("9167400000",1);
       //中国大陆
        //riben 81 09012345678
        boolean riben = (boolean) PhoneUtil.checkPhoneNumber("9012345678",81);
       //中国大陆 86 13693346916
        boolean asss = (boolean) PhoneUtil.checkPhoneNumber("13693346916",86);
        //中国香港 852 61234581
        boolean aa = (boolean) PhoneUtil.checkPhoneNumber("61234581",852);


        System.out.println(riben);
    }

}
