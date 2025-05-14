package com.learn.springboottutorial.service;

import com.learn.springboottutorial.dto.UserLoginRequest;
import com.learn.springboottutorial.dto.UserRegisterRequest;
import com.learn.springboottutorial.model.User;
import jakarta.validation.Valid;

/**
 * @author anthonylee
 */
public interface UserService {
    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User login(UserLoginRequest userLoginRequest);
}
