package org.example.authentification.payment.Exeptions;

public class TranscationNotFound extends RuntimeException{
    public TranscationNotFound(String message){
        super (message);
    }
    public TranscationNotFound(){}
}
