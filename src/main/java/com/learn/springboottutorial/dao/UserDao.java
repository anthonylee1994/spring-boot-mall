package com.learn.springboottutorial.dao;

import com.learn.springboottutorial.dto.UserRegisterRequest;
import com.learn.springboottutorial.model.User;

/**
 * @author anthonylee
 */
public interface UserDao {
    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);
}
