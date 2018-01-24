package cn.com.example.customermanagement.valid;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by fangzy on 2018/1/24 15:50
 */
//指定该注解可以加到的位置
@Target({ElementType.METHOD,ElementType.FIELD})
//执行的时机
@Retention(RetentionPolicy.RUNTIME)
//指定这个自定义注解的检验类
@Constraint(validatedBy = CheckUserNameValidator.class)
public @interface CheckUserName {
    String message() default "用户名验证不通过";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
