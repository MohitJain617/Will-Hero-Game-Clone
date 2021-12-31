package com.example.application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

public class Sword extends Weapon {

    @Serial
    private static final long serialVersionUID = 17L;
    public Sword(double x, double y) {
        super(x, y);
        render();
    }
    private void render(){
        ImageView iv = new ImageView();
        Image img = new Image("sword.png");
        iv.setImage(img);
        iv.setPreserveRatio(true);
        iv.setFitHeight(90);
        iv.setFitWidth(90);
        iv.setRotate(-90);
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
    public void setLocation(double x, double y){
        super.setLocation(x,y);
    }
}
