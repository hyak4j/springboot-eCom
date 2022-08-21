package com.herny.springbootecom.service.impl;

import com.herny.springbootecom.dao.UserDao;
import com.herny.springbootecom.dto.UserRegisterRequest;
import com.herny.springbootecom.model.User;
import com.herny.springbootecom.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        // 檢查註冊 email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null){
            // 表示 email 已經被註冊
            log.warn("此email {} 已經被註冊", userRegisterRequest.getEmail()); // {} 填入後面參數的值
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 創建帳號
        return userDao.createUser(userRegisterRequest);
    }
}
