package com.example.application;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class GamePlay implements Serializable {
    @Serial
    private static final long serialVersionUID = 6L;
    private Hero hero;
    private ArrayList<Island> islands;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Reward> rewards;
    private ArrayList<Weapon> weaponInstances;
    transient AnimationTimer animator;
    private int jumps ;
    private long dashTime;
    private final Random random ;
    private transient ImageView crown_Img ;
    private int deathCount;
    private boolean pause;
    private final double[] mov = new double[]{ 0.8,0.8,0.8,0.85,0.85};
    private transient Game game;
    private transient SceneController st;
    private transient ArrayList<ImageView> clouds;

    @FXML
    private transient Group swordGroup;
    @FXML
    private transient Group knifeGroup;
    @FXML
    private transient Text swordText;
    @FXML
    private transient Text knifeText;

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

    @FXML
    private transient ImageView cloud_1;

    @FXML
    private transient ImageView cloud_2;

    @FXML
    private transient ImageView cloud_3;

    @FXML
    private transient ImageView cloud_4;

    @FXML
    private transient ImageView cloud_5;

    @FXML
    private transient Text jump_count;

    @FXML
    private transient ImageView bg_Island;

    public GamePlay(){

        islands = new ArrayList<Island>();
        obstacles = new ArrayList<Obstacle>();
        rewards = new ArrayList<Reward>();
        clouds = new ArrayList<ImageView>();
        crown_Img = new ImageView(new Image("crownn.gif"));
        random = new Random();
        pause = false;
        jumps = 0 ;
        setup_Game();
        dashTime = System.nanoTime();
        st = new SceneController();
        weaponInstances = new ArrayList<Weapon>();
        deathCount = 0;
        animatorLogic();
    }
    private void animatorLogic(){
        animator = new AnimationTimer(){

            @Override
            public void handle(long now) {

                if(hero.getLocation().getX() >= crown_Img.getX()+5 ){
                    try {
                        animator.stop();
                        showVictoryScreen(game_pane);
                        return ;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //-----HERO--------
                if(!hero.isAlive()) {
                    System.out.println(hero.getLocation().getY());
                    System.out.println("Hero died!");              // Show end menu
                    try {
                        animator.stop();
                        int points = hero.getCollectedCoins() ;
//                        hero = new Hero(300,120);
//                        hero.setCollectedCoins(points);
                        hero.makeAlive();
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

                //-----Animate weapons------
                for(Weapon weapon : weaponInstances){
                    if(weapon.use()){
                        weapon.gravityEffect();
                    }
                }

                //------remove animated weapons-----
                Iterator<Weapon> itr = weaponInstances.iterator();
                while(itr.hasNext()){

                    Weapon wi = itr.next();
                    if(!wi.use()){  itr.remove(); wi.undisplay(game_pane); }

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

                    crown_Img.setX(crown_Img.getX()-dashSpeed);
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
                        if(currIsland.checkCollision(obs)) {
                            currIsland.ifObstacleCollides(obs);
                        }
                    }
                }
                //hero vs reward and obstacles
                for(Reward reward: rewards){
                    if(reward.checkCollision(hero)){
                        reward.ifHeroCollides(hero);
                        if(reward instanceof WeaponChest) {
                            hero.displayWeapon(game_pane);
                            setWeaponButtons();
                        }
                    }
                }
                for(Obstacle obs: obstacles){
                    if(obs.checkCollision(hero)){
                        dashTime = dashTime/100;
                        obs.ifHeroCollides(hero);
                    }
                    for(Weapon we: weaponInstances){
                        if(obs.checkCollision(we)){
                            obs.ifWeaponCollides(we);
                            we.setLifeTime(0);
                        }
                    }
                }
                //--------Obstacle vs Obstacle-----------
                for( int i=0 ; i < obstacles.size() ; i++ ){
                    if(obstacles.get(i) instanceof TNT){ continue ; }
                    for( int j=i+1 ; j < obstacles.size() ; j++){
                        if(obstacles.get(i) instanceof TNT){ continue ; }
                        Obstacle orc1 = obstacles.get(i);
                        Obstacle orc2 = obstacles.get(j);
                        if(orc1.checkCollision(orc2)){
                            //System.out.println(i+" "+j);
                            orc1.ifObstacleCollides(orc2);
                        }
                    }
                }

                //REMOVAL of OBSTACLES
                for(Obstacle obs : obstacles){
                    if(!obs.isAlive()){
                        hero.collectCoins(obs.deathReward());
                    }
                }
                Iterator<Obstacle> itr1 = obstacles.iterator();
                while(itr1.hasNext()){
                    Obstacle obs = itr1.next();
                    if(!obs.isAlive()){  obs.undisplay(game_pane) ; itr1.remove(); }
                }

                score.setText(hero.getCollectedCoins() + " Coins");
                jump_count.setText(String.valueOf(jumps));

                ImageView iv ;

                for(int i=0 ; i<clouds.size(); i++){
                    iv = clouds.get(i);
                    double pos = iv.getLayoutX()-mov[i];
                    if(pos <= 0){ pos = 1400 ;}
                    iv.setLayoutX(pos);
                }

                bg_Island.setLayoutX(Math.max(0,650 - (4*jumps)));
            }
        };
    }

    public void setup_Game(){

        // Hero
        hero = new Hero(300.0,0.0);

        int x = 250;

        String []Large_Island_Images = {"island1.png","island_large1.png","island_large2.png"};
        String []Small_Island_Images = {"island_med.png","island_small.png"};

        for(int i=1;i<=12;i++){

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

            // Rewards

            if(i==1){
                rewards.add(new FloatingCoin(x+70,180));
                rewards.add(new FloatingCoin(x+145,160));
                rewards.add(new FloatingCoin(x+220,180));
                obstacles.add(new TNT(x+135,300));
                rewards.add(new WeaponChest(x+1100,310,new Sword(0,0)));
            }

            if(i==3){
                obstacles.add(new RedOrc(x+100,126));
                obstacles.add(new StdOrc(x+250,250));
                rewards.add(new WeaponChest(x+1500,315,new ThrowingKnife(0,0)));
                obstacles.add(new TNT(x+1050,300));
                obstacles.add(new RedOrc(x+1200,126));
            }

            if(i==5){
                obstacles.add(new RedOrc(x+250,126));
                obstacles.add(new StdOrc(x+250,280));
                rewards.add(new CoinChest(x+100,310));
                rewards.add(new FloatingCoin(x+980,180));
                rewards.add(new FloatingCoin(x+1060,160));
                rewards.add(new FloatingCoin(x+1140,160));
                rewards.add(new FloatingCoin(x+1220,180));
            }

            if(i==7){

                rewards.add(new WeaponChest(x+50,310,new ThrowingKnife(0,0)));
                obstacles.add(new TNT(x+200,300));
                rewards.add(new FloatingCoin(x+1050,160));
                rewards.add(new FloatingCoin(x+1200,160));
                obstacles.add(new RedOrc(x+1050,126));
                obstacles.add(new StdOrc(x+1200,240));
            }

            if(i==9){

                obstacles.add(new StdOrc(x+50,126));
                obstacles.add(new TNT(x+150,300));
                obstacles.add(new RedOrc(x+250,126));
                rewards.add(new WeaponChest(x+1500,315,new Sword(0,0)));
                rewards.add(new CoinChest(x+1100,315));
            }

            if(i==10){

                obstacles.add(new RedOrc(x+30,126));
                obstacles.add(new StdOrc(x+150,250));
                obstacles.add(new RedOrc(x+270,126));
                obstacles.add(new TNT(x+1000,300));
                rewards.add(new CoinChest(x+1200,315));
            }

            if(i==11){

                obstacles.add(new StdOrc(x+30,126));
                obstacles.add(new RedOrc(x+150,250));
                obstacles.add(new StdOrc(x+270,126));
                rewards.add(new FloatingCoin(x+1000,180));
                rewards.add(new FloatingCoin(x+1080,160));
                rewards.add(new FloatingCoin(x+1160,160));
                rewards.add(new FloatingCoin(x+1240,180));
                rewards.add(new WeaponChest(x+1500,315,new Sword(0,0)));
                obstacles.add(new RedOrc(x+1900,250));
                obstacles.add(new StdOrc(x+2100,126));
                obstacles.add(new RedOrc(x+2200,250));

            }
        }

        islands.add(new Island(x,364,358,160,Large_Island_Images[0]));
        islands.add(new Island(x+300,364,358,160,Large_Island_Images[1]));
        islands.add(new Island(x+600,364,358,160,Large_Island_Images[2]));
        islands.add(new Island(x+900,364,358,160,Large_Island_Images[1]));

        islands.add(new Island(x+1750,364,358,160,Large_Island_Images[1]));
        islands.add(new Island(x+2000,364,358,160,Large_Island_Images[0]));

        // BOSS
        obstacles.add(new BossOrc(x+200,100));

        // Crown , chest and dummy island
        rewards.add(new CoinChest(x+2100,310));
        set_Crown();
        islands.add(new Island(x+10000,364,358,160,Large_Island_Images[1]));

    }

    public void set_Crown(){

        Island is = islands.get(islands.size()-2);
        double x = is.getLocation().getX();
        crown_Img.setX(x+70);
        crown_Img.setY(220);
        crown_Img.setFitHeight(110);
        crown_Img.setFitWidth(170);
    }

    public void setWeaponButtons(){

        int[] levels = hero.weaponData();
        if(levels[0] == 0){
            swordGroup.setOpacity(0.33);
        } else {
            swordGroup.setOpacity(0.80);
        }
        swordText.setText(Integer.toString(levels[0]));

        if(levels[1] == 0){
            knifeGroup.setOpacity(0.33);
        } else {
            knifeGroup.setOpacity(0.80);
        }
        knifeText.setText(Integer.toString(levels[1]));
    }
    public void heroDash(MouseEvent e){

        jumps++ ;

        if(hero.getCurrentWeapon()!=null){
            Weapon temp = hero.getCurrentWeapon() ;
            weaponInstances.add(temp) ;
            temp.display(game_pane);
            //if level 2 throwing knife----
            if(temp.getClass() == ThrowingKnife.class){
                if(temp.getLevel() >= 2){
                    temp = hero.getCurrentWeapon();
                    temp.setYSpeed(-5); temp.setXSpeed(37);
                    temp.setRotate(80);
                    weaponInstances.add(temp);
                    temp.display(game_pane);
                }
            }
            dashTime = System.nanoTime() + 110000000;
            return ;
        }

        dashTime = System.nanoTime() + 100000000;
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
        deathCount++;
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
        deathCount++;
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("EndGameMenu.fxml")));
        Parent root = loader.load();
        Endgame ender = loader.getController();
        ender.setGamePlay(this);
        Stage stage = (Stage)(e.getScene().getWindow());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showVictoryScreen(Node e) throws IOException{
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("WinGameMenu.fxml")));
        Parent root = loader.load();
        VictoryScreen victory = loader.getController();
        victory.setParameters(hero.getCollectedCoins());
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
        this.deathCount = gp.deathCount;
        this.jumps = gp.jumps;
        this.reinitialize();
    }

    public void setGame(Game ga) {
        this.game = ga;
    }

    public int getScore(){
        return hero.getCollectedCoins();
    }

    public void reinitialize() throws IOException {

        if(game_pane != null){
            System.out.println("Game_pane is not null");
            for (Island island : islands) island.display(game_pane);
            for(Obstacle obs: obstacles) obs.display(game_pane);
            for(Reward rew: rewards) rew.display(game_pane);
            hero.display(game_pane);
            set_Crown();
            setWeaponButtons();
            game_pane.getChildren().add(crown_Img);
            clouds.add(cloud_1); clouds.add(cloud_2); clouds.add(cloud_3); clouds.add(cloud_4); clouds.add(cloud_5);
            jump_count.setText(String.valueOf(jumps));
            pauseGroup.setDisable(true);
            score.setText(hero.getCollectedCoins()+" Coins");
            if(!pause) animator.start();
            else {
                showPauseMenu(null);
            }
        }
    }
    public void changeWeaponToSword(MouseEvent e){
        this.hero.chooseWeapon("Sword",game_pane);
    }
    public void changeWeaponToThrowingKnife(MouseEvent e){
        this.hero.chooseWeapon("ThrowingKnife",game_pane);
    }
    public boolean canRevive(){
        return (deathCount < 2);
    }
}
