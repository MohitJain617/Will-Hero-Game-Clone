package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.Serial;
import java.io.Serializable;

public abstract class GameObject implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    private final Location loc;
    public GameObject(double x, double y){
        loc = new Location(x,y);
    }
    public abstract void gravityEffect();
    public abstract void updateLocation();
    public abstract void ifHeroCollides(Hero hero); //action after hero collides
    public abstract void display(AnchorPane anc);
    public void setLocation(double x, double y){
        loc.setX(x);
        loc.setY(y);
    }
    public Location getLocation(){
        return new Location(loc.getX(),loc.getY());
    }
    public abstract Bounds getBounds();
    public boolean checkCollision(GameObject gob){
        return gob.getBounds().intersects(this.getBounds());
    }
}
