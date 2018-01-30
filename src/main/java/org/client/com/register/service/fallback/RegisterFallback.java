package org.client.com.register.service.fallback;

import org.client.com.register.service.RegisterInterface;
import org.client.com.model.AccountModel;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterFallback implements RegisterInterface {

    @Autowired
    private ResponseResult result;

    @Override
    public ResponseResult register(AccountModel model) {
        result.setSuccess(false);
        result.setCode(404);
        result.setMessage("账户服务断开");
        return result;
    }
}
