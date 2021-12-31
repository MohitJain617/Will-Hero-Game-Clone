package com.example.application;

import java.io.Serializable;
import java.util.ArrayList;

public class Helmet implements Serializable {
    private Weapon S;
    private Weapon TK;
    public Helmet() {
        Weapon S = null; //Sword
        Weapon TK = null; //Throwing Knife
    }

    public int addWeapon(Weapon weapon){
        if(weapon instanceof Sword){
            if(S == null) S = weapon;
            else S.levelUp();
        } else if(weapon instanceof ThrowingKnife){
            if(TK == null) TK = weapon;
            else TK.levelUp();
        } else {
            return 1; //unsuccessful
        }
        return 0; //successful
    }
    public Weapon getWeapon(String name){
        if(name.equals("Sword")){
            return this.S;
        } else if(name.equals("ThrowingKnife")) {
            return this.TK;
        }
        return null;
    }
}
