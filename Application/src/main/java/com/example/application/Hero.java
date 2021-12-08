package com.example.application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class Hero extends GameObject{
    Image img;
    ImageView iv;
    public Hero(double x, double y){
        super(x,y);
        iv = new ImageView();
        img = new Image("hero.png");
        iv.setImage(img);
        iv.setFitHeight(118.5);
        iv.setFitWidth(144.0);
        iv.setX(x); iv.setY(y);
        iv.setPreserveRatio(true);
    }
    @Override
    public void gravityEffect() {
        //keyframe pls
    }

    @Override
    public void display(AnchorPane anc) {
        //add iv to the AnchorPane
        anc.getChildren().add(iv);
    }

}
