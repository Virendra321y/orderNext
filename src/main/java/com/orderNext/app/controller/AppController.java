package com.orderNext.app.controller;

import com.orderNext.app.dto.*;
import com.orderNext.app.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    private AppService service;

    // CUSTOMER
    @PostMapping("/customers")
    public String createCustomer(@RequestBody CustomerDTO dto) {
        service.createCustomer(dto);
        return "Customer created";
    }

    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable String id) {
        return service.getCustomer(id);
    }

    // ADDRESS
    @PostMapping("/addresses")
    public String addAddress(@RequestBody AddressDTO dto) {
        service.addAddress(dto);
        return "Address added";
    }

    @GetMapping("/addresses/{customerId}")
    public List<AddressDTO> getAddresses(@PathVariable String customerId) {
        return service.getAddresses(customerId);
    }

    // PRODUCT
    @PostMapping("/products")
    public String createProduct(@RequestBody ProductDTO dto) {
        service.createProduct(dto);
        return "Product created";
    }

    @GetMapping("/products")
    public List<ProductDTO> getProducts() {
        return service.getProducts();
    }

    // ORDER
    @PostMapping("/orders")
    public String placeOrder(@RequestBody PlaceOrderRequestDTO dto) {
        return service.placeOrder(dto);
    }

    @GetMapping("/orders/{id}")
    public OrderResponseDTO getOrder(@PathVariable String id) {
        return service.getOrder(id);
    }
}
