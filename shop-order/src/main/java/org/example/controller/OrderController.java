package org.example.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.OrderApplication;
import org.example.domain.Order;
import org.example.domain.Product;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/order/prod/{pid}")
    public Order order(@PathVariable("pid") Integer pid) {
        int size = discoveryClient.getInstances("service-product").size();
        int i = new Random().nextInt(size);
        ServiceInstance serviceInstance = discoveryClient.getInstances("service-product").get(i);
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort();
        log.info("url:" + url);
        log.info("pid:" + pid);
        Product product = restTemplate.getForObject(url + "/product/" + pid, Product.class);
        log.info("product:" + JSON.toJSONString(product));

        Order order = new Order();
        order.setUid(1);
        order.setUsername("test");
        order.setPid(product.getPid());
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);

        orderService.save(order);
        return order;
    }

    @GetMapping("/order2/prod/{pid}")
    public Order order2(@PathVariable("pid") Integer pid) {
        ServiceInstance serviceInstance = discoveryClient.getInstances("service-product").get(0);
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort();
        log.info("pid:" + pid);
        Product product = restTemplate.getForObject(url + "/product/" + pid, Product.class);
        log.info("product:" + JSON.toJSONString(product));

        Order order = new Order();
        order.setUid(1);
        order.setUsername("test");
        order.setPid(product.getPid());
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);

        orderService.save(order);
        return order;
    }

    @GetMapping("/order1/prod/{pid}")
    public Order order1(@PathVariable("pid") Integer pid) {
        log.info("pid:" + pid);
        Product product = restTemplate.getForObject("http://localhost:8081/product/" + pid, Product.class);
        log.info("product:" + JSON.toJSONString(product));

        Order order = new Order();
        order.setUid(1);
        order.setUsername("test");
        order.setPid(product.getPid());
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);
        orderService.save(order);
        return order;
    }
}

















