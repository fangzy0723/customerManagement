package cn.com.example.customermanagement.controller;

import cn.com.example.customermanagement.constants.SecurityConstants;
import cn.com.example.customermanagement.domain.ResultBean;
import cn.com.example.customermanagement.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by fangzy on 2018/1/26 12:57
 */
@RestController
public class BrowserSecurityContoller extends BaseController {
    private Logger logger = LoggerFactory.getLogger(BrowserSecurityContoller.class);

    /**
     * spring缓存请求信息的对象
     */
    private RequestCache requestCache = new HttpSessionRequestCache();
    /**
     * spring 提供的处理转发的工具
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 拦截到需要认证的请求，跳转到这个方法
     * 该请求是不是以.html结尾的，是以.html结尾的则跳转到登陆页面，否则返回需要认证的信息
     * 不是登陆的请求返回没有认证的状态码
     * @return
     */
    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultBean requireAuthentication() throws IOException {

        //获取缓存的请求信息
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if(savedRequest != null){
            String redirectUrlPath = savedRequest.getRedirectUrl();
            logger.info("引发跳转到这个方法的请求地址：{}",redirectUrlPath);
            //判断该请求是不是以.html结尾的，是以.html结尾的则跳转到登陆页面，否则返回需要认证的信息
            if(StringUtils.endsWithIgnoreCase(redirectUrlPath,SecurityConstants.DEFAULT_LOGIN_REQUEST_URL_SUFFIX)){
                //转发到登陆页面
                logger.info("securityProperties.getBorwser().getLoginPage():"+securityProperties.getBorwser().getLoginPage());
                //转发到的地址在配置文件中获取
                redirectStrategy.sendRedirect(request,response,securityProperties.getBorwser().getLoginPage());
            }
        }
        return new ResultBean(HttpStatus.UNAUTHORIZED.value(),"认证失败",null);
    }
}
