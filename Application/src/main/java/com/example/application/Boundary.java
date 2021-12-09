package com.example.application;

public class Boundary {
    private double xWidth;
    private double yWidth;
    public Boundary(double x, double y){
        this.xWidth = x;
        this.yWidth = y;
    }
    public double getX(){
        return xWidth;
    }
    public double getY(){
        return yWidth;
    }
    public void setX(double x){
        this.xWidth = x;
    }
    public void setY(double y){
        this.yWidth = y;
    }
}
