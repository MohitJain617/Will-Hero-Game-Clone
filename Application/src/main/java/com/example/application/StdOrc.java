package com.example.application;

        import javafx.geometry.Bounds;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.AnchorPane;

public class StdOrc extends GreenOrc{
    double xSpeed; double ySpeed;
    public StdOrc(double xloc, double yloc) {

        super(xloc, yloc, 1, 20);
        Image image = new Image("orcfinal.png");
        ImageView iv = new ImageView();
        iv.setImage(image);
        iv.setPreserveRatio(true);
        iv.setX(xloc); iv.setY(yloc);
        iv.setFitHeight(59); iv.setFitWidth(69);
        xSpeed = 0; ySpeed = 0;
        this.setImageView(iv);
    }

    @Override
    public void ifHeroCollides(Hero hero) {
        //TODO
    }

    @Override
    public void ifWeaponCollides() {
        //TODO
    }

    @Override
    public void ifObstacleCollides(Obstacle obs) {
        //TODO
    }
}
