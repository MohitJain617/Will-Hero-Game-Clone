package com.example.application;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Game extends Application implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;
    private GamePlay gameplay;
    private Image icon ;
    private SceneController st;
    @FXML
    Group mainGroup;
    @FXML
    Group highScores;
    @FXML
    Group settingGroup;

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
        scene = new Scene(root,1280,720);

        ShowGameIntro();
    }

    private void ShowGameIntro() throws IOException {

        Parent introRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Intro.fxml")));
        Scene introScene = new Scene(introRoot,1280,720);
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
        st.changeScene(e,"GamePlay.fxml");
    }
    public void exit(){
        System.exit(0);
    }

    public void showHighScore(ActionEvent e){
        this.highScores.setDisable(false);
        st.fade(mainGroup,300,0.25).play();
        st.fade(highScores,300,1).play();
        this.mainGroup.setDisable(true);
    }
    public void backFromHighScore(MouseEvent e){
        this.mainGroup.setDisable(false);
        st.fade(mainGroup,300,1).play();
        st.fade(highScores,300,0).play();
        this.highScores.setDisable(true);
    }
    public void showSetting(){
        this.settingGroup.setDisable(false);
        st.fade(settingGroup,200,1).play();
    }
    public void hideSettings(){
        this.settingGroup.setDisable(true);
        st.fade(settingGroup,200,0).play();
    }
    public void clickOnSetting(ActionEvent e){
        if(this.settingGroup.getOpacity() == 0) showSetting();
        else hideSettings();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(highScores != null){
            this.highScores.setDisable(true);
        }
        if(settingGroup != null){
            this.settingGroup.setDisable(true);
            this.settingGroup.setOpacity(0);
        }
    }
}