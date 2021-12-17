package com.example.application;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class GamePlay implements Initializable {
    private Hero hero;
    private ArrayList<Island> islands;
    private ArrayList<Obstacle> obstacles;
    AnimationTimer animator;
    private long dashTime;

    @FXML
    AnchorPane game_pane;

    @FXML
    Group pauseGroup;

    @FXML
    Group mainGroup;

    public GamePlay(){

        hero = new Hero(300.0,230.0);
        islands = new ArrayList<Island>();
        obstacles = new ArrayList<Obstacle>();
        obstacles.add(new RedOrc(1000,206));
        obstacles.add(new BossOrc(659,126));
        dashTime = System.nanoTime();
        animator = new AnimationTimer(){

            @Override
            public void handle(long now) {
                //normal gravity on hero
                if(dashTime <= now) {
                    hero.setXspeed(0);
                    hero.setYspeed(hero.getYspeed() + 0.3);
                    hero.gravityEffect();

                } else {
                    hero.setYspeed(0);
                }

                //gravity on obstacles
                for(Obstacle obs: obstacles){
                    if(obs instanceof Orcs){
                        ((Orcs) obs).setYspeed(((Orcs) obs).getYspeed() + 0.35);
                    }
                    obs.gravityEffect();
                }
                //check for dash

                int dashSpeed = 60 ;

                if(dashTime > now){

                   //make the objects move forward
                    for(Obstacle obs: obstacles){
                        Location curr = obs.getLocation();
                        obs.setLocation(curr.getX()-dashSpeed,curr.getY());
                        obs.updateLocation();
                    }
                    for(Island currIsland : islands){
                        Location curr = currIsland.getLocation();
                        currIsland.setLocation(curr.getX()-dashSpeed,curr.getY());
                        currIsland.updateLocation();
                    }
                }
                for (Island currIsland : islands) {
                    //check collision with hero
                    if (currIsland.checkCollision(hero)) {
                        currIsland.ifHeroCollides(hero);
                    }
                    //check collision with obstacles
                    for(Obstacle obs: obstacles){
                        if(currIsland.checkCollision(obs)){
                            currIsland.ifObstacleCollides(obs);
                        }
                    }
                }
            }
        };
    }
    public void heroDash(MouseEvent e){
        dashTime = System.nanoTime() + 150000000;
    }
    public void showPauseMenu(MouseEvent e) throws IOException {
        animator.stop();
        SceneController st = new SceneController();
        pauseGroup.setDisable(false);
        st.fade(pauseGroup,300,1).play();
        st.fade(mainGroup,300,0.7).play();
    }

    public void resume(ActionEvent e){
        animator.start();
        pauseGroup.setDisable(true);
        SceneController st = new SceneController();
        st.fade(pauseGroup,300,0).play();
        st.fade(mainGroup,300,1).play();
    }
    public void showEndMenu(ActionEvent e) throws IOException {
        new SceneController().changeScene(e,"EndGameMenu.fxml");
    }
    public void showGamePlay(ActionEvent e) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GamePlay.fxml")));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hero.display(game_pane);
        islands.add(new Island(113,366,358,126,"island1.png"));
        islands.add(new Island(945,366,358,126,"island1.png"));
        islands.add(new Island(500,366,358,126,"island1.png"));
        for (Island island : islands) island.display(game_pane);
        for(Obstacle obs: obstacles) obs.display(game_pane);
        pauseGroup.setDisable(true);
        animator.start();
    }
}
