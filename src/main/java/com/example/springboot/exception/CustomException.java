package com.example.springboot.exception;

public class CustomException extends Exception{
    public CustomException(String message){
        super(message);
    }
}
