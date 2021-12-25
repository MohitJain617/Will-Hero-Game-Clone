package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public abstract class Obstacle extends GameObject implements ObstacleCollision{
    transient ImageView iv;
    private double xSpeed;
    private double ySpeed;

    public Obstacle(double x, double y) {
        super(x, y);
        iv = null;
    }
    public void setImageView(ImageView iv){this.iv = iv;}
    protected ImageView getImageView(){return this.iv;}
    @Override
    public void gravityEffect(){
        Location curr = this.getLocation();
        this.setLocation(curr.getX()+xSpeed,curr.getY()+ySpeed);
        this.updateLocation();
    }
    @Override
    public void updateLocation() {
        Location curr = this.getLocation();
        iv.setX(curr.getX());
        iv.setY(curr.getY());
    }
    @Override
    public void display(AnchorPane anc) {
        anc.getChildren().add(iv);
    }

    @Override
    public Bounds getBounds() {
        return iv.getBoundsInParent();
    }

    public double getXspeed(){return this.xSpeed;}
    public double getYspeed(){return this.ySpeed;}
    public void setXspeed(double x){this.xSpeed = x;}
    public void setYspeed(double y){this.ySpeed = y;}
    public abstract void ifWeaponCollides(Weapon w);
    public abstract boolean isAlive();
    public abstract Coins deathReward();
}
