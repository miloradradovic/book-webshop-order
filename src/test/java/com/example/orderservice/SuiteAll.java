package com.example.orderservice;

import com.example.orderservice.api.OrderControllerUnitTests;
import com.example.orderservice.service.OrderServiceUnitTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        OrderControllerUnitTests.class, OrderServiceUnitTests.class
})
public class SuiteAll {
}
