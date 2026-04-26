package com.orderNext.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderRequestDTO {
    private String customerId;
    private Long addressId;
    private List<OrderItemDTO> items;
}
