package org.example.authentification.payment.requests;

import lombok.Data;

@Data
public class PaymentRequest {
    double amount;
    Integer sellerId;
    String description;
    Integer userId;
}
