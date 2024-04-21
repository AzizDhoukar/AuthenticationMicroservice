package org.example.authentification.payment.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;

    @CreatedDate
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transactionDate;

    private double amount;

    private String description;

    @ManyToOne
    private Wallet  wallet;

    public Transaction( Wallet wallet, double amount, LocalDateTime transactionDate, String description) {
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.description = description;
        this.wallet = wallet;
    }
}
