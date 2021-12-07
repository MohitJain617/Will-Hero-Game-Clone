package com.example.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GamePlay {
    Hero hero;
    @FXML
    AnchorPane game_pane;
    public GamePlay(){
        hero = new Hero(126.0,230.0);
    }
    public void showPauseMenu(ActionEvent e) throws IOException {
        Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PauseMenu.fxml")));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root2);
        stage.setScene(scene);
        stage.show();
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
        setup();
        stage.show();
    }
    public void setup(){
        hero.display(game_pane);
    }

}
