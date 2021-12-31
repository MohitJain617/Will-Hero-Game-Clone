package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.util.Objects;

public class Hero extends GameObject{
    transient ImageView iv;
    private double ySpeed;
    private double xSpeed;
    private boolean alive;
    private Weapon currentWeapon;
    private int collectedCoins;
    private Helmet helmet ;

    public Hero(double x, double y){
        super(x,y);
        ySpeed = 0;
        xSpeed = 0;
        render();
        alive = true;
        currentWeapon =  null ;
        collectedCoins = 0 ;
        helmet = new Helmet() ;

        helmet.addvalidWeapons(new ThrowingKnife(0,0));  // TODO Needs to add according to user
        helmet.addvalidWeapons(new Sword(0,0));
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
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
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
        if(currentWeapon != null){
            if(currentWeapon instanceof Sword) currentWeapon.setLocation(curr.getX()-40,curr.getY()+30);
            else currentWeapon.setLocation(curr.getX(),curr.getY()+50);
            currentWeapon.updateLocation();
        }
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
    public void displayWeapon(AnchorPane anc){
        if(currentWeapon != null && !anc.getChildren().contains(currentWeapon.getImageView())){
            anc.getChildren().add(currentWeapon.getImageView());
        }
    }

    public int getCollectedCoins(){ return this.collectedCoins ; }

    public void setCollectedCoins(int coins){ this.collectedCoins = coins ; }

    public Weapon getCurrentWeapon(){

        if(this.currentWeapon == null ){ return null ; }

        return this.currentWeapon.copy() ;      // TODO copy
    }

    public void addWeapon(Weapon weapon){

        int verdict = this.helmet.addWeapon(weapon);

        if(verdict == 0){
            this.currentWeapon = weapon ;
            System.out.println("Valid weapon given to hero");
            return ;
        }

        System.out.println("Invalid Weapon");
    }

}
