package com.example.common.enums;

public enum StatusEnums {
    NOT_AUDIT("待审核"),
    APPROVE("通过"),
    REJECT("拒绝");

    public String value;

    StatusEnums(String value) {
        this.value = value;
    }
}
