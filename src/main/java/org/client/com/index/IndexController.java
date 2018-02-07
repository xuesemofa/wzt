package org.client.com.index;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {

    @RequestMapping("/")
    public ModelAndView init() {
        return new ModelAndView("redirect:/index");
    }

    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "message", required = false) String message) {
        return new ModelAndView("/index").addObject("message", message);

    }
}
