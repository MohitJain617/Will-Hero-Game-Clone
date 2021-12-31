package com.example.application;

import java.io.Serial;

public abstract class GreenOrc extends Orcs{
    @Serial
    private static final long serialVersionUID = 7L;
    public GreenOrc(double x, double y, int points, int health) {
        super(x, y, points, health);
    }
}
