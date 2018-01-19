package cn.com.example.customermanagement.controller;

import cn.com.example.customermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by fangzy on 2018/1/17 14:22
 */
@Controller
public class HelloWordController extends BaseController{

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String helloword(){
        return "page/index";
    }


}
