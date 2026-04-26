package com.chaisharma.chaimasala.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponseDTO {

    private String id;

    private String customerName;

    private String productName;

    private String weight;

    private int quantity;

    private double totalBill;

    private String address;

    private String pincode;

    private String phone;

    private String paymentType;

    private String status;

    private LocalDateTime orderDate;

    private String estimatedDelivery;
}
