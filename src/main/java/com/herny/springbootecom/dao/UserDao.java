package com.herny.springbootecom.dao;

import com.herny.springbootecom.dto.UserRegisterRequest;
import com.herny.springbootecom.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
