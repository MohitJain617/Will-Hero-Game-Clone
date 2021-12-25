package com.example.application;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

public class GamePlay implements Serializable {
    private Hero hero;
    private ArrayList<Island> islands;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Reward> rewards;
    transient AnimationTimer animator;
    private long dashTime;
    private final Random random ;
    private int cnt = 0 ;
    private boolean pause;
    private transient Game game;
    private transient SceneController st;

    @FXML
    private transient AnchorPane game_pane;

    @FXML
    private transient Group pauseGroup;

    @FXML
    private transient Group savingGroup;

    @FXML
    private transient Group mainGroup;
    @FXML
    private transient TextField tf;
    @FXML
    private transient Text message;

    @FXML
    private transient Text score ;

    public GamePlay(){

        islands = new ArrayList<Island>();
        obstacles = new ArrayList<Obstacle>();
        rewards = new ArrayList<Reward>();
        random = new Random();
        pause = false;
        setup_Game();
        dashTime = System.nanoTime();
        st = new SceneController();
        animatorLogic();
    }
    private void animatorLogic(){
        animator = new AnimationTimer(){

            @Override
            public void handle(long now) {
                //-----HERO--------
                if(!hero.isAlive()) {
                    System.out.println(hero.getLocation().getY());
                    System.out.println("Hero died!");              // Show end menu
                    try {
                        animator.stop();
                        hero = new Hero(300,120);
                        //hero.setLocation(300,120);
                        showEndMenu(game_pane);
                        return ;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // Normal Gravity on hero
                if(dashTime <= now) {
                    hero.setXspeed(0);
                    hero.setYspeed(hero.getYspeed() + 0.3);
                    hero.gravityEffect();

                } else {
                    hero.setYspeed(0.5);          // To restrict unlimited flying
                    hero.gravityEffect();
                }

                //-------OBSTACLES and ISLANDS----------

                // Gravity on obstacles

                for (Obstacle obs : obstacles) {
                    if (obs instanceof Orcs) {
                        ((Orcs) obs).setYspeed(((Orcs) obs).getYspeed() + 0.35);
                    }
                }

                // Checking for Dash
                int dashSpeed = 70 ;

                if(dashTime > now){
                    // Make all the objects move backwards
                    for(Obstacle obs: obstacles){
                        Location curr = obs.getLocation();
                        obs.setLocation(curr.getX()-dashSpeed,curr.getY());
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

                for(Obstacle obs: obstacles){
                    obs.gravityEffect();
                }

                //---------COLLISION CHECKS-------------

                // Jumps on Islands
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
                        dashTime = dashSpeed/100;
                        obs.ifHeroCollides(hero);
                    }
                }

                for( int i=0 ; i < obstacles.size() ; i++ ){

                    if(obstacles.get(i) instanceof TNT){ continue ; }

                    for( int j=i+1 ; j < obstacles.size() ; j++){

                        if(obstacles.get(i) instanceof TNT){ continue ; }

                        Obstacle orc1 = obstacles.get(i);
                        Obstacle orc2 = obstacles.get(j);

                        if(orc1.checkCollision(orc2)){

                            System.out.println(i+" "+j);
                            orc1.ifObstacleCollides(orc2);
                        }
                    }
                }
                //REMOVAL of OBSTACLES:
                ArrayList<Obstacle> remove = new ArrayList<>();

                for(Obstacle obs : obstacles){
                    if(!obs.isAlive()){
                        // obs.getCoins();              //  1
                        hero.setCollectedCoins(hero.getCollectedCoins()+1);
                        remove.add(obs);
                    }
                }

                for(Obstacle obs : remove){ obstacles.remove(obs) ; }

                score.setText(hero.getCollectedCoins() + " Coins");
            }
        };

    }

    public void setup_Game(){
        hero = new Hero(300.0,230.0);
        rewards.add(new CoinChest(2800,310));

        int x = 250;

        String []Large_Island_Images = {"island1.png","island_large1.png","island_large2.png"};
        String []Small_Island_Images = {"island_med.png","island_small.png"};

        obstacles.add(new StdOrc(x+600,26));

        for(int i=1;i<=11;i++){

            ArrayList<Integer>ind1 = new ArrayList<Integer>() ;
            ArrayList<Integer>ind2 = new ArrayList<Integer>() ;

            for(int k=1;k<=3;k++){ ind1.add(random.nextInt(Large_Island_Images.length)); }
            for(int k=1;k<=2;k++){ ind2.add(random.nextInt(Small_Island_Images.length)); }

            islands.add(new Island( x+0 ,368,358,126,Large_Island_Images[ind1.get(0)]));
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
        obstacles.add(new TNT(3950,295));
    }

    public void heroDash(MouseEvent e){
        dashTime = System.nanoTime() + 100000000;
        System.out.println(cnt++);
    }
    public void showPauseMenu(MouseEvent e) throws IOException {
        animator.stop();
        pauseGroup.setDisable(false);
        st.fade(pauseGroup,300,1).play();
        st.fade(mainGroup,300,0.7).play();
    }

    public void resume(ActionEvent e) throws IOException {
        animator.start();
        pauseGroup.setDisable(true);
        exitSaveScreen(null);
        SceneController st = new SceneController();
        st.fade(pauseGroup,300,0).play();
        st.fade(mainGroup,300,1).play();
    }
    public void showEndMenu(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("EndGameMenu.fxml")));
        Parent root = loader.load();
        Endgame ender = loader.getController();
        ender.setGamePlay(this);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void showEndMenu(Node e) throws IOException{
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("EndGameMenu.fxml")));
        Parent root = loader.load();
        Endgame ender = loader.getController();
        ender.setGamePlay(this);
        Stage stage = (Stage)(e.getScene().getWindow());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void showSaveScreen(ActionEvent e){
        this.savingGroup.setDisable(false);
        st.fade(savingGroup,300,1).play();
    }
    public void saveCurrentGame(ActionEvent e) throws IOException {
        if(tf.getText().equals("")){
            message.setText("Empty file name");
        } else {
            message.setText(this.game.addGamePlay(tf.getText(),this));
        }
    }
    public void exitSaveScreen(ActionEvent e) throws IOException {
        this.savingGroup.setDisable(true);
        st.fade(savingGroup,300,0).play();
    }

    public void copy(GamePlay gp) throws IOException {
        this.islands = gp.islands;
        this.hero = gp.hero;
        this.dashTime = gp.dashTime;
        this.obstacles = gp.obstacles;
        this.rewards = gp.rewards;
        this.pause = gp.pause;
        this.game = gp.game;
        this.reinitialize();
    }
    public void setGame(Game ga) {
        this.game = ga;
    }
    public void reinitialize() throws IOException {
        if(game_pane != null){
            hero.displayWeapon(game_pane);
            hero.display(game_pane);
            for (Island island : islands) island.display(game_pane);
            for(Obstacle obs: obstacles) obs.display(game_pane);
            for(Reward rew: rewards) rew.display(game_pane);
            pauseGroup.setDisable(true);
            score.setText(hero.getCollectedCoins()+" Coins");
            if(!pause) animator.start();
            else {
                showPauseMenu(null);
            }
            game_pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent k) {
                    if(k.getCode().equals(KeyCode.SPACE)){
                        //TODO
                        heroDash(null);
                    }
                }
            });
        }
    }
}
