package com.learn.springboottutorial.service.impl;

import com.learn.springboottutorial.dao.UserDao;
import com.learn.springboottutorial.dto.UserRegisterRequest;
import com.learn.springboottutorial.model.User;
import com.learn.springboottutorial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author anthonylee
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
