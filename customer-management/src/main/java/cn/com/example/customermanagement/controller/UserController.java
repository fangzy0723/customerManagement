package cn.com.example.customermanagement.controller;

import cn.com.example.customermanagement.anno.ResultBeans;
import cn.com.example.customermanagement.domain.ResultBean;
import cn.com.example.customermanagement.domain.User;
import cn.com.example.customermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by fangzy on 2018/1/17 14:22
 */
@RestController
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @ResultBeans
    @RequestMapping("/findAll")
    public ResultBean findAll(){
        response.setHeader("Access-Control-Allow-Origin", "*");
        return new ResultBean<List<User>>(userService.findAll());
    }
}
