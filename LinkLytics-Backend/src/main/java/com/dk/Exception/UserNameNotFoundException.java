package com.dk.Exception;

public class UserNameNotFoundException extends Exception{


    public UserNameNotFoundException(String s) {
        System.out.println(s);
    }

    public String getMessage(String message) {
        return message;
    }
}
