package com.example.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HomeController {

    @FXML
    Button homepageBut, movieBut, theaterBut, employeeBut, signUpBut, signInBut, searchBut;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException, SQLException {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == homepageBut) { // if homepage button is clicked go to homepage.fxml
            //get reference to the button's stage
            stage = (Stage) homepageBut.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
        } else if (event.getSource() == movieBut){// if movies butt is clicked go to movieInfo.fxml
            stage = (Stage) movieBut.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("movieInfo.fxml"));
        } else if (event.getSource() == theaterBut) {// if theater butt is clicked go to theater
            // .fxml
            stage = (Stage) theaterBut.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("theaterList.fxml"));
        } else if (event.getSource() == employeeBut) {// if employees butt is clicked go to
            // employees.fxml
            stage = (Stage) employeeBut.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("employee.fxml"));
        } else if (event.getSource() == signInBut) { // if signIn butt is clicked go to movieInfo
            // signIn.fxml
            stage = (Stage) signInBut.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("signIn.fxml"));
        } else if (event.getSource() == signUpBut) {
            stage = (Stage) signUpBut.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
        }
//        create a new scene with root and set the stage
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }
}


