package com.orderNext.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponseDTO {
    private String orderId;
    private String customerId;
    private Long addressId;
    private Double totalAmount;
    private List<OrderItemDetailDTO> items;
}
