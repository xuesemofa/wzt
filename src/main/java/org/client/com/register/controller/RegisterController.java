package org.client.com.register.controller;

import org.client.com.model.AccountModel;
import org.client.com.register.model.RegisterModel;
import org.client.com.register.service.RegisterInterface;
import org.client.com.util.redirect.RedirectUtil;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterInterface registerInterface;
    @Autowired
    private ResponseResult result;

    @RequestMapping(value = "/yh", method = RequestMethod.GET)
    public ModelAndView init() {
        return new ModelAndView("/register")
                .addObject("model", new RegisterModel());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@Valid @ModelAttribute("model") RegisterModel model, BindingResult bindingResult) {
        //数据验证
        if (bindingResult.hasErrors())
            return new ModelAndView("/register")
                    .addObject("model", model)
                    .addObject("message", bindingResult.getFieldError().getDefaultMessage());
//两次输入的密码是否一至
        if (!model.isPass())
            return new ModelAndView("/register")
                    .addObject("model", model)
                    .addObject("message", "两次输入的密码不一致");

        AccountModel accountModel = new AccountModel();
        accountModel.setAccount(model.getAccount());
        accountModel.setAcctype(1);
        accountModel.setPassword(model.getPassword());
        accountModel.setTimes(System.currentTimeMillis());

        ResponseResult result = registerInterface.register(accountModel);
        if (result.isSuccess())
            return new ModelAndView(RedirectUtil.REDIRECT + "/register/registerOK");
        else
            return new ModelAndView("/register")
                    .addObject("model", model)
                    .addObject("message", result.getCode() == 501 ? "该账户已注册" : result.getMessage());
    }

    @RequestMapping(value = "/registerOK", method = RequestMethod.GET)
    public ModelAndView registerOK() {
        return new ModelAndView("/registerOK");
    }
}
