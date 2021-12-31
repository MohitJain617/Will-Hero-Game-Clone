package com.example.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class VictoryScreen implements Initializable {

    int score;
    //User user;

    @FXML
    private Text text ;

    @FXML
    private Text curr_score ;

    VictoryScreen() {
        score = 0 ;
    }

    public void showMainMenu(ActionEvent e) throws IOException {
        new SceneController().changeScene(e,"MainMenu.fxml");
    }

    public void setParameters(int collectedCoins) {
        this.score = collectedCoins;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(curr_score != null){
            curr_score.setText("34");
        }
    }
}
