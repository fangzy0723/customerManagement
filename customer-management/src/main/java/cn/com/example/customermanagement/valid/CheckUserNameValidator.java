package cn.com.example.customermanagement.valid;

import cn.com.example.customermanagement.domain.User;
import cn.com.example.customermanagement.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * CheckUserName:自定义注解名
 * Created by fangzy on 2018/1/24 15:56
 */
public class CheckUserNameValidator implements ConstraintValidator<CheckUserName, Object> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(CheckUserName constraintAnnotation) {
        System.out.println("验证用户名的自定义Validator初始化...");
    }

    /**
     * 验证用户名是否被使用，根据传过来的用户名查询数据库，能查到说明被使用，查不到没被使用
     * @param value
     * @param context
     * @return true:用户名没有被使用表示验证通过  false：用户名已被使用表示验证不通过
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        System.out.println("被验证的用户名:"+value);
        User user = userService.findByName(value.toString());
        if(user==null||StringUtils.isEmpty(user.getName())){
            return true;
        }
        return false;
    }
}
