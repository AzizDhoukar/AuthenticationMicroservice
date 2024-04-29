package org.example.authentification.payment.services;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.authentification.payment.Exeptions.UserNotFound;
import org.example.authentification.payment.models.Transaction;
import org.example.authentification.payment.models.User;
import org.example.authentification.payment.models.Wallet;
import org.example.authentification.payment.repositories.TransactionRepository;
import org.example.authentification.payment.repositories.UserRepository;
import org.example.authentification.payment.repositories.WalletRepository;
import org.example.authentification.payment.requests.PaymentRequest;
import org.example.authentification.payment.requests.SplitPaymentRequest;
import org.springframework.stereotype.Service;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import static org.example.authentification.payment.models.Transaction.TransactionStatus.SUCCEED;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    public Transaction createPayment (PaymentRequest request){
        return transferMoney(request.getSellerId(), request.getUserId(), request.getAmount());
    }

    public List<Integer> createSplitPayment(SplitPaymentRequest request) {
        List<Integer> list;
        for (SplitPaymentRequest.SplitDetail split : request.getSplitDetails()){
            Transaction t = transferMoney(request.getSellerId(), split.getUserId(), split.getAmount());
            list.add(t.getTransactionId());
        }

    }


    private Transaction transferMoney(Integer sellerId, Integer userId, double amount){
        User seller = userRepository.findById(sellerId).orElseThrow(UserNotFound::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);

        Wallet sellerWallet = seller.getWallet();
        Wallet userWallet = user.getWallet();

        sellerWallet.setBalance(sellerWallet.getBalance() + amount);
        userWallet.setBalance(userWallet.getBalance() - amount);

        walletRepository.save(sellerWallet);
        walletRepository.save(userWallet);

        Transaction transactionUser = new Transaction(userWallet, -amount,  "you sent money to"+seller.getUserId(), SUCCEED);
        Transaction transaction = new Transaction(sellerWallet, amount,  user.getUserId() + "sent you money", SUCCEED);
        transactionRepository.save(transactionUser);
        return transactionRepository.save(transaction);
    }
}
