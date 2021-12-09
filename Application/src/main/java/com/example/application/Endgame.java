package com.example.application;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.ParallelCamera;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

import static javafx.animation.Animation.INDEFINITE;

public class Endgame {

    @FXML
    ImageView orc_end ;

    @FXML
    ImageView orc1;

    @FXML
    ImageView orc2;

    @FXML
    ImageView orc3;

    @FXML
    ImageView orc4;

    @FXML
    ImageView orc5;

    @FXML
    ImageView orc6;

    @FXML
    ImageView orc7;

    public void bounce(){

        Timeline bouncer = new Timeline();

        bouncer.getKeyFrames().addAll(
                makeKeyFrame(0, 0.0, 1.0, 1.0),
                makeKeyFrame(100, 15.0, 0.95, 1.05),
                makeKeyFrame(200, 35.0, 0.9, 1.1),
                makeKeyFrame(300, 65.0, 0.85, 1.15),
                makeKeyFrame(400,100.0,0.8,1.2),
                makeKeyFrame(500, 135.0, 0.9, 1.1),
                makeKeyFrame(600, 140.0, 1.1, 0.9),
                makeKeyFrame(650, 145.0, 1.2, 0.8),
                makeKeyFrame(700, 140.0, 1.1, 0.9),
                makeKeyFrame(800, 135.0, 0.9, 1.1),
                makeKeyFrame(900, 90.0, 0.8, 1.2),
                makeKeyFrame(1000, 60.0, 0.85, 1.15),
                makeKeyFrame(1100, 35.0, 0.9, 1.1),
                makeKeyFrame(1200, 15.0, 0.95, 1.05),
                makeKeyFrame(1300,0.0,1.0,1.0)
        );

        bouncer.setCycleCount(2);
        visual_effects(bouncer);
    }

    private KeyFrame makeKeyFrame(int d, double y, double sx, double sy) {
        return new KeyFrame(
                new Duration(d),
                new KeyValue(orc_end.translateYProperty(), y),
                new KeyValue(orc_end.scaleXProperty(), sx),
                new KeyValue(orc_end.scaleYProperty(), sy)
        );
    }

    public void visual_effects(Timeline bounce){

        ArrayList<ImageView> orcs = new ArrayList<>();
        orcs.add(orc1);
        orcs.add(orc2);
        orcs.add(orc3);
        orcs.add(orc4);
        orcs.add(orc5);
        orcs.add(orc6);
        orcs.add(orc7);

        Random rand = new Random();
        SceneController st = new SceneController();

        int r = rand.nextInt(7);
        int s = rand.nextInt(7);
        int t = rand.nextInt(7);

        FadeTransition f1 = st.fade(orcs.get(1), 400,1.0);
        FadeTransition f2 = st.fade(orcs.get(s), 400,1.0);
        FadeTransition f3 = st.fade(orcs.get(t), 500,1.0);

        SequentialTransition seq = new SequentialTransition (f1,f2,f3);

        ParallelTransition par = new ParallelTransition(seq,bounce);
        par.setOnFinished(actionEvent -> {
            visual_effects(bounce);
        });
        par.play();
    }

}


//        PauseTransition p1 = new PauseTransition(Duration.millis(1000));
//        PauseTransition p2 = new PauseTransition(Duration.millis(500));
//        PauseTransition p3 = new PauseTransition(Duration.millis(1500));

//    SceneController st = new SceneController();
//        st.fade(pauseGroup,300,1).play();
//        st.fade(mainGroup,300,0.7).play();
//        System.out.println("Click is working");
//}
