package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class BossOrc extends GreenOrc{
    ImageView iv;
    Image image;
    double xSpeed; double ySpeed;
    public BossOrc(double xloc, double yloc) {
        super(xloc, yloc, 5, 100);
        image = new Image("orcbossFinal.png");
        iv = new ImageView();
        iv.setImage(image);
        iv.setPreserveRatio(true);
        iv.setX(xloc); iv.setY(yloc);
        iv.setFitHeight(216); iv.setFitWidth(211);
        xSpeed = 0; ySpeed = 0;
    }

    @Override
    public double getXspeed(){return this.xSpeed;}
    @Override
    public double getYspeed(){return this.ySpeed;}
    @Override
    public void setXspeed(double x){this.xSpeed += x;}
    @Override
    public void setYspeed(double y){this.ySpeed += y;}
    @Override
    public void gravityEffect() {
        Location curr = this.getLocation();
        this.setLocation(curr.getX()+xSpeed,curr.getY()+ySpeed);
        iv.setX(curr.getX()+xSpeed);
        iv.setY(curr.getY()+ySpeed);
    }

    @Override
    public void ifHeroCollides(Hero hero) {
        //TODO
    }

    @Override
    public void display(AnchorPane anc) {
        anc.getChildren().add(iv);
    }

    @Override
    public Bounds getBounds() {
        return iv.getBoundsInParent();
    }

    @Override
    public void ifWeaponCollides() {
        //TODO
    }

    @Override
    public void ifObstacleCollides(Obstacle obs) {
        //TODO
    }
}
