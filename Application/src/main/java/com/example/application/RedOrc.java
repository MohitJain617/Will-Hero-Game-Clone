package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class RedOrc extends Orcs{
    ImageView iv;
    double xSpeed; double ySpeed;
    public RedOrc(double xloc, double yloc) {
        super(xloc, yloc, 1, 20);
        Image image = new Image("orcredFinal.png");
        iv = new ImageView();
        iv.setImage(image);
        iv.setPreserveRatio(true);
        iv.setLayoutX(xloc); iv.setLayoutY(yloc);
        iv.setFitHeight(71); iv.setFitWidth(84);
        xSpeed = 0; ySpeed = 0;
    }

    @Override
    public double getxSpeed(){return this.xSpeed;}
    @Override
    public double getySpeed(){return this.ySpeed;}
    @Override
    public void setxSpeed(double x){this.xSpeed += x;}
    @Override
    public void setySpeed(double y){this.ySpeed += y;}

    @Override
    public void gravityEffect() {
        Location curr = this.getLocation();
        this.setLocation(curr.getX()+xSpeed,curr.getY()+ySpeed);
        iv.setX(curr.getX()+xSpeed);
        iv.setY(curr.getY()+ySpeed);
        System.out.println(iv.getY());
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
