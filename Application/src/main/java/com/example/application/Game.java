package com.example.application;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class Game extends Application {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private GamePlay gameplay;
    private Image icon ;
    //-------------------------
    public Game(){
        gameplay = new GamePlay();
        icon = new Image("hero.png");
    }
    @Override
    public void start(Stage stage) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        stage.setTitle("WillHero");
        scene = new Scene(root);
        stage.getIcons().add(icon);

        Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Intro.fxml")));
        Scene introscene = new Scene(root2);

        stage.setScene(introscene);
        stage.show();
        PauseTransition del = new PauseTransition(Duration.seconds(1));
        del.setOnFinished(event -> stage.setScene(scene));
        del.play();
    }

    public void showMainMenu(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void showLoadMenu(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoadGames.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void showGamePlay(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
        root = loader.load();
        gameplay = loader.getController();
        gameplay.setup();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
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

    public static void main(String[] args) {
        launch();
    }
}