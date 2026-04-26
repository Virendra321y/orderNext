package com.orderNext.app.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private String productId;
    private String productName;
    private String category;
    private Double price;
}
