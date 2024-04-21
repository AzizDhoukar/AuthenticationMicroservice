package org.example.authentification.payment.Exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WalletNotFound extends RuntimeException{

    public WalletNotFound() {

    }

    public WalletNotFound(String message) {
        super(message);
    }

}