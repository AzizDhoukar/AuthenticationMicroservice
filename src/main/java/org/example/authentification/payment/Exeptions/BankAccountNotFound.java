package org.example.authentification.payment.Exeptions;

public class BankAccountNotFound extends Exception{
    public BankAccountNotFound (String message){
        super (message);
    }
    public BankAccountNotFound (){}
}
