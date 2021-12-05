package com.example.application;

import javafx.scene.layout.AnchorPane;

import java.io.Serializable;

public abstract class GameObject implements Serializable {
    private double x;
    private double y;
    public GameObject(double x, double y){
        this.x = x;
        this.y = y;
    }
    public abstract void gravityEffect();
    public abstract void display(AnchorPane anc);
    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }

}
