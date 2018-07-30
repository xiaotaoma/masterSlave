package com.mxt.mysql.masterSlave.service;

import com.mxt.mysql.masterSlave.dao.UserMapper;
import com.mxt.mysql.masterSlave.dao.extend.UserExtendMapper;
import com.mxt.mysql.masterSlave.model.User;
import com.zkzn.common.utils.response.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserExtendMapper userExtendMapper;

    @Transactional
    public void create(User user) {
        userMapper.insert(user);
    }

    @Transactional
    public void update(User user) {
        userMapper.update(user);
    }

    public User getByPid(int userId) {
        return userMapper.getByPid(userId);
    }

    public List<User> list(PageQuery pageQuery) {
        return userExtendMapper.list(pageQuery);
    }

    public Integer count(PageQuery pageQuery) {
        return userExtendMapper.count(pageQuery);
    }
}
