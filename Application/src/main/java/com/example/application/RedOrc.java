package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class RedOrc extends Orcs{
    double xSpeed; double ySpeed;
    public RedOrc(double xloc, double yloc) {

        super(xloc, yloc, 1, 20);
        Image image = new Image("orcredFinal.png");
        ImageView iv = new ImageView(image);
        iv.setPreserveRatio(true);
        iv.setX(xloc); iv.setY(yloc);
        iv.setFitHeight(57); iv.setFitWidth(67.5);
        xSpeed = 0; ySpeed = 0;
        this.setImageView(iv);
    }

    @Override
    public void ifHeroCollides(Hero hero) {
        //TODO
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
