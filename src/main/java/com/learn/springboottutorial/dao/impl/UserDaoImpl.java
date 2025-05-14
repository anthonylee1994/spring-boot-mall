package com.learn.springboottutorial.dao.impl;

import com.learn.springboottutorial.dao.UserDao;
import com.learn.springboottutorial.dto.UserRegisterRequest;
import com.learn.springboottutorial.model.User;
import com.learn.springboottutorial.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author anthonylee
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "INSERT INTO user (email, password, created_date, last_modified_date) VALUES (:email, :password, :created_date, :last_modified_date)";

        Map<String, Object> params = new HashMap<>();
        params.put("email", userRegisterRequest.getEmail());
        params.put("password", userRegisterRequest.getPassword());

        Date now = new Date();
        params.put("created_date", now);
        params.put("last_modified_date", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public User getUserById(Integer userId) {
        String sql = "SELECT * FROM user WHERE user_id = :userId";

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql, params, new UserRowMapper());

        if (userList.isEmpty()) {
            return null;
        } else {
            return userList.getFirst();
        }
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = :email";

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        List<User> userList = namedParameterJdbcTemplate.query(sql, params, new UserRowMapper());

        if (userList.isEmpty()) {
            return null;
        } else {
            return userList.getFirst();
        }
    }
}
