package org.client.com.login.service;

import feign.Param;
import org.client.com.login.service.fallback.LoginFallBack;
import org.client.com.model.AccountModel;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "account", fallback = LoginFallBack.class)
public interface LoginInterface {
    @RequestMapping(value = "/account/acc", method = RequestMethod.GET)
    ResponseResult<AccountModel> getAccount(@Param(value = "account") String account);

    @RequestMapping(value = "/account/id", method = RequestMethod.GET)
    ResponseResult getLanders(@Param(value = "id") String id);
}
