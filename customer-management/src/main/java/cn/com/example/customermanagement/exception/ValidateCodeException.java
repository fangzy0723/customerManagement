package cn.com.example.customermanagement.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * Created by fangzy on 2018/1/26 22:23
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String detail) {
        super(detail);
    }
}
