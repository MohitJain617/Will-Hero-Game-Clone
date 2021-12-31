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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Game extends Application implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;
    private GamePlay gameplay;
    private Image icon ;
    private SceneController st;
    private HashMap<String, GamePlay> savedGames;
    @FXML
    Group mainGroup;
    @FXML
    Group highScores;
    @FXML
    Group settingGroup;

    //Load Game injections
    @FXML
    private Text selectText;
    @FXML
    private ChoiceBox<String> gameChoiceBox;

    public Game(){
        st = new SceneController();
        gameplay = new GamePlay();
        gameplay.setGame(this);
        icon = new Image("hero.png");
        savedGames = new HashMap<String,GamePlay>();
        //TO initialize the output.ser
//        try {
//            serialize();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
        this.gameplay.setGame(this);
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("GamePlay.fxml")));
        root = loader.load();
        GamePlay currentGamePlay = loader.getController();
        currentGamePlay.copy(this.gameplay);
        this.gameplay = currentGamePlay;
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
    public void serialize() throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("src\\output.ser"));
            out.writeObject(savedGames);
        } catch (IOException e) {
            //do something
            e.printStackTrace();
        } catch (NullPointerException f){
            System.out.println("Null pointer exception");
        }
        finally
        {
            if (out != null) out.close();
        }
    }
    public void deserialize() throws IOException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("src\\output.ser"));
            System.out.println("Does reach checkpoint 1");
            savedGames = (HashMap)in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found exception des");
        } finally {
            if (in != null) in.close();
        }
    }
    public String addGamePlay(String name, GamePlay gp) throws IOException {
        deserialize();
        //-----PRINTING STUFF TO CHECK-----
        for(Map.Entry map: savedGames.entrySet()){
            String key = (String) map.getKey();
            System.out.println(key);
        }
        //-----whats inside the hashmap-----
        if(savedGames.containsKey(name)){
            return "Given name already exists";
        } else {
            savedGames.put(name,gp);
            serialize();
            return "Save successful";
        }
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
        //------Initalizing for loadGame----------
        if(gameChoiceBox != null){

            try {
                deserialize();
            } catch (IOException e) {
                e.printStackTrace();
            }
            gameChoiceBox.getItems().addAll(savedGames.keySet());
        }
        //----------------------------------------
    }
    //Event Handlers inside Load Game:
    public void playLoadedGame(ActionEvent e) {
        if(gameChoiceBox == null) {
            return;
        }
        else if(gameChoiceBox.getValue() == null) {
            this.selectText.setText("No saved game selected");
        } else {
            String key = gameChoiceBox.getValue();
            this.gameplay = savedGames.get(key);
            try {
                showGamePlay(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void deleteGame(ActionEvent e){
        if(gameChoiceBox != null){
            String key = gameChoiceBox.getValue();
            if(key == null){
                this.selectText.setText("No saved game selected");
            } else {
                savedGames.remove(key);
                try {
                    serialize();
                    showMainMenu(e);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}