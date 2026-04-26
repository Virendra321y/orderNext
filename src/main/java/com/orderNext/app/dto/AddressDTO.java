package com.orderNext.app.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private Long id;
    private String customerId;
    private String street;
    private String city;
    private String state;
    private String zipCode;
}
