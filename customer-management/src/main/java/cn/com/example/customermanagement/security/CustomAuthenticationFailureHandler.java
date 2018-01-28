package cn.com.example.customermanagement.security;

import cn.com.example.customermanagement.constants.LoginResponseType;
import cn.com.example.customermanagement.domain.ResultBean;
import cn.com.example.customermanagement.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆失败处理类
 * Created by fangzy on 2018/1/26 14:58
 */
@Component("customAuthenticationFailureHandler")
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        logger.info("登陆失败！");

        if(LoginResponseType.JSON.equals(securityProperties.getBorwser().getLoginResponseType())){
            //响应以json方式
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new ResultBean(HttpStatus.INTERNAL_SERVER_ERROR.value(),exception.getMessage(),null)));
        }else{
            //响应以跳转的方式
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
