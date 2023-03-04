package com.lxx.cloud.mall.exception;


import com.lxx.cloud.mall.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 林修贤
 * @date 2023/2/11
 * @description 处理统一异常的handler
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e){
        log.error("Default Exception: ", e);
        return ApiRestResponse.error(LxxMallExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(LxxMallException.class)
    @ResponseBody
    public Object handleLxxMallException(LxxMallException e){
        log.error("LxxMallException: ", e);
        return ApiRestResponse.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException: ", e);
        return handleBindingResult(e.getBindingResult());
    }

    private ApiRestResponse handleBindingResult(BindingResult result){
        //把异常处理为对外暴露的提示
        List<String> list = new ArrayList<>();
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            for (int i = 0; i < allErrors.size(); i++) {
                ObjectError objectError = allErrors.get(i);
                String message = objectError.getDefaultMessage();
                list.add(message);
            }
        }
        if (list.size() == 0){
            return ApiRestResponse.error(LxxMallExceptionEnum.REQUEST_PARAM_ERROR);
        }
        return ApiRestResponse.error(LxxMallExceptionEnum.REQUEST_PARAM_ERROR.getCode(), list.toString());
    }
}
