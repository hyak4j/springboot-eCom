package com.herny.springbootecom.service;

import com.herny.springbootecom.dto.UserLoginRequest;
import com.herny.springbootecom.dto.UserRegisterRequest;
import com.herny.springbootecom.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);

    User login(UserLoginRequest userLoginRequest);
}
