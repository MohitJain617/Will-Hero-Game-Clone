package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.util.Objects;

public class Hero extends GameObject{
    transient ImageView iv;
    private double ySpeed;
    private double xSpeed;
    private boolean alive;
    private int collectedCoins;

    public Hero(double x, double y){
        super(x,y);
        ySpeed = 0;
        xSpeed = 0;
        render();
        alive = true;
        collectedCoins = 0 ;
    }
    private void render(){
        iv = new ImageView();
        Image img = new Image("hero.png");
        iv.setImage(img);
        iv.setPreserveRatio(true);
        iv.setFitHeight(90);
        iv.setFitWidth(90);
        iv.setX(this.getLocation().getX()); iv.setY(this.getLocation().getY());
    }
    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        render();
    }

    public void damage(){
        alive = false;
    }
    public boolean isAlive(){
        if(this.getLocation().getY() > 480) alive = false;
        return alive;
    }

    @Override
    public Bounds getBounds(){
        return this.iv.getBoundsInParent();
    }

    @Override
    public void gravityEffect() {
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
    public void ifHeroCollides(Hero hero) {
        //do nothing since hero can't collide with itself
    }

    public void setYspeed(double speed){
        ySpeed = speed;
    }
    public double getYspeed(){
        return ySpeed;
    }
    public void setXspeed(double speed){
        xSpeed = speed;
    }
    public double getXspeed(){
        return xSpeed;
    }

    @Override
    public void display(AnchorPane anc) {
        //add iv to the AnchorPane
        anc.getChildren().add(iv);
    }

    public int getCollectedCoins(){ return this.collectedCoins ; }

    public void setCollectedCoins(int coins){ this.collectedCoins = coins ; }

}
