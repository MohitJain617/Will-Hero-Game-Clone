package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

public class CoinChest extends Reward{
    @Serial
    private static final long serialVersionUID = 2L;
    private Coins coinreward;
    private boolean retrieved;
    public CoinChest(double x, double y) {
        super(x, y);
        render();
        retrieved = false;
        coinreward = new Coins(3);
    }

    private void render(){
        Image img = null;
        if(!retrieved) {
            img = new Image("treasure_close.png");
        } else {
            img = new Image("treasure_open.png");
        }
        ImageView iv = new ImageView(img);
        iv.setX(this.getLocation().getX()); iv.setY(this.getLocation().getY());
        iv.setPreserveRatio(true);
        iv.setPickOnBounds(true);
        if(!retrieved) {
            iv.setFitHeight(100);
            iv.setFitWidth(100);
        } else {
            iv.setFitHeight(120);
            iv.setFitWidth(120);
        }
        super.setImageView(iv);
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
        if(retrieved) return;

        hero.collectCoins(this.coinreward);
        retrieved = true;

        ImageView iv = this.getImageView();
        iv.setImage(new Image("treasure_open.png"));
        iv.setFitHeight(120);
        iv.setFitWidth(120);
        Location curr = this.getLocation();
        this.setLocation(curr.getX(),curr.getY()-23);
        this.updateLocation();
    }

}
