package org.example.authentification.payment.services;

import org.example.authentification.authentification.repositoies.UserRepository;
import org.example.authentification.payment.models.Seller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerService {
    private final UserRepository userRepository;

    public SellerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Seller> allUsers() {
        List<Seller> sellers = new ArrayList<>();

        userRepository.findAll();

        return sellers;
    }

}
