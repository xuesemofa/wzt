package org.client.com;

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
import org.client.com.api.AccountInterface;
import org.client.com.login.controller.LoginController;
import org.client.com.login.model.LoginModel;
import org.client.com.model.AccountModel;
import org.client.com.util.resultJson.ResponseResult;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class MyShiroRealm extends AuthorizingRealm {

    private final static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Resource
    private AccountInterface service;
    @Resource
    private LoginController login;

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
        Map<String, Object> map = login.getLanders();
        if (map == null)
            return info;
        if ((Integer) map.get("types") == 0) {
            info.addRole("admin");
        } else if ((Integer) map.get("types") == 1) {
            info.addRole("admins");
        } else {
//            普通账户角色
            info.addRole("user");
        }
        return info;
    }

    /**
     * 登陆验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {

//        // 用户名
        String username = (String) authcToken.getPrincipal();
        ResponseResult<AccountModel> object = service.getAccount(username);
        JSONObject json = null;
        if (object.isSuccess())
            json = (JSONObject) JSONObject.wrap(object.getData());
        try {
            if (json == null || !json.has("account"))
//            用户未找到异常
                throw new UnknownAccountException("账户或密码不正确!");

            LoginModel user = new LoginModel();
            String account = json.getString("account");
            user.setUsername(account);
            String pwd = json.getString("password");
            user.setPassword(pwd);
            // 简单验证
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
// 当验证都通过后，把用户信息放在session里
            Session session = SecurityUtils.getSubject().getSession();
            Map<String, Object> map = new HashMap<>();
            map.put("account", user.getUsername());
            String uuid = json.getString("uuid");
            map.put("uuid", uuid);
            int acctype = json.getInt("acctype");
            map.put("types", acctype);
            session.setAttribute("map", map);
            return info;
        } catch (Exception e) {
            throw new UnknownAccountException("用户或密码不正确!!!");
        }
    }

}
