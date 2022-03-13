package org.example.service;

import org.example.domain.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-product")
public interface ProductService {

    @GetMapping("/product/{pid}")
    Product getById(@PathVariable("pid") Integer pid);
}
