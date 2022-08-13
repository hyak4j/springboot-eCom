package com.herny.springbootecom.service.impl;

import com.herny.springbootecom.dao.UserDao;
import com.herny.springbootecom.dto.UserRegisterRequest;
import com.herny.springbootecom.model.User;
import com.herny.springbootecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }
}
