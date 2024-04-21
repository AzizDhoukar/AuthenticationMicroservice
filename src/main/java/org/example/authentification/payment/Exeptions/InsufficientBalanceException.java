package org.example.authentification.payment.Exeptions;

public class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException (String message){
        super (message);
    }
    public InsufficientBalanceException (){}

}
