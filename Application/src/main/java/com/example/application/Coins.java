package com.example.application;

import java.io.Serial;
import java.io.Serializable;

public class Coins implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;
    private int value;
    public Coins(int val){
        this.value = val;
    }
    public int getValue(){
        return this.value;
    }
    public void addCoins(Coins c){
        this.value += c.getValue();
    }
    public boolean useCoins(int x){
        if(this.value < x) return false;
        this.value -= x;
        return true;
    }
}
