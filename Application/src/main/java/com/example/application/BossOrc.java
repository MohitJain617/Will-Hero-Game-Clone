package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class BossOrc extends GreenOrc{
    Image image;
    double xSpeed; double ySpeed;
    public BossOrc(double xloc, double yloc) {
        super(xloc, yloc, 5, 100);
        image = new Image("orcbossFinal.png");
        ImageView iv = new ImageView();
        iv.setImage(image);
        iv.setPreserveRatio(true);
        iv.setX(xloc); iv.setY(yloc);
        iv.setFitHeight(172.8); iv.setFitWidth(168.8);
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
