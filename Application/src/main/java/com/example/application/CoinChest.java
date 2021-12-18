package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CoinChest extends Reward{
    boolean retrieved;
    public CoinChest(double x, double y) {
        super(x, y);
        Image img = new Image("treasure_close.png");
        ImageView iv = new ImageView(img);
        iv.setX(x); iv.setY(y);
        iv.setPreserveRatio(true);
        iv.setPickOnBounds(true);
        iv.setFitHeight(100);
        iv.setFitWidth(100);
        super.setImageView(iv);
        retrieved = false;
    }

    @Override
    public void ifHeroCollides(Hero hero) {
        if(retrieved) return;
        ImageView iv = this.getImageView();
        iv.setImage(new Image("treasure_open.png"));
        iv.setFitHeight(120);
        iv.setFitWidth(120);
        Location curr = this.getLocation();
        this.setLocation(curr.getX(),curr.getY()-23);
        this.updateLocation();
        retrieved = true;
    }

}
