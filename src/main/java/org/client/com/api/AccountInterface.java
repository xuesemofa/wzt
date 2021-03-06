package org.client.com.api;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.client.com.model.AccountModel;
import org.client.com.util.resultJson.ResponseResult;

public interface AccountInterface {

    @RequestLine("GET /account/acc?account={account}")
    ResponseResult<AccountModel> getAccount(@Param("account") String account);

    @RequestLine("GET /account/id?id={id}")
    ResponseResult getById(@Param("id") String id);

    @Headers("Content-Type:application/json")
    @RequestLine("POST /account/account")
    ResponseResult register(@Param("model") AccountModel model);
}
