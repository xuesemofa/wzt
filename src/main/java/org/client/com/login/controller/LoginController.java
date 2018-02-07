package org.client.com.login.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.client.com.api.AccountInterface;
import org.client.com.login.model.LoginModel;
import org.client.com.util.base64.Base64Util;
import org.client.com.util.redirect.RedirectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * login
 */
@RestController
public class LoginController {
    private final static Logger log = LoggerFactory
            .getLogger(LoginController.class);

    @Autowired
    DefaultKaptcha defaultKaptcha;

    @Autowired
    private AccountInterface loginInterface;

    /**
     * @param model  LoginModel
     * @param result BindingResult
     * @return ModelAndView
     */
    @PostMapping("/loginIn")
    public ModelAndView loginIn(
            @Valid @ModelAttribute("form") LoginModel model,
            BindingResult result) {
        RedirectUtil redirectUtil = new RedirectUtil();
        if (result.hasErrors()) {
            return new ModelAndView(redirectUtil.getRedirect() + "/index")
                    .addObject("message", result.getFieldError().getDefaultMessage());
        }

        Subject subject = SecurityUtils.getSubject();
        String pwd = Base64Util.encode(model.getPassword());
        UsernamePasswordToken token = new UsernamePasswordToken(
                model.getUsername(), pwd);
        try {
            subject.login(token);
            return new ModelAndView(redirectUtil.getRedirect() + "/home/init");
        } catch (UnknownAccountException lae) {
            token.clear();
            return new ModelAndView(redirectUtil.getRedirect() + "/index")
                    .addObject("message", lae.getMessage());
        } catch (AuthenticationException e) {
            token.clear();
            return new ModelAndView(redirectUtil.getRedirect() + "/index")
                    .addObject("message", "用户或密码不正确");
        }
    }

    @GetMapping("/logout")
    public void logout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            if (subject.isAuthenticated()) {
                subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
                if (log.isDebugEnabled()) {
                    log.debug("用户退出登录");
                }
            }
        } catch (SessionException e) {
            e.printStackTrace();
        }
    }

    // 跳转忘记密码
    @GetMapping("/toForgot")
    public ModelAndView toForgot() {
        return new ModelAndView("/pwd").addObject("xgmmModel", new LoginModel());
    }

    //密码找回
    @PostMapping("/forgot")
    public ModelAndView forgot(@Valid @ModelAttribute("xgmmModel") LoginModel model,
                               BindingResult result) {
        // 表单验证
        if (result.hasErrors()) {
            return new ModelAndView("/pwd").addObject("xgmmModel", model)
                    .addObject("errortextzhmm",
                            result.getFieldError().getDefaultMessage());
        }

        return null;
    }

}
