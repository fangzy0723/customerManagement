package cn.com.example.customermanagement.filter;

import cn.com.example.customermanagement.constants.SecurityConstants;
import cn.com.example.customermanagement.controller.CreateImageController;
import cn.com.example.customermanagement.domain.ImageCode;
import cn.com.example.customermanagement.exception.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fangzy on 2018/1/26 22:25
 */
public class ValidateCodeFilter extends OncePerRequestFilter{

    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SessionStrategy sessionStrategy =  new HttpSessionSessionStrategy();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,request.getRequestURI())
                &&  StringUtils.endsWithIgnoreCase(request.getMethod(),"POST")){
            try{
                validateCode(new ServletWebRequest(request));
            }catch (ValidateCodeException e){
                authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    public void validateCode(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        //拿到session中的图片信息
        ImageCode imageCodeSession = (ImageCode)sessionStrategy.getAttribute(servletWebRequest, CreateImageController.SESSION_KEY);
        //从request中获取到输入的验证码
        String imageCodeRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(),"imageCode");
        //验证输入的验证码是不是为空
        if(StringUtils.isBlank(imageCodeRequest)){
            throw new ValidateCodeException("验证码不能为空");
        }
        //验证session中存放的验证码图片信息是不是null
        if(imageCodeSession == null){
            throw new ValidateCodeException("验证码不存在");
        }
        //判断验证码是不是过期
        if(imageCodeSession.isExpireTime()){
            sessionStrategy.removeAttribute(servletWebRequest,CreateImageController.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期");
        }
        //判断输入的验证码是否跟session中缓存的相同
        if(!StringUtils.endsWith(imageCodeRequest,imageCodeSession.getCode())){
            throw new ValidateCodeException("验证码输入不正确");
        }
        sessionStrategy.removeAttribute(servletWebRequest,CreateImageController.SESSION_KEY);
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
}
