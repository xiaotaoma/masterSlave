package com.mxt.mysql.masterSlave.dao.extend;

import com.mxt.mysql.masterSlave.model.User;
import com.zkzn.common.utils.response.PageQuery;

import java.util.List;

public interface UserExtendMapper {

    List<User> list(PageQuery pageQuery);

    Integer count(PageQuery pageQuery);
}
