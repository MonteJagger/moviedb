package com.example.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Hiumathy on 5/2/17.
 */
public class Movie implements Initializable {
    Stage stage;
    Parent root;

    @FXML
    Button movieBut;

    @FXML
    VBox movieList;
    ArrayList<MoviesInfo> moviesdb = new ArrayList<MoviesInfo>();


    public Movie(Stage stage, Parent root) {
        this.stage = stage;
        this.root = root;

        stage.setScene(new Scene(this.root, 900, 600));
        stage.show();
    }

    @FXML
    private void handleButtonAction (ActionEvent event) throws IOException, SQLException {

    }

    @FXML
    private void loadDataFromDatabase(ActionEvent e) throws SQLException {
        Drive myConn = new Drive(); // check connection
        Statement myStmt = myConn.Connect().createStatement(); // write query

        // while on movieInfo.fxml if movieBut button is clicked populate movie database
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("loading movies pages...");
        movieBut.setOnAction(e -> {
            try {
                loadDataFromDatabase(e);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }
}
