package com.example.kolesnikov_advancedServer.exeption;

public class UserNotFoundExeption extends Exception{
    public UserNotFoundExeption(String message) {
        super(message);
    }
}
