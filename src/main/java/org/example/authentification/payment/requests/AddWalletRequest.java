package org.example.authentification.payment.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import org.example.authentification.payment.models.Seller;
import org.example.authentification.payment.models.Transaction;

import java.util.List;

@Getter
public class AddWalletRequest {
    private Double balance;

    private Integer sellerId;
}
