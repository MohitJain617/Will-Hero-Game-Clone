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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static javafx.util.Duration.INDEFINITE;

public class GamePlay implements Initializable {
    private Hero hero;
    private ArrayList<Island> islands;
    AnimationTimer animator;

    @FXML
    AnchorPane game_pane;

    @FXML
    Group pauseGroup;

    @FXML
    Group mainGroup;

    public GamePlay(){

        hero = new Hero(126.0,230.0);
        islands = new ArrayList<Island>();
        animator = new AnimationTimer(){

            @Override
            public void handle(long l) {
                hero.setYspeed(hero.getYspeed() + 0.2);
                hero.gravityEffect();
                for(int i = 0; i < islands.size(); i++){
                    Island currIsland = islands.get(i);
                    if(currIsland.checkCollision(hero)){
                        currIsland.ifHeroCollides(hero);
                    }
                }
            }
        };
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("EndGameMenu.fxml"));
        Parent root = loader.load();
        Endgame endgame = loader.getController();
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        endgame.bounce();
    }
    public void showGamePlay(ActionEvent e) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GamePlay.fxml")));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void setup(){
        hero.display(game_pane);
        islands.add(new Island(79,381,406,170,"island1.png"));
        for(int i = 0; i < islands.size(); i++) islands.get(i).display(game_pane);
        animator.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pauseGroup.setDisable(true);
    }
}
