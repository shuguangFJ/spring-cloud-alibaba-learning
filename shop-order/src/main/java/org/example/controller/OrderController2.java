package org.example.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.Order;
import org.example.domain.Product;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController2 {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/prod/{pid}")
    public Order order(@PathVariable("pid") Integer pid) {

        log.info("pid:" + pid);
        Product product = productService.getById(pid);
        log.info("product:" + JSON.toJSONString(product));

        Order order = new Order();
        order.setUid(1);
        order.setUsername("test");
        order.setPid(product.getPid());
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);

//        orderService.save(order);
        return order;
    }

    @GetMapping("/order/message")
    public String message(){
        return "message";
    }
}
