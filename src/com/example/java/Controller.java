package com.example.java;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    // button IDs
    @FXML
    Button homepageBut, movieBut, theaterBut, employeeBut, signUpBut, signInBut, searchBut;

    // list IDs
    @FXML
    VBox movieList, theaterList, employeeList;

    ArrayList<MoviesInfo> moviesdb = new ArrayList<MoviesInfo>();

    // switching fxml pages
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

    // is called everytime there is some action
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Loading...");
    }

    private void onMouseClickedProperty(ActionEvent e) {
        //find all the titles of a movie
    }

    @FXML
    private void loadDataFromDatabase(ActionEvent event) throws SQLException {
        Drive myConn = new Drive(); // check connection
        Statement myStmt = myConn.Connect().createStatement(); // write query

        // while on movieInfo.fxml if movieBut button is clicked populate movie database
        if (event.getSource() == movieBut) {
            // call the query
            ResultSet movieTimes = myStmt.executeQuery("SELECT Title, Rating, Release_Date " +
                    "\tFROM MOVIE");

            // clear list everytime so there is no overflow
            movieList.getChildren().clear();
            while (movieTimes.next()) {
                MoviesInfo m = new MoviesInfo(movieTimes.getString("Title"), movieTimes.getString("Rating"), movieTimes.getDate("Release_Date"));
                moviesdb.add(m);

                Hyperlink h = new Hyperlink(m.getTitle()); // title link
                Text t = new Text("\nRated: " + m.getRating() + "\nReleased: " + m.getDate());
                h.setId(m.getTitle());
                Button deleteBut = new Button("Delete");
                movieList.getChildren().addAll(h, t, deleteBut,
                        new Text("\n-----------------------------------------------------------------------------------------------------"));
            }
        }
        else if (event.getSource() == theaterBut) {// while on theaterList.fxml if movieBut button
            // is clicked populate movie database
            ResultSet theaters = myStmt.executeQuery("SELECT Theater_Name, Location FROM THEATRE");

            theaterList.getChildren().clear();
            while (theaters.next()) {
                theaterList.getChildren().add(new Text(theaters.getString("Theater_Name") + "\n"
                        + "Located: " + theaters.getString("Location") + "\n" +
                        "\n\n-----------------------------------------------------------------------------------------------------"));
            }
        }
        else if (event.getSource() == employeeBut) {
            ResultSet employees = myStmt.executeQuery("SELECT Name, Job_Type FROM EMPLOYEE");

            employeeList.getChildren().clear();
            while (employees.next()) {
                employeeList.getChildren().add(new Text(employees.getString("Name") + "\nJob type: " + employees.getString("Job_Type") +
                        "\n\n-----------------------------------------------------------------------------------------------------"));
            }
        }

    }
}