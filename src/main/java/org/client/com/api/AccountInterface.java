package org.client.com.api;

import feign.Param;
import org.client.com.fallback.AccountFallBack;
import org.client.com.model.AccountModel;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "http://localhost:9002", name = "account", fallback = AccountFallBack.class)
public interface AccountInterface {
    @RequestMapping(value = "/account/account/acc", method = RequestMethod.GET)
    ResponseResult<AccountModel> getAccount(@Param(value = "account") String account);

    @RequestMapping(value = "/account/account/id", method = RequestMethod.GET)
    ResponseResult getLanders(@Param(value = "id") String id);

    @RequestMapping(value = "/account/account",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseResult register(@Param("model") AccountModel model);
}
