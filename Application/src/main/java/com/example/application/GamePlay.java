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
import java.util.*;

public class GamePlay implements Initializable {
    private Hero hero;
    private ArrayList<Island> islands;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Reward> rewards;
    AnimationTimer animator;
    private long dashTime;
    private final Random random ;
    int cnt = 0 ;

    @FXML
    AnchorPane game_pane;

    @FXML
    Group pauseGroup;

    @FXML
    Group mainGroup;

    public GamePlay(){

        islands = new ArrayList<Island>();
        obstacles = new ArrayList<Obstacle>();
        rewards = new ArrayList<Reward>();
        random = new Random();
        setup_Game();
        dashTime = System.nanoTime();
        animator = new AnimationTimer(){

            @Override
            public void handle(long now) {
                //-----HERO--------
                if(!hero.isAlive()) {
                    System.out.println(hero.getLocation().getY());
                    System.out.println("Hero died!");              // Show end menu
                    try {
                        animator.stop();
                        new SceneController().changeScene(game_pane,"EndGameMenu.fxml");
                        return ;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //normal gravity on hero
                if(dashTime <= now) {
                    hero.setXspeed(0);
                    hero.setYspeed(hero.getYspeed() + 0.3);
                    hero.gravityEffect();

                } else {
                   hero.setYspeed(0.5);
                   hero.gravityEffect();
                }

                //-------OBSTACLES and ISLANDS----------
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

                   //make the objects move backwards
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
                    for(Reward reward: rewards){
                        Location curr = reward.getLocation();
                        reward.setLocation(curr.getX()-dashSpeed,curr.getY());
                        reward.updateLocation();
                    }
                }
                //---------COLLISION CHECKS-------------
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
                //hero vs reward and obstacles
                for(Reward reward: rewards){
                    if(reward.checkCollision(hero)){
                        reward.ifHeroCollides(hero);
                    }
                }
                for(Obstacle obs: obstacles){
                    if(obs.checkCollision(hero)){
                        dashTime = 0;
                        obs.ifHeroCollides(hero);
                    }
                }
                //REMOVAL of OBSTACLES:
                obstacles.removeIf(obs -> obs.isAlive() == false);

            }
        };
    }

    public void setup_Game(){

        hero = new Hero(300.0,230.0);
        rewards.add(new CoinChest(2800,310));

        int x = 250;

        String []Large_Island_Images = {"island1.png","island_large1.png","island_large2.png"};
        String []Small_Island_Images = {"island_med.png","island_small.png"};

        for(int i=1;i<=11;i++){

            ArrayList<Integer>ind1 = new ArrayList<Integer>() ;
            ArrayList<Integer>ind2 = new ArrayList<Integer>() ;

            for(int k=1;k<=3;k++){ ind1.add(random.nextInt(Large_Island_Images.length)); }
            for(int k=1;k<=2;k++){ ind2.add(random.nextInt(Small_Island_Images.length)); }

            islands.add(new Island(x+0,368,358,126,Large_Island_Images[ind1.get(0)]));
            islands.add(new Island(x+500,363,358,126,Small_Island_Images[ind2.get(0)]));

            obstacles.add(new RedOrc(x+600,126));

            islands.add(new Island(x+950,368,358,126,Large_Island_Images[ind1.get(1)]));
            islands.add(new Island(x+1450,375,358,126,Small_Island_Images[ind2.get(1)]));

            obstacles.add(new StdOrc(x+1550,126));

            islands.add(new Island(x+1900,364,358,160,Large_Island_Images[ind1.get(2)]));
            obstacles.add(new StdOrc(x+2000,126));

            x+=2450 ;
        }

        islands.add(new Island(x,364,358,160,"island_large2.png"));
        obstacles.add(new BossOrc(x+100,126));
        //TNT
        obstacles.add(new TNT(2200,295));
    }

    public void heroDash(MouseEvent e){
        dashTime = System.nanoTime() + 150000000;
        System.out.println(cnt++);
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
        for (Island island : islands) island.display(game_pane);
        for(Obstacle obs: obstacles) obs.display(game_pane);
        for(Reward rew: rewards) rew.display(game_pane);
        pauseGroup.setDisable(true);
        animator.start();
    }
}
