package org.example.authentification.payment.services;

import lombok.RequiredArgsConstructor;
import org.example.authentification.payment.Exeptions.BankAccountNotFound;
import org.example.authentification.payment.Exeptions.InsufficientBalanceException;
import org.example.authentification.payment.Exeptions.UserNotFound;
import org.example.authentification.payment.models.BankAccount;
import org.example.authentification.payment.models.User;
import org.example.authentification.payment.models.Transaction;
import org.example.authentification.payment.models.Wallet;
import org.example.authentification.payment.repositories.BankAccountRepository;
import org.example.authentification.payment.repositories.TransactionRepository;
import org.example.authentification.payment.repositories.UserRepository;
import org.example.authentification.payment.repositories.WalletRepository;
import org.example.authentification.payment.requests.AddWalletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.example.authentification.payment.models.Transaction.TransactionStatus.SUCCEED;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final WalletRepository walletRepository;

    public Transaction fundTransfer(String sourceMobileNo, String targetMobileNo, Double amount, String uniqueId) throws UserNotFound {
        Optional<User> user = userRepository.findByMobileNo(sourceMobileNo);
        if (user.isEmpty()){ throw new UserNotFound("No user with the number:" + sourceMobileNo); }
        User customer = user.get();
        Wallet wallet = customer.getWallet();

        Optional<User> targetUser = userRepository.findByMobileNo(sourceMobileNo);
        if (targetUser.isEmpty()){ throw new UserNotFound("No user with the number:" + sourceMobileNo); }
        User targetCustomer = targetUser.get();


        Transaction transaction = new Transaction(wallet, amount, "Fund Transfer from Wallet to Wallet Successfully !", SUCCEED);
        transactionRepository.save(transaction);
        return transaction;
    }

    public Double showBalance(String uniqueId) {
        //TODO
        return 0.0;
    }

    public Transaction addMoney(Integer userId, Double amount) throws UserNotFound, InsufficientBalanceException, BankAccountNotFound {
        Optional<User> customer = userRepository.findById(userId);
        if (customer.isEmpty()){ throw new UserNotFound("No user with the id:" + userId); }

        User user = customer.get();
        Wallet wallet = user.getWallet();

        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(wallet.getWalletId());
        if (optionalBankAccount.isEmpty()) {throw new BankAccountNotFound("User dose not have a linked bank account");}
        BankAccount bankAccount = optionalBankAccount.get();

        if(bankAccount.getBankBalance()==0 || bankAccount.getBankBalance()<amount) {throw new InsufficientBalanceException("Insufficient balance in bank");}

        bankAccount.setBankBalance(bankAccount.getBankBalance() - amount);
        wallet.setBalance(wallet.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
        walletRepository.save(wallet);

        Transaction transaction = new Transaction(wallet, amount,  "Fund Transfer from bank to Wallet Successfully !", SUCCEED);
        transactionRepository.save(transaction);
        return transaction;



    }

    public Wallet addWallet(AddWalletRequest request) throws UserNotFound {
        Wallet wallet = new Wallet();
        Optional<User> user = userRepository.findById(request.getUserId());
        if(user.isEmpty())throw new UserNotFound("no user by that id");
        wallet.setUser(user.get());
        walletRepository.save(wallet);
        return wallet;
    }
}
