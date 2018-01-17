package cn.com.example.customermanagement.repository;


import cn.com.example.customermanagement.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fangzy on 2017/9/2 17:15
 */
@Repository
public interface UserMapper {

    /**
     * 根据name查询user
     * @param name
     */
    User findByName(String name);

    /**
     * 查询所有用户列表
     * @return
     */
    List<User> findAll();

    /**
     * 添加一个用户
     * @param user
     */
    void addUser(User user);

    /**
     * 根据主键修改用户
     * @param user
     */
    void updateUser(User user);

    /**
     * 根据主键删除用户
     * @param id
     */
    void deleteUser(Long id);
}
