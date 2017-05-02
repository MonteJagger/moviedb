package com.example.java;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    // Add MySQL database driver to classpath
    // Get connection
    // Submit SQL query
    // Process result set
    @FXML
    Button homepageBut, movieBut, newlyReleasedBut, theaterBut, signUpBut, signInBut, searchBut;
    @FXML
    VBox movieList, theaterList;
    ArrayList<MoviesInfo> moviesdb = new ArrayList<MoviesInfo>();

    // switching fxml pages
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException, SQLException {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == homepageBut) {
            //get reference to the button's stage
            stage = (Stage) homepageBut.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
        } else if (event.getSource() == movieBut) {
            stage = (Stage) movieBut.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("movieList.fxml"));
        } else if (event.getSource() == theaterBut) {
            stage = (Stage) theaterBut.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("theaterList.fxml"));
        } else if (event.getSource() == signInBut) {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Loading...");

    }

    private void onMouseClickedProperty(ActionEvent e) {

        //find all the titles of a movie
        for (MoviesInfo m : moviesdb) {
            if (e.getSource() == m.getTitle()) {
                System.out.println("true");
            }
        }

    }

    @FXML
    private void loadDataFromDatabase(ActionEvent event) throws SQLException {
        Drive myConn = new Drive(); // check connection
        Statement myStmt = myConn.Connect().createStatement(); // write query




        // movieBut button
        if (event.getSource() == movieBut) {
            ResultSet movieTimes = myStmt.executeQuery("SELECT Title, Rating, Release_Date " +
                    "\tFROM MOVIE");

            movieList.getChildren().clear();
            while (movieTimes.next()) {
                MoviesInfo m = new MoviesInfo(movieTimes.getString("Title"), movieTimes.getString("Rating"), movieTimes.getDate("Release_Date"));
                moviesdb.add(m);

                Hyperlink h = new Hyperlink(m.getTitle()); // title link
                Text t = new Text("\nRated: " + m.getRating() + "\nReleased: " + m.getDate());
                h.setId(m.getTitle());
                h.setOnAction(e -> onMouseClickedProperty(e));
                movieList.getChildren().addAll(h, t,
                        new Text
                                ("\n-----------------------------------------------------------------------------------------------------"));
            }

        }
        else if (event.getSource() == theaterBut) {
            ResultSet theaters = myStmt.executeQuery("SELECT Theater_Name, Location FROM THEATRE");

            theaterList.getChildren().clear();
            while (theaters.next()) {

                theaterList.getChildren().add(new Text(theaters.getString("Theater_Name") + "\n"
                        + "Located: " + theaters.getString("Location") + "\n" +
                        "\n\n-----------------------------------------------------------------------------------------------------"));
            }

        }
    }
}