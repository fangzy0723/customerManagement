package cn.com.example.customermanagement.constants;

/**
 * Created by fangzy on 2018/1/26 13:37
 */
public interface SecurityConstants {

    /**
     * 当请求需要身份认证时，默认跳转的url
     */
    public static final String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";
    /**
     * 默认的用户名密码登录请求处理url
     */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/login";

    public static final String DEFAULT_LOGIN_REQUEST_URL_SUFFIX = ".html";

}
