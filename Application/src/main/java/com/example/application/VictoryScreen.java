package com.example.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class VictoryScreen{

    private int score;
    //User user;

    @FXML
    Text text ;

    @FXML
    Text curr_score ;

    public VictoryScreen() {
        score = 0 ;
    }

    public void showMainMenu(ActionEvent e) throws IOException {
        new SceneController().changeScene(e,"MainMenu.fxml");
    }

    public void setParameters(int collectedCoins) {
        this.score = collectedCoins;
        reinitialize();
    }

    public void reinitialize() {

        if(curr_score != null && text!=null){
            curr_score.setText(String.valueOf(score));

            if(score < 5){
                text.setText("SCORE");
            }

            else{
                text.setText("NEW HIGHSCORE !");
            }
        }
    }
}
