package org.example.authentification.payment.controllers;

import lombok.RequiredArgsConstructor;
import org.example.authentification.payment.Exeptions.TranscationNotFound;
import org.example.authentification.payment.Exeptions.UserNotFound;
import org.example.authentification.payment.models.User;
import org.example.authentification.payment.models.Transaction;
import org.example.authentification.payment.models.Wallet;
import org.example.authentification.payment.repositories.TransactionRepository;
import org.example.authentification.payment.requests.PaymentRequest;
import org.example.authentification.payment.requests.SplitPaymentRequest;
import org.example.authentification.payment.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final TransactionRepository transactionRepository;
    private final PaymentService paymentService;
    @GetMapping()
    public ResponseEntity<List<Transaction>> getAll() {
        return ResponseEntity.ok(transactionRepository.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(transactionRepository.findById(id).orElseThrow(TranscationNotFound::new));
    }

    @PostMapping("/create")
    public ResponseEntity<Transaction> createPayment(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }
    @PostMapping("/createSplit")
    public ResponseEntity<List<Transaction>> createSplitPayment(@RequestBody SplitPaymentRequest request) {
        return ResponseEntity.ok(paymentService.createSplitPayment(request));
    }
}
