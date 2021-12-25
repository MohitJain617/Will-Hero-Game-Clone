package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public abstract class Weapon extends GameObject {
    private int currentLevel;
    private int xSpeed;
    transient ImageView iv;
    public Weapon(double x, double y) {
        super(x, y);
        iv = null;
        currentLevel = 1;
        xSpeed = 0;
    }
    public void setImageView(ImageView iv){this.iv = iv;}
    protected ImageView getImageView(){return this.iv;}

    public void display(AnchorPane anc) {
        anc.getChildren().add(iv);
    }

    @Override
    public void updateLocation() {
        Location curr = this.getLocation();
        iv.setX(curr.getX());
        iv.setY(curr.getY());
    }
    @Override
    public void gravityEffect(){
        //no effect
    }
    @Override
    public Bounds getBounds() {
        return this.iv.getBoundsInParent();
    }
    @Override
    public void ifHeroCollides(Hero hero){
        //do nothing
    }
}
