package cn.com.example.customermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fangzy on 2018/1/17 15:15
 */
public class BaseController {
    @Autowired
    public HttpServletRequest request;
    @Autowired
    public HttpServletResponse response;
}
