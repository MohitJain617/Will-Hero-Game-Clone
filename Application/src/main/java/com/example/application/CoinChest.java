package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

public class CoinChest extends Reward{

    boolean retrieved;
    public CoinChest(double x, double y) {
        super(x, y);
        render();
        retrieved = false;
    }
    private void render(){
        Image img = new Image("treasure_close.png");
        ImageView iv = new ImageView(img);
        iv.setX(this.getLocation().getX()); iv.setY(this.getLocation().getY());
        iv.setPreserveRatio(true);
        iv.setPickOnBounds(true);
        iv.setFitHeight(100);
        iv.setFitWidth(100);
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
