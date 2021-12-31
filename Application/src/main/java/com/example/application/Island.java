package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

public class Island extends GameObject implements ObstacleCollision{

    transient ImageView iv;
    private double xWidth;
    private double yWidth;
    private String islandName;

    public Island(double x, double y, double xwidth, double ywidth, String island) {
        super(x, y);
        this.xWidth = xwidth;
        this.yWidth = ywidth;
        this.islandName = island;
        render();
    }

    private void render(){
        iv = new ImageView();
        Image img = new Image(islandName);
        iv = new ImageView(img);
        iv.setFitHeight(yWidth);
        iv.setFitWidth(xWidth);
        iv.setX(this.getLocation().getX()); iv.setY(this.getLocation().getY());
        iv.setPickOnBounds(true);
        iv.setPreserveRatio(true);
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
    public void gravityEffect() {
        //nothing boi
    }
    @Override
    public void updateLocation() {
        Location curr = this.getLocation();
        iv.setX(curr.getX());
        iv.setY(curr.getY());
    }


    @Override
    public void ifHeroCollides(Hero hero) {
        if(hero.getLocation().getY() < this.getLocation().getY()){
            hero.setYspeed(-10);
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

            if(obs instanceof BossOrc && obs.getLocation().getY() < this.getLocation().getY() ){
                (obs).setYspeed(-10);
                (obs).setXspeed(0);
                return ;
            }

            if(obs.getLocation().getY() < this.getLocation().getY()){
                (obs).setYspeed(-12);
                (obs).setXspeed(0);
            }
        }
    }
}
