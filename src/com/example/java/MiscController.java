package com.example.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Created by Hiumathy on 5/3/17.
 */
public class MiscController implements Initializable{
    // button IDs
    @FXML
    Button homepageBut, movieBut, theaterBut, employeeBut, signUpBut, signInBut, searchBut;
    @FXML
    ComboBox theaterChoice, jobTypeChoice;

    @FXML
    VBox miscList;

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
        } else if (event.getSource() == employeeBut) {// if misc. butt is clicked go to
            // miscPage.fxml
            stage = (Stage) employeeBut.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("miscPage.fxml"));
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

    // is called every time there is some action
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Loading...");

        Drive myConn = new Drive(); // get connection
        try {
            Statement myStmt = myConn.Connect().createStatement();
            allMisc(myStmt);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MOVIE PAGE FUNCTIONS
    @FXML
    private void handleMiscPage(ActionEvent event) throws IOException, SQLException {
        Drive myConn = new Drive(); // check connection
        Statement myStmt = myConn.Connect().createStatement(); // write query
        employeeBut.setOnAction(e -> allMisc(myStmt));

    }



    @FXML
    private void allMisc(Statement myStmt) {

    }

}

