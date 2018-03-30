package cn.com.example.customermanagement.controller;

import cn.com.example.customermanagement.anno.ResultBeans;
import cn.com.example.customermanagement.domain.ResultBean;
import cn.com.example.customermanagement.domain.User;
import cn.com.example.customermanagement.exception.CustomException;
import cn.com.example.customermanagement.exception.UserControllerException;
import cn.com.example.customermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by fangzy on 2018/1/17 14:22
 */
@RestController
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    /**
     * controller的方法抛出异常时，aop处理的优先级比异常捕获的优先级高
     * 要是想用异常统一处理，就不要使用aop处理这个方法
     * @return
     */
    @ResultBeans
    @GetMapping("/findAll")
    public ResultBean findAll(){
        response.setHeader("Access-Control-Allow-Origin", "*");
        return new ResultBean<List<User>>(userService.findAll());
    }

    /**
     * 添加用户
     * @Valid 注解是为了验证用户参数
     * @RequestBody 注解是为了把接口调用传过来的json参数内容封装到user对象中
     * BindingResult 对象中存放参数验证不通过的信息包含字段和错误信息
     * @param user
     * @param bindingResult
     * @return
     */
    @PostMapping("/addUsers")
    public User addUsers(@Valid @RequestBody User user, BindingResult bindingResult){
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(bindingResult.hasErrors()){
            StringBuffer sb = new StringBuffer();
            bindingResult.getAllErrors().stream().forEach(error -> {
                //error的类型是FieldError，可以从FieldError中获取到错误的字段和错误信息
                //FieldError fieldError = (FieldError)error;
                //System.out.println(fieldError.getField()+"   "+fieldError.getDefaultMessage());
                sb.append(error.getDefaultMessage()+"；");
                System.out.println(error.getDefaultMessage());
            });
            throw new CustomException(10,sb.toString());
        }
        userService.addUser(user);
        System.out.println(user.toString());
        return user;
    }
    @PutMapping("/updateUserById")
    public User updateUserById(@Valid @RequestBody User user, BindingResult bindingResult){
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(bindingResult.hasErrors()){
            StringBuffer sb = new StringBuffer();
            bindingResult.getAllErrors().stream().forEach(error -> {
                sb.append(error.getDefaultMessage()+"；");
            });
            throw new CustomException(10,sb.toString());
        }
        return userService.updateUser(user);
    }

    /**
     * 根据name查询用户
     * @param name
     * @return
     */
    @GetMapping("/getUser/{name}")
    public User getUser(@PathVariable String name){
        return userService.findByName(name);
    }

    /**
     * 根据id删除用户
     * 如果传过来的id不是数字类型的则会报404
     * id必须是数字类型的
     * @param id
     */
    @DeleteMapping("/deleteUser/{id:\\d+}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    /**
     * 获取登陆用户的全部信息
     * @param authentication
     */
    @GetMapping("/LoginUserDetailsAll")
    public Authentication LoginUserDetailsAll(Authentication authentication){
        return authentication;
    }
    /**
     * 获取登陆用户的详细信息
     * @param userDetails
     */
    @GetMapping("/LoginUserDetails")
    public UserDetails LoginUserDetails(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
    }
}
