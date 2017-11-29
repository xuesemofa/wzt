package org.consume.com.user.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.consume.com.user.service.UserInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * user
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final static Logger log = LoggerFactory
            .getLogger(UserController.class);

    @Autowired
    private UserInterface service;

    /**
     * 获取当前登录的用户，主要用于shiro
     *
     * @return
     */
    @RequiresRoles("admin")
    @HystrixCommand(fallbackMethod = "admins_error")
    @GetMapping("/admins")
    public String get() {
        String s = service.get();
        if (s.equals("200"))
            return "添加成功";
        else if (s.equals("501"))
            return "账户已存在";
        return "添加失败";
    }

    public String admins_error() {
        return "账户接口断开";
    }
}
