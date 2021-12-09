package com.example.application;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static javafx.util.Duration.INDEFINITE;

public class GamePlay implements Initializable {
    private Hero hero;

    @FXML
    AnchorPane game_pane;

    @FXML
    Group pauseGroup;

    @FXML
    Group mainGroup;

    public GamePlay(){
        hero = new Hero(126.0,230.0);
    }
    public void showPauseMenu(MouseEvent e) throws IOException {
        SceneController st = new SceneController();
        st.fade(pauseGroup,300,1).play();
        st.fade(mainGroup,300,0.7).play();
        System.out.println("Click is working");
    }
    public void resume(ActionEvent e){
        SceneController st = new SceneController();
        st.fade(pauseGroup,300,0).play();
        st.fade(mainGroup,300,1).play();
        System.out.println("Resume is working");
    }
    public void showEndMenu(ActionEvent e) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("EndGameMenu.fxml"));
        Parent root = loader.load();
        Endgame endgame = loader.getController();
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        endgame.bounce();
    }
    public void showGamePlay(ActionEvent e) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GamePlay.fxml")));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void setup(){
        hero.display(game_pane);
    }
//
//    private Timeline bounce(){
//
//        Timeline bouncer = new Timeline();
//
//        bouncer.getKeyFrames().addAll(
//                makeKeyFrame(0, 0.0, 1.0, 1.0),
//                makeKeyFrame(100, 10.0, 1.025, 1.05),
//                makeKeyFrame(200, 24.0, 1.05, 1.25),
//                makeKeyFrame(300, 40.0, 1.1, 1.1),
//                makeKeyFrame(400, 24.0, 1.05, 1.025),
//                makeKeyFrame(500, 10.0, 1.025, 1.05),
//                makeKeyFrame(600, 0.0, 1.0, 1.0)
//        );
//
//        bouncer.setCycleCount(5);
//        return bouncer;
//    }
//
//    private KeyFrame makeKeyFrame( int d, double y, double sx, double sy) {
//        return new KeyFrame(
//                new Duration(d),
//                new KeyValue(orc_end.translateYProperty(), y),
//                new KeyValue(orc_end.scaleXProperty(), sx),
//                new KeyValue(orc_end.scaleYProperty(), sy)
//        );
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
