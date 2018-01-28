package cn.com.example.customermanagement.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by fangzy on 2018/1/26 14:06
 */
@ConfigurationProperties(prefix="customer.security")
public class SecurityProperties {

    private BrowserPreperties borwser = new BrowserPreperties();

    public BrowserPreperties getBorwser() {
        return borwser;
    }

    public void setBorwser(BrowserPreperties borwser) {
        this.borwser = borwser;
    }
}
