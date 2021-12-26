package com.example.application;

import java.util.ArrayList;

public class Helmet {

    private final ArrayList<Weapon> weapons ;
    private final ArrayList<Weapon> validWeapons ;

    public Helmet() {

        this.weapons = new ArrayList<>();
        this.validWeapons = new ArrayList<>() ;

    }

    public void addvalidWeapons(Weapon weapon){
        validWeapons.add(weapon);
    }

    public int addWeapon(Weapon weapon){

        for(Weapon validweapon : validWeapons){

            if(validweapon.getClass() == weapon.getClass()){
                weapons.add(weapon);
                return 0 ;
            }
        }

        return 1 ;   // Unsuccessful
    }
}
