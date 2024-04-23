package org.example.authentification.payment.services;

import lombok.RequiredArgsConstructor;
import org.example.authentification.authentification.models.User;
import org.example.authentification.authentification.repositoies.UserRepository;
import org.example.authentification.payment.Exeptions.BankAccountNotFound;
import org.example.authentification.payment.Exeptions.InsufficientBalanceException;
import org.example.authentification.payment.Exeptions.SellerNotFound;
import org.example.authentification.payment.models.BankAccount;
import org.example.authentification.payment.models.Seller;
import org.example.authentification.payment.models.Transaction;
import org.example.authentification.payment.models.Wallet;
import org.example.authentification.payment.repositories.BankAccountRepository;
import org.example.authentification.payment.repositories.TransactionRepository;
import org.example.authentification.payment.repositories.SellerRepositoryNew;
import org.example.authentification.payment.repositories.WalletRepository;
import org.example.authentification.payment.requests.AddWalletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final SellerService sellerService;
    private final SellerRepositoryNew sellerRepositoryNew;
    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public Transaction fundTransfer(String sourceMobileNo, String targetMobileNo, Double amount, String uniqueId) throws SellerNotFound {
        Optional<Seller> user = sellerRepositoryNew.findByMobileNo(sourceMobileNo);
        if (user.isEmpty()){ throw new SellerNotFound("No user with the number:" + sourceMobileNo); }
        Seller customer = user.get();
        Wallet wallet = customer.getWallet();

        Optional<Seller> targetUser = sellerRepositoryNew.findByMobileNo(sourceMobileNo);
        if (targetUser.isEmpty()){ throw new SellerNotFound("No user with the number:" + sourceMobileNo); }
        Seller targetCustomer = targetUser.get();


        Transaction transaction = new Transaction(wallet, amount, LocalDateTime.now(), "Fund Transfer from Wallet to Wallet Successfully !");
        transactionRepository.save(transaction);
        return transaction;
    }

    public Double showBalance(String uniqueId) {
        //TODO
        return 0.0;
    }

    public Transaction addMoney(Integer userId, Double amount) throws SellerNotFound, InsufficientBalanceException, BankAccountNotFound {
        Optional<Seller> customer = sellerRepositoryNew.findById(userId);
        if (customer.isEmpty()){ throw new SellerNotFound("No user with the id:" + userId); }

        Seller seller = customer.get();
        Wallet wallet = seller.getWallet();

        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(wallet.getWalletId());
        if (optionalBankAccount.isEmpty()) {throw new BankAccountNotFound("User dose not have a linked bank account");}
        BankAccount bankAccount = optionalBankAccount.get();

        if(bankAccount.getBankBalance()==0 || bankAccount.getBankBalance()<amount) {throw new InsufficientBalanceException("Insufficient balance in bank");}

        bankAccount.setBankBalance(bankAccount.getBankBalance() - amount);
        wallet.setBalance(wallet.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
        walletRepository.save(wallet);

        Transaction transaction = new Transaction(wallet, amount, LocalDateTime.now(), "Fund Transfer from bank to Wallet Successfully !");
        transactionRepository.save(transaction);
        return transaction;



    }

    public Wallet addWallet(AddWalletRequest request) throws SellerNotFound{
        Wallet wallet = new Wallet();
        Optional<User> user = userRepository.findById(request.getSellerId());
        if (user.isEmpty()){throw new SellerNotFound("no seller with id:"+request.getSellerId());}
        wallet.setUser(user.get());
        walletRepository.save(wallet);
        return wallet;
    }
}
