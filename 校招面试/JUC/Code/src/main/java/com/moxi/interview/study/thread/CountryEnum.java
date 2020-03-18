package com.moxi.interview.study.thread;

import lombok.Getter;

/**
 * 城市枚举类
 */
public enum CountryEnum {
    One(1, "齐"),Two(2, "楚"),THREE(3, "燕"),FOUR(4, "赵"),FIVE(5, "魏"),SIX(6, "韩");

    @Getter private Integer retCode;

    @Getter private String retMessage;

    CountryEnum(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public static CountryEnum forEachCountryEnum(int index) {
        CountryEnum [] myArray = CountryEnum.values();
        for (CountryEnum countryEnum : myArray) {
            if(index == countryEnum.getRetCode()) {
                return countryEnum;
            }
        }
        return null;
    }
}
