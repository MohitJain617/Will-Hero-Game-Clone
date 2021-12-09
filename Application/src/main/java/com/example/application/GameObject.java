package com.example.application;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.Serializable;

public abstract class GameObject implements Serializable {
    private Location loc;
    private Boundary bounds;
    public GameObject(double x, double y, double xwidth, double ywidth){
        loc = new Location(x,y);
        bounds = new Boundary(xwidth,ywidth);
    }
    public abstract void gravityEffect();
    public abstract void ifHeroCollides(); //action after hero collides
    public abstract void display(AnchorPane anc);
    public void setLocation(double x, double y){
        loc.setX(x);
        loc.setY(y);
    }
    public Location getLocation(){
        Location l2 = new Location(loc.getX(),loc.getY());
        return l2;
    }
    public boolean checkCollision(GameObject g1){
        return false;
    }
}
