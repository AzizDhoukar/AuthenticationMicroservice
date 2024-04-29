package org.example.authentification.authentification.exeptions;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String message){
        super (message);
    }
    public UserNotFound(){}
}
