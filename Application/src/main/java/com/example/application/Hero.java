package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class Hero extends GameObject{
    Image img;
    ImageView iv;
    private double ySpeed;
    private double xSpeed;
    public Hero(double x, double y){
        super(x,y);
        ySpeed = 0;
        xSpeed = 0;
        iv = new ImageView();
        img = new Image("hero.png");
        iv.setImage(img);
        iv.setFitHeight(106.65);
        iv.setFitWidth(129.6);
        iv.setX(x); iv.setY(y);
        iv.setPreserveRatio(true);
    }
    @Override
    public Bounds getBounds(){
        return this.iv.getBoundsInParent();
    }
    @Override
    public void gravityEffect() {
        Location curr = this.getLocation();
        this.setLocation(curr.getX()+xSpeed,curr.getY()+ySpeed);
        iv.setX(curr.getX()+xSpeed);
        iv.setY(curr.getY()+ySpeed);
    }

    @Override
    public void ifHeroCollides(Hero hero) {
        //do nothing since hero can't collide with itself
    }

    public void setYspeed(double speed){
        ySpeed = speed;
    }
    public double getYspeed(){
        return ySpeed;
    }
    @Override
    public void display(AnchorPane anc) {
        //add iv to the AnchorPane
        anc.getChildren().add(iv);
    }

}
