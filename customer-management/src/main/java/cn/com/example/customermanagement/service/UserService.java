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
}
