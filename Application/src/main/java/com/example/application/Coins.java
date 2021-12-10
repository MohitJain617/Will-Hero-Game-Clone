package com.example.application;

public class Coins {
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
