package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public abstract class Obstacle extends GameObject{
    public Obstacle(double x, double y) {
        super(x, y);
    }
    public abstract void ifWeaponCollides();
    public abstract boolean isAlive();
    public abstract Coins deathReward();
}
