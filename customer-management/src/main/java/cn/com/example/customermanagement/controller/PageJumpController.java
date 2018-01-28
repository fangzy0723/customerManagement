package cn.com.example.customermanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用于页面跳转的controller
 * Created by fangzy on 2018/1/26 16:06
 */
@Controller
public class PageJumpController extends BaseController{

    @GetMapping("/")
    public String toHelloWord(){
        response.setHeader("Access-Control-Allow-Origin", "*");
        return "index";
    }
}
