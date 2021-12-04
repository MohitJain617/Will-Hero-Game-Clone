package com.example.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GamePlay {
    Parent root;
    Scene scene;
    Stage stage;
    public void showPauseMenu(ActionEvent e) throws IOException {
        Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PauseMenu.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root2);
        stage.setScene(scene);
        stage.show();
    }
    public void showEndMenu(ActionEvent e) throws IOException {
        Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EndGameMenu.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root2);
        stage.setScene(scene);
        stage.show();
    }
    public void showGamePlay(ActionEvent e) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GamePlay.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
