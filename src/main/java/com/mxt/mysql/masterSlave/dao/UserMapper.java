package com.mxt.mysql.masterSlave.dao;

import com.mxt.mysql.masterSlave.model.User;

public interface UserMapper{

    int insert(User user);

    int update(User user);

    User getByPid(Integer userId);

}