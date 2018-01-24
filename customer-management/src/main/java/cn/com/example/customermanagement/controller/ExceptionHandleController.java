package cn.com.example.customermanagement.controller;

import cn.com.example.customermanagement.exception.CustomException;
import cn.com.example.customermanagement.exception.UserControllerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理
 * Created by fangzy on 2018/1/24 10:29
 * Controller层抛出的异常在这统一捕获处理，返回给用户统一格式的内容
 */
@ControllerAdvice
public class ExceptionHandleController {

    //这个方法只处理controller抛出来的CustomException异常
    //@ExceptionHandler注解指定处理的异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    //指定返回的状态码
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public Map<String,Object> handleCustomException(CustomException ce){
        Map<String,Object> map = new HashMap<>();
        map.put("code",ce.getCode());
        map.put("message",ce.getMessage());
        return map;
    }

    //这个方法只处理controller抛出来的UserControllerException异常
    //@ExceptionHandler注解指定处理的异常
    @ExceptionHandler(UserControllerException.class)
    @ResponseBody
    //指定返回的状态码
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handleUserControllerException(UserControllerException ce){
        Map<String,Object> map = new HashMap<>();
        map.put("code",ce.getCode());
        map.put("message",ce.getMessage());
        return map;
    }
}
