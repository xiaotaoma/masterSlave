package com.mxt.mysql.masterSlave.controller;

import com.mxt.mysql.masterSlave.model.User;
import com.mxt.mysql.masterSlave.service.UserService;
import com.zkzn.common.utils.request.RequestUtils;
import com.zkzn.common.utils.response.CodeStatus;
import com.zkzn.common.utils.response.PageQuery;
import com.zkzn.common.utils.response.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {
    @Autowired
    private UserService userService;

    /**
     *
     * @return
     */
    @RequestMapping("/user/list")
    @ResponseBody
    public ResultData list() {
        PageQuery pageQuery = new PageQuery(10, 1, new User());
        Integer count = userService.count(pageQuery);
        List<User> list = userService.list(pageQuery);
        Map<String, Object> map = new HashMap<>();
        map.put("count", count);
        map.put("list", list);
        return new ResultData(CodeStatus.RESPONSE_STATUS_SUCCESS, "", map);
    }

    @RequestMapping("/user/create")
    @ResponseBody
    public ResultData create(HttpServletRequest request) {
        String user_name = request.getParameter("user_name");
        String sex = request.getParameter("sex");
        String user_password = request.getParameter("user_password");
        User user = new User();
        user.setUserName(user_name);
        user.setSex(Integer.parseInt(sex));
        user.setUserPassword(user_password);
        userService.create(user);
        return new ResultData(CodeStatus.RESPONSE_STATUS_SUCCESS, "");
    }
}
