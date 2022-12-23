package com.example.tz_demo.entity;

public enum TypeEnum {
    PHONE(1,"手机"),
    COMPUTER(2,"电脑"),
    OTHER(3,"其他");

    final int code;
    final String type;

    TypeEnum(int code, String type){
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
