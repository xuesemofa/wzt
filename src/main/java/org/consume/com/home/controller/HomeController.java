package org.consume.com.home.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Random;

@RestController
@RequestMapping("/home")
public class HomeController {
    private final static Logger logger = LoggerFactory.getLogger(HomeController.class);

//    @Autowired
//    private HeatService service;

    /**
     * 登录后主页
     */
    @RequiresAuthentication
    @RequestMapping("/init")
    public ModelAndView init() throws Exception {
//所有的换热站
//        List<HeatModel> list = service.findAll();
        return new ModelAndView("/home/index");
//                .addObject("heats", list);

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

    @RequestMapping("/getCpuRatioForWindows")
    public int getCpuRatioForWindows() throws Exception {
        int max = 300;
        int min = 1;
        Random random = new Random();

        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

}
