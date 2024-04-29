package org.example.authentification.payment.requests;

import lombok.Getter;

@Getter
public class AddWalletRequest {
    private Double balance;

    private Integer userId;
}
