package com.example.orderservice.feign;

import com.example.orderservice.feign.client.BookCatalogData;
import com.example.orderservice.feign.client.CartClient;
import com.example.orderservice.feign.client.EditInStock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("catalog-server")
public interface BookFeign {

    @PostMapping(value = "api/books/client/get-by-cart")
    List<BookCatalogData> getByCart(@RequestBody CartClient cart);

    @PutMapping(value = "api/books/client/edit-in-stock")
    void editInStock(@RequestBody EditInStock editInStock);
}
