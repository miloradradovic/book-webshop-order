package com.example.orderservice.feign;

import com.example.orderservice.feign.client.UserDataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("auth-server")
public interface UserFeign {

    @GetMapping(value = "api/users/client/data-for-order")
    UserDataResponse getDataForOrder();
}
