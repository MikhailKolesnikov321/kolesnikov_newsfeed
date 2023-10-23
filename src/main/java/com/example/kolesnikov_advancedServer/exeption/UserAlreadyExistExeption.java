package com.example.kolesnikov_advancedServer.exeption;

public class UserAlreadyExistExeption extends Exception{
    public UserAlreadyExistExeption(String message){
        super(message );
    }
}
