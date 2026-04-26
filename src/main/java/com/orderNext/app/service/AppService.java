package com.orderNext.app.service;

import com.orderNext.app.dto.*;

import java.util.List;

public interface AppService {

    void createCustomer(CustomerDTO dto);
    CustomerDTO getCustomer(String id);

    void addAddress(AddressDTO dto);
    List<AddressDTO> getAddresses(String customerId);

    void createProduct(ProductDTO dto);
    List<ProductDTO> getProducts();

    String placeOrder(PlaceOrderRequestDTO dto);
    OrderResponseDTO getOrder(String orderId);
}
