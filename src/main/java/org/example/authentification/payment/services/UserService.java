package org.example.authentification.payment.services;

import org.example.authentification.authentification.repositoies.UserRepository;
import org.example.authentification.payment.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll();
        return users;
    }

    public org.example.authentification.authentification.models.User getinfo(Integer id){
        return userRepository.findById(id).orElseThrow();
    }
}
