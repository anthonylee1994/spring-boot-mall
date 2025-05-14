package com.learn.springboottutorial.dao;

import com.learn.springboottutorial.dto.UserRegisterRequest;
import com.learn.springboottutorial.model.User;

/**
 * @author anthonylee
 */
public interface UserDao {
    User getUserById(Integer userId);

    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
