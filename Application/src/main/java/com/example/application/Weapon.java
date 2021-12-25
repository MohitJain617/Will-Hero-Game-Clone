package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public abstract class Weapon extends GameObject {

    private int currentLevel;
    private double xSpeed;
    private long lifeTime ;
    transient ImageView iv;

    public Weapon(double x, double y) {
        super(x, y);
        iv = null;
        currentLevel = 1;
        xSpeed = 0;
        lifeTime = 0 ;
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
        Location loc = this.getLocation();
        this.setLocation(loc.getX()+xSpeed,loc.getY());
        updateLocation();
    }
    @Override
    public Bounds getBounds() {
        return this.iv.getBoundsInParent();
    }
    @Override
    public void ifHeroCollides(Hero hero){
        //do nothing
    }

    public Weapon copy(){

        Weapon w ;

        if(this instanceof ThrowingKnife){
             w = new ThrowingKnife(this.getLocation().getX()+60,this.getLocation().getY());
             w.iv.setRotate(90);
        }

        else{  w =  new Sword(this.getLocation().getX() , this.getLocation().getY()); }

        w.lifeTime = System.nanoTime() + (long)4e8 ;
        w.currentLevel = this.currentLevel ;
        w.xSpeed = 40 ;

        return w ;
    }

    public boolean use(){
        return this.lifeTime >= System.nanoTime() ;
    }

    public void undisplay(){
        iv.setOpacity(0);
    }
    public void display(){
        iv.setOpacity(1);
    }

    public void setLifeTime(long lifeTime){


    }
}
