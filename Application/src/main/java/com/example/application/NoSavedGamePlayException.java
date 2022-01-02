package com.example.application;

public class NoSavedGamePlayException extends Exception{
    public NoSavedGamePlayException(){
        super("No saved GamePlay");
    }
    public NoSavedGamePlayException(String msg){
        super(msg);
    }
}
