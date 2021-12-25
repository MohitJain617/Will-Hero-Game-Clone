package com.example.application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

public class TNT extends Obstacle{
    private boolean fuse;
    private long timer;
    public TNT(double xloc, double yloc) {
        super(xloc, yloc);
        render();
        fuse = true;
        timer = 1200000000;
    }

    private void render(){
        Image image = new Image("TNT.png");
        ImageView iv = new ImageView(image);
        iv.setPickOnBounds(true);
        iv.setPreserveRatio(true);
        iv.setX(this.getLocation().getX()); iv.setY(this.getLocation().getY());
        iv.setFitHeight(70); iv.setFitWidth(70);
        this.setImageView(iv);
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

    @Override
    public void ifHeroCollides(Hero hero) {
        //kill hero
        if(fuse){
            blast();
        } else if(System.nanoTime() < timer){
            hero.damage();
        }
    }
    private void blast(){
        fuse = false;
        ImageView iv = this.getImageView();
        Image img = new Image("blast-unscreen.gif");
        iv.setImage(img);
        iv.setFitHeight(200); iv.setFitWidth(200);
        Location loc = this.getLocation();
        this.setLocation(loc.getX()-60,loc.getY()-60);
        this.updateLocation();
        timer = timer + System.nanoTime();
    }
    @Override
    public void ifWeaponCollides() {
        //set the timer off
    }

    @Override
    public boolean isAlive() {
        if((!fuse) && (System.nanoTime() - timer > 0)) {
            System.out.println("REMOVE TNT");
            ImageView iv = this.getImageView();
            iv.setOpacity(0);
            return false;
        }
        return true;
    }

    @Override
    public Coins deathReward() {
        return new Coins(0);
    }

    @Override
    public void ifObstacleCollides(Obstacle obs) {

    }
}
