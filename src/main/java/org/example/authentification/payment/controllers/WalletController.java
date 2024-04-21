package org.example.authentification.payment.controllers;

import lombok.RequiredArgsConstructor;
import org.example.authentification.payment.Exeptions.WalletNotFound;
import org.example.authentification.payment.models.Transaction;
import org.example.authentification.payment.models.Wallet;
import org.example.authentification.payment.repositories.WalletRepository;
import org.example.authentification.payment.requests.BankToWalletTransferRequest;
import org.example.authentification.payment.requests.TransactionRequest;
import org.example.authentification.payment.services.SellerService;
import org.example.authentification.payment.services.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    private final WalletRepository walletRepository;

    private final SellerService sellerServiceImpl;


    @GetMapping("/{id}")
    public ResponseEntity<Wallet> viewWallet(@PathVariable("id") Integer uniqueId) throws WalletNotFound {
        Optional<Wallet> wallet = walletRepository.findById(uniqueId);
        if (wallet.isEmpty()){
            throw new WalletNotFound("No wallet with this id" + uniqueId);
        }
        return new ResponseEntity<>(wallet.get(), HttpStatus.OK);
    }

    @PutMapping("/transfer")
    public ResponseEntity<Transaction> WalletTOWalletTransfer(@RequestBody TransactionRequest transactionRequest) throws WalletNotFound {
        Transaction transaction = walletService.fundTransfer(transactionRequest.getSourceMobileNo(), transactionRequest.getTargetMobileNo(), transactionRequest.getAmount(),transactionRequest.getUniqueId());
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping("/addMoney")
    public ResponseEntity<Transaction> addAmountFromBankToWallet(@RequestBody BankToWalletTransferRequest request) throws Exception {
        Transaction transaction = walletService.addMoney(request.getUserId(), request.getAmount());
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

}
