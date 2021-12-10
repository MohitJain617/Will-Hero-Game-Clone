package com.example.application;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void changeScene(ActionEvent e, String filename) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(filename)));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public FadeTransition fade(Node n1, int duration, double to){
        FadeTransition fade = new FadeTransition(Duration.millis(duration),n1);
        fade.setToValue(to);
        fade.setCycleCount(1);
        return fade;
    }
    public void something(Node n1){
        TranslateTransition tran = new TranslateTransition();
    }
}
