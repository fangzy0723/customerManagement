package cn.com.example.customermanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义用户验证处理逻辑
 * 处理登陆请求获取用户信息
 * Created by fangzy on 2018/1/26 10:17
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * username：用户登陆时所用输入的用户名，实际登陆业务中需要使用这个用户名去数据库查询用户信息进用户状态
     *
     * 用户注册时也需要使用PasswordEncoder的encode(String)方法给用户的密码加密，将加密之后的密码放到数据库
     * security验证登陆密码默认的策略是将登陆时的密码用PasswordEncoder的encode(String)加密之后跟从数据库中查出来的比对
     * 相同则验证通过，不相同则验证失败，所以保存的密码要使用相同算法得到的密码，以免验证不通过
     *
     * User接口中的isEnabled、isAccountNonExpired、isAccountNonLocked、isCredentialsNonExpired方法有一个为false则验证不通过
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //此处业务逻辑：
        //1：根据用户名查询数据库
        //2：获取到注册时保存到数据库的密码
        String password = passwordEncoder.encode("123456");
        //3: isEnabled  用户是否可用
        //4: isAccountNonExpired  用户是否失效
        //5: isAccountNonLocked   用户是否被锁定
        //6: isCredentialsNonExpired  用户正式是否可用
        return new User(username,password,true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
