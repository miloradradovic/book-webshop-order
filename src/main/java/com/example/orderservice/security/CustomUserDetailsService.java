package com.example.orderservice.security;

import com.example.orderservice.feign.client.UserDetailsResponse;
import com.example.orderservice.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService {

    @Autowired
    UserFeign userFeign;

    public UserDetailsImpl getUserDetails() {
        UserDetailsResponse response = userFeign.getUserDetails();
        List<GrantedAuthority> grantedAuthorities = response.getRoles()
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UserDetailsImpl(response.getEmail(), response.getPassword(), grantedAuthorities);
    }
}
