package com.example.application;

import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 20L;
    private String name;
    private Coins coins;
    private int highScore;
    public User(){
        name = "Player";
        coins = new Coins(0);
        highScore = 0;
    }
    public void collectCoins(Coins c){
        if(c == null) return;
        coins.addCoins(c);
    }
    public int getHighScore(){
        return this.highScore;
    }
    public boolean setHighScore(int current){
        if(highScore < current){
            highScore = current;
            return true;
        }
        return false;
    }
    public int getCoins(){return coins.getValue();}
    public boolean useCoins(int needed){
        return coins.useCoins(needed);
    }
    public String getName(){return name;}
}
