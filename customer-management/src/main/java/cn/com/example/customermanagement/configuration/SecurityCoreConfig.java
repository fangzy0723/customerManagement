package cn.com.example.customermanagement.configuration;

import cn.com.example.customermanagement.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 这个类的目的是为了让SecurityProperties起作用
 * Created by fangzy on 2018/1/26 14:35
 */

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
