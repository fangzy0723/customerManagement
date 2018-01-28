package cn.com.example.customermanagement.properties;

import cn.com.example.customermanagement.constants.LoginResponseType;

/**
 * Created by fangzy on 2018/1/26 14:10
 */
public class BrowserPreperties {

    private String loginPage="/page/login.html";
    //默认记住我的时间，秒数
    private int rememberMeSeconds = 3600;

    private LoginResponseType loginResponseType = LoginResponseType.JSON;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginResponseType getLoginResponseType() {
        return loginResponseType;
    }

    public void setLoginResponseType(LoginResponseType loginResponseType) {
        this.loginResponseType = loginResponseType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }
}
