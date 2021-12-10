package com.example.application;

public abstract class Orcs extends Obstacle{
    private int health;
    private Coins coins;
    public Orcs(double x, double y, int points, int health) {
        super(x, y);
        coins = new Coins(points);
        this.health = health;
    }
    public int getHealth(){
        return this.health;
    }

    @Override
    public Coins deathReward(){
        return this.coins;
    }

    //TODO: implement ifWeaponCollides(w: Weapon) here later
    protected void damage(int val){
        this.health -= val;
    }

    @Override
    public boolean isAlive(){
        return (this.health > 0);
    }
}
