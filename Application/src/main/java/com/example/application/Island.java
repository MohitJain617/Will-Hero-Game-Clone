package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Island extends GameObject implements ObstacleCollision{
    ImageView iv;
    public Island(double x, double y, double xwidth, double ywidth, String island) {
        super(x, y);
        iv = new ImageView();
        Image img = new Image(island);
        iv.setImage(img);
        iv.setFitHeight(ywidth);
        iv.setFitWidth(xwidth);
        iv.setLayoutX(x); iv.setLayoutY(y);
        iv.setPickOnBounds(true);
        iv.setPreserveRatio(true);
    }

    @Override
    public void gravityEffect() {
        //nothing boi
    }

    @Override
    public void ifHeroCollides(Hero hero) {
        if(hero.getLocation().getY() < this.getLocation().getY()){
            hero.setYspeed(-9);
        }
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
    public void ifObstacleCollides(Obstacle obs) {
        if(obs instanceof Orcs){
            ((Orcs) obs).setySpeed(-10);
        }
    }
}
