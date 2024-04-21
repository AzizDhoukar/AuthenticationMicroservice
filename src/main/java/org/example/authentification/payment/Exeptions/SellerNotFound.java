package org.example.authentification.payment.Exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SellerNotFound extends RuntimeException{
    public SellerNotFound(String message){
        super (message);
    }
    public SellerNotFound(){

    }
}
