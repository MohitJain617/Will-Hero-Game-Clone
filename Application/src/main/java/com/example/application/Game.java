package com.example.application;

import javafx.animation.FadeTransition;
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

    public Game(){
        gameplay = new GamePlay();
        icon = new Image("hero.png");
    }

    @Override
    public void start(Stage stage) throws IOException {

        this.stage = stage ;
        stage.setTitle("Will Hero");
        stage.getIcons().add(icon);

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        root.setOpacity(0.2);
        scene = new Scene(root,900,560);

        ShowGameIntro();
    }

    private void ShowGameIntro() throws IOException {

        Parent introRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Intro.fxml")));
        Scene introScene = new Scene(introRoot,900,560);
        stage.setScene(introScene);
        stage.show();

        makeFadeout(introScene);
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

    private void makeFadeout(Scene fadeScene){

        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1000));
        fade.setNode(fadeScene.getRoot());
        fade.setFromValue(1);
        fade.setToValue(0.4);
        fade.setOnFinished((ActionEvent event)-> {
            this.stage.setScene(this.scene);
            makeFadeIn();
        });
        fade.play();
    }

    private void makeFadeIn(){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1000));
        fade.setNode(this.root);
        fade.setFromValue(0.2);
        fade.setToValue(1);
        fade.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}