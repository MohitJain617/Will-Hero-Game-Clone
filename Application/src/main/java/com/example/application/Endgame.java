package com.example.application;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static javafx.animation.Animation.INDEFINITE;

public class Endgame {

    @FXML
    ImageView orc_end ;

    public void bounce(){

        Timeline bouncer = new Timeline();

        bouncer.getKeyFrames().addAll(
                makeKeyFrame(0, 0.0, 1.0, 1.0),
                makeKeyFrame(100, 15.0, 0.95, 1.05),
                makeKeyFrame(200, 35.0, 0.9, 1.1),
                makeKeyFrame(300, 65.0, 0.85, 1.15),
                makeKeyFrame(400,100.0,0.8,1.2),
                makeKeyFrame(500, 140.0, 0.9, 1.1),
                makeKeyFrame(600, 140.0, 1, 1.0),
                makeKeyFrame(700, 140.0, 1.1, 0.9),
                makeKeyFrame(800, 140.0, 1.2, 0.8),
                makeKeyFrame(900, 140.0, 1.1, 0.9),
                makeKeyFrame(1000, 140.0, 1, 1.0),
                makeKeyFrame(1100, 140.0, 0.9, 1.1),
                makeKeyFrame(1200, 100.0, 0.8, 1.2),
                makeKeyFrame(1300, 65.0, 0.85, 1.15),
                makeKeyFrame(1400, 35.0, 0.9, 1.1),
                makeKeyFrame(1500, 15.0, 0.95, 1.05),
                makeKeyFrame(1600,0.0,1.0,1.0)
        );

        bouncer.setCycleCount(INDEFINITE);
        bouncer.play();
    }

    private KeyFrame makeKeyFrame(int d, double y, double sx, double sy) {
        return new KeyFrame(
                new Duration(d),
                new KeyValue(orc_end.translateYProperty(), y),
                new KeyValue(orc_end.scaleXProperty(), sx),
                new KeyValue(orc_end.scaleYProperty(), sy)
        );
    }
}
