package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public abstract class Reward extends GameObject {
    ImageView iv;
    private double xSpeed;
    private double ySpeed;
    public Reward(double x, double y) {
        super(x, y);
        xSpeed = 0; ySpeed = 0;
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
    public void setSpeed(double x, double y){
        xSpeed = x; ySpeed = y;
    }
    public double getYSpeed(){return ySpeed;}
    public double getXSpeed(){return xSpeed;}
    public void display(AnchorPane ap){
        ap.getChildren().add(iv);
    }
    @Override
    public void updateLocation() {
        Location curr = this.getLocation();
        iv.setX(curr.getX());
        iv.setY(curr.getY());
    }
    @Override
    public Bounds getBounds() {
        return iv.getBoundsInParent();
    }
}
