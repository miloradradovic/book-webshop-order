package com.example.orderservice.feign;

import com.example.orderservice.feign.client.UserDataResponse;
import com.example.orderservice.feign.client.UserDetailsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("auth-server")
public interface UserFeign {

    @GetMapping(value = "api/auth/client/get-user-details")
    UserDetailsResponse getUserDetails();

    @GetMapping(value = "api/auth/client/get-current-user-data")
    UserDataResponse getUserData();
}
