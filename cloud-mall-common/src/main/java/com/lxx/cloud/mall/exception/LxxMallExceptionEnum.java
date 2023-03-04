package com.lxx.cloud.mall.exception;

/**
 * @author 林修贤
 * @date 2023/2/11
 * @description
 */
public enum LxxMallExceptionEnum {
    REQUEST_PARAM_ERROR(10001, "参数有误，请重试"),
    NO_ENUM(10002, "找不到枚举"),
    NEED_USER_NAME(10003, "需要用户名"),
    NEED_PASSWORD(10004, "需要密码"),
    PASSWORD_TOO_SHORT(10005, "密码不能太短"),
    NAME_EXISTS(10006, "用户名不能重复"),
    INSERT_FAILED(10007, "插入失败，请重试"),
    WRONG_PASSWORD(10008, "密码错误"),
    UPDATE_FAILED(10009, "更新错误"),
    NEED_LOGIN(100010, "请登录"),
    NEED_ADMIN(100011, "非管理员，无法操作"),
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
