package com.example.application;

public class InsufficientCoinException extends Exception{
    public InsufficientCoinException(String msg){
        super(msg);
    }
    public InsufficientCoinException(){
        super("Not Enough Coins");
    }
}
