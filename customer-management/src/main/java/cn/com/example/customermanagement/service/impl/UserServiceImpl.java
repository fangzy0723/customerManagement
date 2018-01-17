package cn.com.example.customermanagement.service.impl;

import cn.com.example.customermanagement.domain.User;
import cn.com.example.customermanagement.repository.UserMapper;
import cn.com.example.customermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fangzy on 2018/1/17 15:11
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
