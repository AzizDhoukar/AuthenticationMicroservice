package org.example.authentification.payment.requests;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SplitPaymentRequest {
    String description;
    Integer sellerId;
    private List<SplitDetail> splitDetails;

    @Data
    public static class SplitDetail {
        private double amount;
        private Integer userId;
    }
}
