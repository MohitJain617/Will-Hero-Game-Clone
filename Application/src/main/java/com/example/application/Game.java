package com.example.application;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
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
    private SceneController st;

    public Game(){
        st = new SceneController();
        gameplay = new GamePlay();
        icon = new Image("hero.png");
    }

    @Override
    public void start(Stage stage) throws IOException {

        this.stage = stage ;
        stage.setTitle("Will Hero");
        stage.getIcons().add(icon);
        stage.setResizable(false);

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        root.setOpacity(0.5);
        scene = new Scene(root,960,540);

        ShowGameIntro();
    }

    private void ShowGameIntro() throws IOException {

        Parent introRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Intro.fxml")));
        Scene introScene = new Scene(introRoot,960,540);
        stage.setScene(introScene);
        stage.show();

        fadeintro(introScene);
    }

    public void showMainMenu(ActionEvent e) throws IOException {
        st.changeScene(e,"MainMenu.fxml");
    }
    public void showLoadMenu(ActionEvent e) throws IOException {
        st.changeScene(e,"LoadGames.fxml");
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
        st.changeScene(e,"EndGameMenu.fxml");
    }

    private void fadeintro(Scene fadeScene){

        FadeTransition fadeout = new FadeTransition();
        fadeout.setDuration(Duration.millis(900));
        fadeout.setNode(fadeScene.getRoot());
        fadeout.setFromValue(1);
        fadeout.setToValue(0.8);
        fadeout.setOnFinished((ActionEvent event)-> {
            this.stage.setScene(this.scene);
        });

        FadeTransition fadein = new FadeTransition();
        fadein.setDuration(Duration.millis(1500));
        fadein.setNode(this.root);
        fadein.setFromValue(0.5);
        fadein.setToValue(1);
        fadein.play();

        PauseTransition pause = new PauseTransition(Duration.millis(1000));

        SequentialTransition seqTransition = new SequentialTransition (pause,fadeout,fadein);
        seqTransition.play();
    }


    public static void main(String[] args) {
        launch(args);
    }
}