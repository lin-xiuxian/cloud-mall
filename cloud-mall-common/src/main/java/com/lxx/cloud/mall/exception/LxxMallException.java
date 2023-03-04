package com.lxx.cloud.mall.exception;

/**
 * @author 林修贤
 * @date 2023/2/11
 * @description 统一异常
 */
public class LxxMallException extends RuntimeException {
    private final Integer code;
    private final String message;

    public LxxMallException(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public LxxMallException(LxxMallExceptionEnum exceptionEnum){
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
