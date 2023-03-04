package com.lxx.cloud.mall.exception;

/**
 * @author 林修贤
 * @date 2023/2/11
 * @description
 */
public enum LxxMallExceptionEnum {
    REQUEST_PARAM_ERROR(10001, "参数有误，请重试"),
    NO_ENUM(10002, "找不到枚举"),
    SYSTEM_ERROR(20000, "系统异常");
    /**
     * 异常码
     */
    Integer code;
    /**
     * 异常信息
     */
    String msg;

    LxxMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
