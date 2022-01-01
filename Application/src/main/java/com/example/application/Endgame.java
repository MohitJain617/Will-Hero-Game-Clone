package com.example.application;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

import static javafx.animation.Animation.INDEFINITE;

public class Endgame implements Initializable {
    private GamePlay gameplay;
    private ArrayList<Orcs> orcs;
    private ArrayList<Island> islands;
    private User user ;
    AnimationTimer animator;

    @FXML
    AnchorPane end_pane;

    @FXML
    Group settingGroup;

    @FXML
    private Text Score ;

    @FXML
    private Text High_Score;

    @FXML
    private Text Coins;

    public Endgame(){
        gameplay = new GamePlay();
        user = null ;
        orcs = new ArrayList<Orcs>();
        orcs.add(new BossOrc(959,40));
        orcs.add(new RedOrc(291,207));
        orcs.add(new RedOrc(75,60));
        orcs.add(new StdOrc(179,182));
        islands = new ArrayList<Island>();
        islands.add(new Island(801,460,433,172,"island_large1.png"));
        islands.add(new Island(45,455,407,162,"island1.png"));

        animator = new AnimationTimer() {
            @Override
            public void handle(long l) {

                //gravity on obstacles
                for(Orcs obs: orcs){
                    if(obs instanceof GreenOrc) obs.setYspeed(obs.getYspeed() + 0.3);
                    else obs.setYspeed(obs.getYspeed() + 0.2);
                    obs.gravityEffect();
                }
                for (Island currIsland : islands) {
                    //check collision with obstacles
                    for(Obstacle obs: orcs){
                        if(currIsland.checkCollision(obs)){
                            currIsland.ifObstacleCollides(obs);
                        }
                    }
                }
            }
        };
    }
    public void showMainMenu(ActionEvent e) throws IOException {
        animator.stop();
        new SceneController().changeScene(e,"MainMenu.fxml");
    }
    public void Revive(ActionEvent e) throws IOException {
        if(this.gameplay == null){
            System.out.println("Null boi");
        }
        animator.stop();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("GamePlay.fxml")));
        Parent root = loader.load();
        GamePlay currentGamePlay = loader.getController();
        currentGamePlay.copy(this.gameplay);
        this.gameplay = currentGamePlay;
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showSetting(){
        this.settingGroup.setDisable(false);
        new SceneController().fade(settingGroup,200,1).play();
    }
    public void hideSettings(){
        this.settingGroup.setDisable(true);
        new SceneController().fade(settingGroup,200,0).play();
    }
    public void clickOnSetting(ActionEvent e){
        if(this.settingGroup.getOpacity() == 0) showSetting();
        else hideSettings();
    }

    public void setGamePlay(GamePlay gp) throws IOException {
        System.out.println("Copying the previous gameplay hopefully");
        this.gameplay.copy(gp);
        this.user = User.getInstance();
        set_scores();
        setReviveButton();
    }

    public void setReviveButton(){
        if(this.gameplay.canRevive() && this.user.getCoins() >= 3){

        }
        else {

        }
    }
    public void set_scores(){

        Coins.setText(user.getCoins()+" Coins");

        if(user.setHighScore(gameplay.getScore())){
            Score.setText("Current Score : "+user.getHighScore());
            High_Score.setText("New High Score : "+ user.getHighScore());
        }

        else{
            Score.setText("Current Score : "+ gameplay.getScore());
            High_Score.setText("High Score : "+ user.getHighScore());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(end_pane != null) {
            for (Orcs orc : orcs) orc.display(end_pane);
            for (Island isle: islands) isle.display(end_pane);
            animator.start();
        }
        if(settingGroup != null){
            this.settingGroup.setDisable(true);
            this.settingGroup.setOpacity(0);
        }
    }
}

