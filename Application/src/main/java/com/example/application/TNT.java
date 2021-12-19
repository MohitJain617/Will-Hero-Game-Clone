package com.example.application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TNT extends Obstacle{
    private boolean fuse;
    private long timer;
    public TNT(double xloc, double yloc) {
        super(xloc, yloc);
        Image image = new Image("TNT.png");
        ImageView iv = new ImageView();
        iv.setImage(image);
        iv.setPreserveRatio(true);
        iv.setPickOnBounds(true);
        iv.setX(xloc); iv.setY(yloc);
        iv.setFitHeight(70); iv.setFitWidth(70);
        this.setImageView(iv);
        fuse = true;
        timer = 1000000000;
    }
    @Override
    public void ifHeroCollides(Hero hero) {
        //kill hero
        if(fuse){
//            hero.damage();
            blast();
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
