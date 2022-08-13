package com.herny.springbootecom.dao;

import com.herny.springbootecom.dto.UserRegisterRequest;
import com.herny.springbootecom.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
