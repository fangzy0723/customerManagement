package cn.com.example.customermanagement.service;

import cn.com.example.customermanagement.domain.User;

import java.util.List;

/**
 * Created by fangzy on 2018/1/17 15:10
 */
public interface UserService {

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
    User updateUser(User user);

    /**
     * 根据主键删除用户
     * @param id
     */
    void deleteUser(Long id);
    /**
     * 根据name查询user
     * @param name
     */
    User findByName(String name);
}
