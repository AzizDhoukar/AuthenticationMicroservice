package org.example.authentification.payment.repositories;

import org.example.authentification.payment.models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepositoryNew extends JpaRepository<Seller, Integer> {

    public Optional<Seller> findByMobileNo (String mobileNo);
}
