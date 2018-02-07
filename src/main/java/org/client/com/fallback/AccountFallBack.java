package org.client.com.fallback;

import org.client.com.api.AccountInterface;
import org.client.com.model.AccountModel;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountFallBack implements AccountInterface {

    @Autowired
    ResponseResult<AccountModel> result;

    @Override
    public ResponseResult<AccountModel> getAccount(String account) {
        result.setSuccess(false);
        result.setCode(-404);
        result.setMessage("服务断开");
        result.setData(null);
        return result;
    }

    @Override
    public ResponseResult getById(String id) {
        result.setSuccess(false);
        result.setCode(-404);
        result.setMessage("服务断开");
        result.setData(null);
        return result;
    }

    @Override
    public ResponseResult register(AccountModel model) {
        result.setSuccess(false);
        result.setCode(-404);
        result.setMessage("服务断开");
        result.setData(null);
        return result;
    }
}
