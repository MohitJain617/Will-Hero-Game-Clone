package com.example.application;

import java.io.*;

public class User {
    private static User user;
    private String name;
    private Coins coins;
    private int highScore;
    private User(){
        name = "Player";
        try{
            deserializeCoins();
        } catch(Exception e){
            coins = new Coins(0);
            System.out.println("Error while deserializing coins");
        }
        highScore = 0;
    }
    public static User getInstance(){
        if(user == null){
            user = new User();
        }
        return user;
    }
    public void deserializeCoins() throws IOException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("src\\saveCoins.ser"));
            coins = (Coins)in.readObject();
        } catch (IOException e) {
            System.out.println("IOException while deserilizing");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found exception in deserialization.");
        } finally {
            if (in != null) in.close();
        }
    }
    public void serializeCoins() throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("src\\saveCoins.ser"));
            out.writeObject(coins);
        } catch (IOException e) {
            //do something
            System.out.println("IOException while serializing coins");
        } catch (NullPointerException f){
            System.out.println("Null pointer exception");
        }
        finally
        {
            if (out != null) out.close();
        }
    }
    public void collectCoins(Coins c){
        if(c == null) return;
        coins.addCoins(c);
    }

    public int getHighScore(){
        return this.highScore;
    }
    public boolean setHighScore(int current){
        if(highScore < current){
            highScore = current;
            return true;
        }
        return false;
    }
    public int getCoins(){return coins.getValue();}
    public boolean useCoins(int needed){
        return coins.useCoins(needed);
    }
    public String getName(){return name;}
}
