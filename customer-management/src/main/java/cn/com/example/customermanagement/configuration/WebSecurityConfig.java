package cn.com.example.customermanagement.configuration;

import cn.com.example.customermanagement.constants.SecurityConstants;
import cn.com.example.customermanagement.filter.ValidateCodeFilter;
import cn.com.example.customermanagement.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * security的配置类
 * Created by fangzy on 2018/1/19 14:03
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private DataSource masterDataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        //会在数据库创建一张表，保存token、用户名等信息到新创建的表中
        jdbcTokenRepository.setDataSource(masterDataSource);
        //true表示会创建一张记录用户token的表，已经存在会启动报错，可以在数据库客户端执行这段sql脚本自行创建表
        //create table persistent_logins (username varchar(64) not null, series varchar(64) primary key, token varchar(64) not null, last_used timestamp not null)
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //把写的验证验证码的过滤器添加到UsernamePasswordAuthenticationFilter之前
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);

        http
            .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        securityProperties.getBorwser().getLoginPage(),
                        "/code/image"
                ).permitAll()
                //参考示例：设置LoginUserDetailsAll的get请求是需要ADMIN权限才能访问的（权限规则变是可以这样设置）
                .antMatchers(HttpMethod.GET,"LoginUserDetailsAll").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                //当请求需要认证时，跳转到这个请求地址
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL).permitAll()
                //告诉security不用security默认/login处理登陆请求
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                //登陆成功处理的方法
                .successHandler(customAuthenticationSuccessHandler)
                //登陆失败处理的方法
                .failureHandler(customAuthenticationFailureHandler)
                .and()
            //记住我功能配置
            .rememberMe()
                //token仓库
                .tokenRepository(persistentTokenRepository())
                //保存时间，单位秒
                .tokenValiditySeconds(securityProperties.getBorwser().getRememberMeSeconds())
                //查询用户登录信息
                .userDetailsService(userDetailsService)
                .and()
            .logout()
                .permitAll()
            .and()
            .csrf().disable();
    }
}
