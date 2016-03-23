package com.runeventsparser.bom;

import java.io.UnsupportedEncodingException;

/**
 * Created by ������� on 31.01.2016.
 */
public enum Sex {
    MALE,FEMALE;

    public static Sex convertSex(Character sex){
        if (sex.equals('Ж') || sex.equals('ж')) return Sex.FEMALE;
        else return Sex.MALE;
    }

    public static String convertSexToUtf(String sex){
        try{
            sex=new String(sex.getBytes(),"utf-8");
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return sex;
    }
}
