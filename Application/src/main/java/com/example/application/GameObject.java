package com.example.application;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.Serializable;

public abstract class GameObject implements Serializable {
    private final Location loc;
    private final Boundary bounds;
    public GameObject(double x, double y, double xwidth, double ywidth){
        loc = new Location(x,y);
        bounds = new Boundary(xwidth,ywidth);
    }
    public abstract void gravityEffect();
    public abstract void ifHeroCollides(Hero hero); //action after hero collides
    public abstract void display(AnchorPane anc);
    public void setLocation(double x, double y){
        loc.setX(x);
        loc.setY(y);
    }
    public Location getLocation(){
        return new Location(loc.getX(),loc.getY());
    }
    public Boundary getBounds(){
        return new Boundary(bounds.getX(),bounds.getY());
    }
    public boolean checkCollision(GameObject gob){
        Location l2 = gob.getLocation();
        Boundary b2 = gob.getBounds();
        //gobs bounds
        double yb1,yb2,xb1,xb2;
        yb1 = l2.getY()-b2.getY()/2;
        yb2 = l2.getY()+b2.getY()/2;
        xb1 = l2.getX()-b2.getX()/2;
        xb2 = l2.getX()+b2.getX()/2;
        //current objects bounds
        double cyb1,cyb2,cxb1,cxb2;
        cyb1 = loc.getY()-bounds.getY()/2;
        cyb2 = loc.getY()+bounds.getY()/2;
        cxb1 = loc.getX()-bounds.getX()/2;
        cxb2 = loc.getX()+bounds.getX()/2;
        //check collision
        boolean ymatch = false;
        boolean xmatch = false;
        if((yb1 > cyb1) && (yb1 < cyb2)) ymatch = true;
        if((yb2 > cyb1) && (yb2 < cyb2)) ymatch = true;
        if((xb1 > cxb1) && (xb1 < cxb2)) xmatch = true;
        if((xb2 > cxb1) && (xb2 < cxb2)) xmatch = true;
        return (ymatch && xmatch);
    }
}
