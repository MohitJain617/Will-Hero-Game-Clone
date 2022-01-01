package com.example.application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

public class ThrowingKnife extends Weapon{
    @Serial
    private static final long serialVersionUID = 18L;
    public ThrowingKnife(double x, double y) {
        super(x, y);
        render();
    }
    private void render(){
        ImageView iv = new ImageView();
        Image img = new Image("WeaponKnife.png");
        iv.setImage(img);
        iv.setPreserveRatio(true);
        iv.setFitHeight(45);
        iv.setFitWidth(45);
        iv.setRotate(-45); //rotate by 90 degrees
        iv.setX(this.getLocation().getX()); iv.setY(this.getLocation().getY());
        this.setImageView(iv);
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
    public int damagePower() {
        return 20;
        //no matter the level;
    }
}
