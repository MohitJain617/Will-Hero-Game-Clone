package com.example.application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.util.Objects;

public class Hero extends GameObject{
    @Serial
    private static final long serialVersionUID = 9L;
    private transient ImageView iv;
    private transient AnchorPane game_pane;

    private double ySpeed;
    private double xSpeed;
    private boolean alive;
    private Weapon currentWeapon;
    private Coins collectedCoins;
    private Helmet helmet ;
    private double rotate ;

    public Hero(double x, double y){
        super(x,y);
        ySpeed = 0;
        xSpeed = 0;
        render();
        alive = true;
        currentWeapon =  null ;
        collectedCoins = new Coins(0) ;
        helmet = new Helmet() ;
    }

    private void render(){
        iv = new ImageView();
        game_pane = null;
        Image img = new Image("hero.png");
        iv.setImage(img);
        iv.setPreserveRatio(true);
        iv.setFitHeight(90);
        iv.setFitWidth(90);
        iv.setX(this.getLocation().getX()); iv.setY(this.getLocation().getY());
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }
    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        render();
    }

    public void damage(){
        this.rotate = 10 ;
        iv.setDisable(true);
        setYspeed(10);
        alive = false;
    }

    public void makeAlive(){
        this.setLocation(300,0);
        this.updateLocation();
        this.setYspeed(0);
        this.setAlive(true);
        iv.setDisable(false);
        this.rotate = 0;
        iv.setRotate(0);
    }

    public boolean isAlive(){

        if(this.getLocation().getY() > 550) alive = false;

        if(this.getLocation().getY() > 400){  damage(); }

        return alive;
    }

    public boolean isDying(){
        return (this.rotate != 0) ;
    }

    public void setAlive(boolean b){
        this.alive = b ;
    }

    @Override
    public Bounds getBounds(){
        return this.iv.getBoundsInParent();
    }

    @Override
    public void gravityEffect() {
        Location curr = this.getLocation();
        this.setLocation(curr.getX()+xSpeed,curr.getY()+ySpeed);
        this.updateLocation();
    }

    @Override
    public void updateLocation() {
        Location curr = this.getLocation();
        iv.setX(curr.getX());
        iv.setY(curr.getY());
        iv.setRotate(iv.getRotate()+rotate);
        if(currentWeapon != null){
            if(currentWeapon instanceof Sword) currentWeapon.setLocation(curr.getX()-40,curr.getY()+30);
            else currentWeapon.setLocation(curr.getX(),curr.getY()+50);
            currentWeapon.updateLocation();
        }
    }

    @Override
    public void ifHeroCollides(Hero hero) {
        //do nothing since hero can't collide with itself
    }

    public void setYspeed(double speed){
        ySpeed = speed;
    }
    public double getYspeed(){
        return ySpeed;
    }
    public void setXspeed(double speed){
        xSpeed = speed;
    }
    public double getXspeed(){
        return xSpeed;
    }

    @Override
    public void display(AnchorPane anc) {
        //add iv to the AnchorPane
        this.game_pane = anc;
        anc.getChildren().add(iv);
        if(currentWeapon != null){
            displayWeapon(anc);
        }
    }
    public void displayWeapon(AnchorPane anc){
        if(currentWeapon != null && !anc.getChildren().contains(currentWeapon.getImageView())){
            anc.getChildren().add(currentWeapon.getImageView());
        }
    }

    public int getCollectedCoins(){ return this.collectedCoins.getValue() ; }

    public void collectCoins(Coins c){
        if(c == null) return;
        this.collectedCoins.addCoins(c);
        User.getInstance().collectCoins(c);
    }
    public Weapon getCurrentWeapon(){

        if(this.currentWeapon == null ){ return null ; }

        return this.currentWeapon.copy() ;
    }

    public void addWeapon(Weapon weapon){

        int verdict = this.helmet.addWeapon(weapon);

        if(verdict == 0){
            chooseWeapon(weapon.getClass().getSimpleName(),this.game_pane);
        }
    }
    public void chooseWeapon(String name, AnchorPane pane){
        //null check
        if(this.game_pane == null) {
//            System.out.println("The pane inside Hero is null");
            return;
        }
        // get weapon from helmet
        Weapon w = null;
        if(name.equals("Sword") || name.equals("ThrowingKnife")){
            w = this.helmet.getWeapon(name);
        }
        //undisplay previous weapon and get new weapon
        if(w != null){
            if(this.currentWeapon != null){
                currentWeapon.undisplay(pane);
            }
            currentWeapon = w;
            currentWeapon.display(pane);
        }
    }
    public int[] weaponData(){
        return this.helmet.weaponData();
    }
}
