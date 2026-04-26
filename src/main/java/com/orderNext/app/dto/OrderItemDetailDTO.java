package com.orderNext.app.dto;

import lombok.Data;

@Data
public class OrderItemDetailDTO {
    private String productId;
    private String productName;
    private Integer quantity;
    private Double price;
}
