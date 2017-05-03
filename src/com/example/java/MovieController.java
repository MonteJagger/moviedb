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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MovieController implements Initializable{
    // button IDs
    @FXML
    Button homepageBut, movieBut, theaterBut, employeeBut, signUpBut, signInBut, searchBut;
    @FXML
    ComboBox searchChoice;
    ObservableList<String> searchList = FXCollections.observableArrayList("Movies", "Location", "Rating");

    // list IDs
    @FXML
    VBox movieList;

    @FXML
    TextArea movieTitleBox;

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

    // is called every time there is some action
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Loading...");
        searchChoice.setItems(searchList);

        Drive myConn = new Drive(); // check connection
        try {
            Statement myStmt = myConn.Connect().createStatement(); // write query
            allMovies(myStmt);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    // MOVIE PAGE FUNCTIONS
    @FXML
    private void handleMoviePage(ActionEvent event) throws IOException, SQLException {
        Drive myConn = new Drive(); // check connection
        Statement myStmt = myConn.Connect().createStatement(); // write query
        movieBut.setOnAction(e -> allMovies(myStmt));

        if(searchChoice.getValue() == "Movies") {
            //Search for a specific movie
            searchBut.setOnAction(e -> searchMovie(myStmt, movieTitleBox.getText()));
        }

        else if(searchChoice.getValue() == "Location") {
            searchBut.setOnAction(e -> searchLocations(myStmt, movieTitleBox.getText()));
        }

        else if(searchChoice.getValue() == "Rating") {
            searchBut.setOnAction(e -> searchRating(myStmt, movieTitleBox.getText()));
        }

    }

    @FXML
    private void allMovies(Statement myStmt) {
        try {
            ResultSet movieTimes = myStmt.executeQuery("SELECT Title, Rating, Release_Date " +
                    "\tFROM MOVIE");
            // clear list everytime so there is no overflow
            movieList.getChildren().clear();
            while (movieTimes.next()) {
                String title = movieTimes.getString("Title");
                Hyperlink h = new Hyperlink(title); // title link
                h.setOnAction(e -> getMovieTimes(myStmt, title));
                Text t = new Text("\nRated: " + movieTimes.getString("Rating") + "\nReleased: " + movieTimes.getDate("Release_Date"));
                h.setId(title);
                Button deleteBut = new Button("Delete");
                movieList.getChildren().addAll(h, t, deleteBut,
                        new Text("\n-----------------------------------------------------------------------------------------------------"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void searchMovie(Statement myStmt, String movie) {
        try {
            ResultSet chosenMovie = myStmt.executeQuery("SELECT Title, Rating, Release_Date\n" +
                    "FROM MOVIE\n" +
                    "WHERE Title = '" + movie + "'");

            movieList.getChildren().clear();
            while (chosenMovie.next()) {
                String title = chosenMovie.getString("Title");
                Hyperlink h = new Hyperlink(title); // title link
                h.setOnAction(e -> getMovieTimes(myStmt, title));
                Text t = new Text("\nRated: " + chosenMovie.getString("Rating") + "\nReleased: " + chosenMovie.getDate("Release_Date"));
                h.setId(title);
                Button deleteBut = new Button("Delete");
                movieList.getChildren().addAll(h, t, deleteBut,
                        new Text("\n-----------------------------------------------------------------------------------------------------"));
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getMovieTimes(Statement myStmt, String movieTitle){
            movieList.getChildren().clear(); // clear the list the is already there
            try {
                // create the ResultSet
                ResultSet showTimes = myStmt.executeQuery("SELECT Theater_Name, " + "Location, Title, Movie_Time FROM THEATRE NATURAL JOIN (SELECT * FROM SHOWS NATURAL JOIN MOVIE WHERE Title = '"+ movieTitle + "') AS G ORDER BY Movie_Time");

                movieList.getChildren().add(new Text(movieTitle + "\n\n")); // print the title of the movie

                while (showTimes.next()) {
                    movieList.getChildren().add(new Text("Time: " + showTimes.getTime("Movie_Time") +"\n" + "Theater: " + showTimes.getString("Theater_Name") + "\n" + "Showing Time: " + showTimes.getString("Location") + "\n\n"));
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

    @FXML
    private void searchLocations(Statement myStmt, String location) {
        //Current movies and show times in #location

        try{
            ResultSet rs = myStmt.executeQuery("SELECT Theater_Name, Title, Movie_Time\n" +
                    "FROM MOVIE NATURAL JOIN (\n" +
                    "\tSELECT * \n" +
                    "    FROM Theatre NATURAL JOIN Shows\n" +
                    "\tWHERE Location = '" + location + "'\n" +
                    ") AS t\n" +
                    "ORDER BY Theater_Name, Title, Movie_Time");

            movieList.getChildren().clear();
            Text t = new Text("CURRENT MOVIES AND SHOW TIMES: " + location);
            t.setId("fuck");
            movieList.getChildren().add(t);
            while (rs.next()) {
                String theaterName = rs.getString("Theater_Name");
                String title = rs.getString("Title");
                Time time = rs.getTime("Movie_Time");

                movieList.getChildren().addAll(new Text("Theater: " + theaterName +"\n" +
                                                "Title: " + title + "\n" +
                                                "Time: " + time + "\n"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void searchRating(Statement myStmt, String rating) {
        try{
            ResultSet rs = myStmt.executeQuery("Select Title\n" +
                    "FROM MOVIE\n" +
                    "WHERE Rating = '" + rating + "'");

            movieList.getChildren().clear();
            Text t = new Text("ALL MOVIES RATED: " + rating);
            t.setId("fuck");
            movieList.getChildren().add(t);
            while (rs.next()) {
                String title = rs.getString("Title");
                Hyperlink h = new Hyperlink(title); // title link
                h.setOnAction(e -> getMovieTimes(myStmt, title));
                movieList.getChildren().add(h);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}