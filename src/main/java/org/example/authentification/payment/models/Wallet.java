package org.example.authentification.payment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.authentification.authentification.models.User;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer walletId;

    private Double balance;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany
    @JsonIgnore
    private List<Transaction> transaction;
}
