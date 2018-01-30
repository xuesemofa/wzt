package org.client.com.home.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/home")
public class HomeController {
    private final static Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * 登录后主页
     */
    @RequiresAuthentication
    @RequestMapping("/init")
    public ModelAndView init() throws Exception {
        return new ModelAndView("/home/index");
    }

    /**
     * 登录后首页
     *
     * @return
     */
    @RequiresAuthentication
    @RequestMapping("/initSubpage")
    public ModelAndView initSubpage() {
        return new ModelAndView("/home/widgets");
    }

}
