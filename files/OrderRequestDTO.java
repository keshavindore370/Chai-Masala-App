package com.chaisharma.chaimasala.DTO;

import lombok.Data;

@Data
public class OrderRequestDTO {

    private String productId;

    private String weight;          // e.g. "250g", "500g"

    private int quantity;

    private String address;

    private String pincode;

    private String phone;

    private String paymentType;     // e.g. "Cash on Delivery", "UPI"
}
