package com.example.application;

import javafx.event.ActionEvent;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

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
        Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EndGameMenu.fxml")));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root2);
        stage.setScene(scene);
        stage.show();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
