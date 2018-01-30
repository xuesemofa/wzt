package org.client.com.register.service;

import feign.Param;
import org.client.com.register.service.fallback.RegisterFallback;
import org.client.com.model.AccountModel;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "account", fallback = RegisterFallback.class)
public interface RegisterInterface {
    @RequestMapping(value = "/account/account",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseResult register(@Param("model") AccountModel model);

}
