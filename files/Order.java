package com.chaisharma.chaimasala.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String customerName;

    private String productId;      // reference to Product._id

    private String productName;    // snapshot at time of order

    private String weight;         // e.g. "250g", "500g"

    private int quantity;

    private double totalBill;

    private String address;

    private String pincode;

    private String phone;

    private String paymentType = "Cash on Delivery";

    // PLACED → CONFIRMED → OUT_FOR_DELIVERY → DELIVERED  (or CANCELLED)
    private String status = "PLACED";

    private LocalDateTime orderDate = LocalDateTime.now();

    private String estimatedDelivery = "30-45 Minutes";
}
