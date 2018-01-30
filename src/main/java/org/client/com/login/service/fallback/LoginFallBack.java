package org.client.com.login.service.fallback;

import org.client.com.login.service.LoginInterface;
import org.client.com.model.AccountModel;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginFallBack implements LoginInterface {

    @Autowired
    ResponseResult<AccountModel> result;

    @Override
    public ResponseResult<AccountModel> getAccount(String account) {
        result.setSuccess(false);
        result.setCode(404);
        result.setMessage("账户服务断开");
        return result;
    }

    @Override
    public ResponseResult getLanders(String id) {
        result.setSuccess(false);
        result.setCode(404);
        result.setMessage("账户服务断开");
        return result;
    }
}
