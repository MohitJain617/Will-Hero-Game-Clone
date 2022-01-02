package com.example.application;

import java.io.*;
import java.util.HashMap;

public class User {
    private static User user;
    private String name;
    private Coins coins;
    private int highScore;
    private HashMap<String, GamePlay> savedGames;
    private User(){
        name = "Player";
        savedGames = new HashMap<>();
        try{
            deserializeData();
            deserializeSavedGames();
        } catch(Exception e){
            coins = new Coins(0);
//            System.out.println("Error while deserializing coins");
        }
        if(coins == null) coins = new Coins(0);
    }
    public HashMap<String,GamePlay> getSavedGames(){
        return savedGames;
    }
    public static User getInstance(){
        if(user == null){
            user = new User();
        }
        return user;
    }
    public void deserializeData() throws IOException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("src\\saveCoins.ser"));
            Object[] userData;
            userData = (Object[])in.readObject();
            if(userData != null){
                coins = (Coins)userData[0];
                highScore = (Integer)userData[1];
                System.out.println("User data: "+coins.getValue()+" " + highScore);
            } else {
                coins = new Coins(0);
                highScore = 0;
            }
        } catch (IOException e) {
//            System.out.println("IOException while deserilizing");
            coins = new Coins(0);
            highScore = 0;
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found exception in deserialization.");
        } finally {
            if (in != null) in.close();
        }
    }
    public void serializeData() throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("src\\saveCoins.ser"));
            Object[] userData = new Object[2];
            userData[0] = coins;
            userData[1] = highScore;
            out.writeObject(userData);
        } catch (IOException e) {
            //do something
//            System.out.println("IOException while serializing coins");
        } catch (NullPointerException f){
//            System.out.println("Null pointer exception");
        }
        finally
        {
            if (out != null) out.close();
        }
    }
    public void serializeSavedGames() throws IOException {
        if(savedGames == null) savedGames = new HashMap<>();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("src\\output.ser"));
            out.writeObject(savedGames);
        } catch (IOException e) {
            //do something
//            e.printStackTrace();
        } catch (NullPointerException f){
//            System.out.println("Null pointer exception");
        } finally {
            if (out != null) out.close();
        }
    }
    public void deserializeSavedGames() throws IOException, NoSavedGamePlayException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("src\\output.ser"));
//            System.out.println("Does reach checkpoint 1");
            savedGames = (HashMap)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
            throw new NoSavedGamePlayException();
        } //            System.out.println("Class not found exception des");
        finally {
            if (in != null) in.close();
        }
        if(savedGames == null) savedGames = new HashMap<>();
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

    public int getCoins(){ return coins.getValue();}

    public boolean useCoins(int needed) {
        try {
            coins.useCoins(needed);
            return true;
        } catch (InsufficientCoinException e) {
            return false;
        }
    }
    public String getName(){return name;}
}
