package com.learn.springboottutorial.service.impl;

import com.learn.springboottutorial.dao.UserDao;
import com.learn.springboottutorial.dto.UserLoginRequest;
import com.learn.springboottutorial.dto.UserRegisterRequest;
import com.learn.springboottutorial.model.User;
import com.learn.springboottutorial.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author anthonylee
 */
@Service
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null) {
            logger.warn("User already exists with email: {}", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        if (user == null) {
            logger.warn("User not found with email: {}", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (user.getPassword().equals(userLoginRequest.getPassword())) {
            return user;
        } else {
            logger.warn("Invalid password for user with email: {}", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
