package com.example.application;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.ParallelCamera;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
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

    public void showMainMenu(ActionEvent e) throws IOException {
        new SceneController().changeScene(e,"MainMenu.fxml");
    }
    public void Revive(ActionEvent e) throws IOException {
        new SceneController().changeScene(e,"GamePlay.fxml");
    }

    public void bounce(){

        Timeline bouncer = new Timeline();

        bouncer.getKeyFrames().addAll(
                makeKeyFrame(0, 0.0, 0.9, 1.1),
                makeKeyFrame(100, 15.0, 0.925, 1.075),
                makeKeyFrame(200, 35.0, 0.95, 1.05),
                makeKeyFrame(300, 65.0, 1.0, 1.0),
                makeKeyFrame(400,100.0,1.05, 0.95),
                makeKeyFrame(500, 150.0, 1.1, 0.9),
                makeKeyFrame(600, 100.0, 1.05, 0.95),
                makeKeyFrame(700, 65.0, 1.0, 1.0),
                makeKeyFrame(800, 35.0, 0.95, 1.05),
                makeKeyFrame(900, 15.0, 0.925, 1.075),
                makeKeyFrame(1000,0.0,0.9,1.1)
        );

        bouncer.setCycleCount(INDEFINITE);
        bouncer.play();
        //visual_effects(bouncer);
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

        FadeTransition f1 = st.fade(orcs.get(3), 500,1.0);
        FadeTransition f2 = st.fade(orcs.get(5), 600,1.0);
        FadeTransition f3 = st.fade(orcs.get(1), 500,1.0);
        FadeTransition f4 = st.fade(orcs.get(0), 500,1.0);
        FadeTransition f5 = st.fade(orcs.get(4), 700,1.0);
        FadeTransition f6 = st.fade(orcs.get(6), 500,1.0);
        FadeTransition f7 = st.fade(orcs.get(2), 600,1.0);

        SequentialTransition seq = new SequentialTransition (f1,f2,f3,f4,f5,f6,f7);

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
