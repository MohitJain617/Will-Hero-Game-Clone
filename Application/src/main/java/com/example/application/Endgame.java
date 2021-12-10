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

    public void showMainMenu(ActionEvent e) throws IOException {
        new SceneController().changeScene(e,"MainMenu.fxml");
    }
    public void Revive(ActionEvent e) throws IOException {
        new SceneController().changeScene(e,"GamePlay.fxml");
    }

    public void bounce(){


        Timeline boss_bounce = bounce_timeline();
        Timeline orc1_bounce = fxn1();
        Timeline orc2_bounce = fxn2();
        Timeline orc3_bounce = fxn3();

        ParallelTransition par = new ParallelTransition(boss_bounce,orc1_bounce,orc2_bounce,orc3_bounce);
        par.play();

    }

    private Timeline bounce_timeline(){

        Timeline bouncer = new Timeline();

        bouncer.getKeyFrames().addAll(
                makeKeyFrame(orc_end,0, 0.0, 0.9, 1.075),
                makeKeyFrame(orc_end,100, 15.0, 0.925, 1.075),
                makeKeyFrame(orc_end,200, 35.0, 0.95, 1.05),
                makeKeyFrame(orc_end,300, 65.0, 1.0, 1.0),
                makeKeyFrame(orc_end,400,100.0,1.05, 0.95),
                makeKeyFrame(orc_end,500, 155.0, 1.15, 0.85),
                makeKeyFrame(orc_end,520, 155.0, 1.15, 0.85),
                makeKeyFrame(orc_end,600, 100.0, 1.05, 0.95),
                makeKeyFrame(orc_end,700, 65.0, 1.0, 1.0),
                makeKeyFrame(orc_end,800, 35.0, 0.95, 1.05),
                makeKeyFrame(orc_end,900, 15.0, 0.925, 1.075),
                makeKeyFrame(orc_end,1000,0.0,0.9,1.1)
        );

        bouncer.setCycleCount(INDEFINITE);
        return bouncer;
    }

    private Timeline fxn1(){

        Timeline bouncer = new Timeline();

        bouncer.getKeyFrames().addAll(
                makeKeyFrame(orc1,0, 0.0, 0.9, 1.075),
                makeKeyFrame(orc1,100, 20.0, 0.925, 1.075),
                makeKeyFrame(orc1,200, 50.0, 0.95, 1.05),
                makeKeyFrame(orc1,300, 90.0, 1.0, 1.0),
                makeKeyFrame(orc1,400,150.0,1.05, 0.95),
                makeKeyFrame(orc1,500, 215.0, 1.15, 0.85),
                makeKeyFrame(orc1,520, 215.0, 1.15, 0.85),
                makeKeyFrame(orc1,620, 150.0, 1.05, 0.95),
                makeKeyFrame(orc1,720, 90.0, 1.0, 1.0),
                makeKeyFrame(orc1,820, 50.0, 0.95, 1.05),
                makeKeyFrame(orc1,920, 20.0, 0.925, 1.075),
                makeKeyFrame(orc1,1020,0.0,0.9,1.1)
        );

        bouncer.setCycleCount(INDEFINITE);
        return bouncer;
    }

    private Timeline fxn2(){

        Timeline bouncer = new Timeline();

        bouncer.getKeyFrames().addAll(
                makeKeyFrame(orc2,0, 0.0, 0.9, 1.1),
                makeKeyFrame(orc2,70, 15.0, 0.95, 1.05),
                makeKeyFrame(orc2,140, 35.0, 1.0, 1.0),
                makeKeyFrame(orc2,210, 70.0, 1.05,0.95 ),
                makeKeyFrame(orc2,280,110.0,1.1, 0.9),
                makeKeyFrame(orc2,350, 160.0, 1.15, 0.85),
                makeKeyFrame(orc2,380, 160.0, 1.15, 0.85),
                makeKeyFrame(orc2,450,110.0,1.1, 0.9),
                makeKeyFrame(orc2,520, 70.0, 1.05, 0.95),
                makeKeyFrame(orc2,590, 35.0,1.0 , 1.0),
                makeKeyFrame(orc2,660, 15.0, 0.95, 1.05),
                makeKeyFrame(orc2,730,0.0,0.9,1.1)
        );

        bouncer.setCycleCount(INDEFINITE);
        return bouncer;
    }

    private Timeline fxn3(){

        Timeline bouncer = new Timeline();

        bouncer.getKeyFrames().addAll(
                makeKeyFrame(orc3,0, 0.0, 0.9, 1.1),
                makeKeyFrame(orc3,80, 20.0, 0.95, 1.05),
                makeKeyFrame(orc3,160, 45.0, 1.0, 1.0),
                makeKeyFrame(orc3,240, 80.0, 1.05,0.95 ),
                makeKeyFrame(orc3,320,130.0,1.1, 0.9),
                makeKeyFrame(orc3,400, 190.0, 1.15, 0.85),
                makeKeyFrame(orc3,440, 190.0, 1.15, 0.85),
                makeKeyFrame(orc3,520, 130.0, 1.1, 0.9),
                makeKeyFrame(orc3,600, 80.0, 1.05, 0.95),
                makeKeyFrame(orc3,680, 45.0, 1.0, 1.0),
                makeKeyFrame(orc3,760, 20.0, 0.95, 1.05),
                makeKeyFrame(orc3,840,0.0,0.9,1.1)
        );

        bouncer.setCycleCount(INDEFINITE);
        return bouncer;
    }

    private KeyFrame makeKeyFrame(ImageView obj ,int d, double y, double sx, double sy) {
        return new KeyFrame(
                new Duration(d),
                new KeyValue(obj.translateYProperty(), y),
                new KeyValue(obj.scaleXProperty(), sx),
                new KeyValue(obj.scaleYProperty(), sy)
        );
    }
}

//    public void visual_effects(Timeline bounce){
//
//        ArrayList<ImageView> orcs = new ArrayList<>();
//        orcs.add(orc1);
//        orcs.add(orc2);
//        orcs.add(orc3);
//
//        Random rand = new Random();
//        SceneController st = new SceneController();
//
//        FadeTransition f1 = st.fade(orcs.get(3), 500,1.0);
//        FadeTransition f2 = st.fade(orcs.get(5), 600,1.0);
//        FadeTransition f3 = st.fade(orcs.get(1), 500,1.0);
//        FadeTransition f4 = st.fade(orcs.get(0), 500,1.0);
//        FadeTransition f5 = st.fade(orcs.get(4), 700,1.0);
//        FadeTransition f6 = st.fade(orcs.get(6), 500,1.0);
//        FadeTransition f7 = st.fade(orcs.get(2), 600,1.0);
//
//        SequentialTransition seq = new SequentialTransition (f1,f2,f3,f4,f5,f6,f7);
//
//        ParallelTransition par = new ParallelTransition(seq,bounce);
//        par.setOnFinished(actionEvent -> {
//            visual_effects(bounce);
//        });
//        par.play();
//    }



//        PauseTransition p1 = new PauseTransition(Duration.millis(1000));
//        PauseTransition p2 = new PauseTransition(Duration.millis(500));
//        PauseTransition p3 = new PauseTransition(Duration.millis(1500));

//    SceneController st = new SceneController();
//        st.fade(pauseGroup,300,1).play();
//        st.fade(mainGroup,300,0.7).play();
//        System.out.println("Click is working");
//}
