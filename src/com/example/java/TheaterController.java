package com.example.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ResourceBundle;

/**
 * Created by Arianne on 5/3/17.
 */
public class TheaterController implements Initializable {
    @FXML
    VBox theaterList;

    @FXML
    TextArea theaterBox;


    @FXML
    ComboBox theaterChoice;
    ObservableList<String> searchList = FXCollections.observableArrayList("Theaters", "Location");

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        theaterChoice.setItems(searchList);
        Drive myConn = new Drive(); // check connection
        try {
            Statement myStmt = myConn.Connect().createStatement(); // write query
            allTheaters(myStmt);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void handleTheaterPage(ActionEvent event) throws IOException, SQLException {
        Drive myConn = new Drive(); // check connection
        Statement myStmt = myConn.Connect().createStatement(); // write query
        theaterBut.setOnAction(e -> allTheaters(myStmt));

        if(theaterChoice.getValue() == "Theaters") {
            //Search for a specific movie
            searchBut.setOnAction(e -> searchTheater(myStmt, theaterBox.getText()));
        }
        else if (theaterChoice.getValue() == "Location") {
            searchBut.setOnAction(e -> searchLocations(myStmt, theaterBox.getText()));
        }

    }

    @FXML
    private void allTheaters(Statement myStmt) {
        try {
            ResultSet rs = myStmt.executeQuery("SELECT Theater_Name, Theatre_ID, Location\n" +
                    "FROM THEATRE;");
            // clear list everytime so there is no overflow
            theaterList.getChildren().clear();
            while(rs.next()){
                String theaterName = rs.getString("Theater_Name");
                String t_id = rs.getString("Theatre_ID");
                Hyperlink h = new Hyperlink(theaterName);
                h.setOnAction(e -> getMovieTimes(myStmt, t_id));
                String location = rs.getString("Location");

                theaterList.getChildren().addAll(h, new Text("Location: " + location + "\n"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getMovieTimes(Statement myStmt, String t_id){
        theaterList.getChildren().clear();
        try {
            ResultSet rs = myStmt.executeQuery("Select Title, Rating, Theatre_ID, Theater_Name, Location, Movie_Time\n" +
                    "FROM MOVIE NATURAL JOIN THEATRE NATURAL JOIN SHOWS\n" +
                    "WHERE Theatre_ID = '" + t_id + "'");
            while(rs.next()){
                String title = rs.getString("Title");
                String rating = rs.getString("Rating");
                Time time = rs.getTime("Movie_Time");
                theaterList.getChildren().add(new Text(title +
                                                "\t" + rating + "\n" +
                                                "Time: " + time + "\n") );

            }
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    private void searchTheater(Statement myStmt, String theater) {
        try {
            ResultSet rs = myStmt.executeQuery("SELECT Theater_Name, Theatre_ID, Location\n" +
                    "FROM THEATRE\n" +
                    "WHERE Theater_Name = '" + theater + "'");

            theaterList.getChildren().clear();
            while(rs.next()){
                String theaterName = rs.getString("Theater_Name");
                String t_id = rs.getString("Theatre_ID");
                Hyperlink h = new Hyperlink(theaterName);
                h.setOnAction(e -> getMovieTimes(myStmt, t_id));
                String location = rs.getString("Location");

                theaterList.getChildren().addAll(h, new Text("Location: " + location + "\n"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void searchLocations(Statement myStmt, String location) {
        //Current movies and show times in #location
        try{
            ResultSet rs = myStmt.executeQuery("SELECT Theater_Name, Theatre_ID, Location\n" +
                    "FROM THEATRE\n" +
                    "WHERE Location = '" + location + "'");

            theaterList.getChildren().clear();

            while(rs.next()){
                String theaterName = rs.getString("Theater_Name");
                String t_id = rs.getString("Theatre_ID");
                Hyperlink h = new Hyperlink(theaterName);
                h.setOnAction(e -> getMovieTimes(myStmt, t_id));
                theaterList.getChildren().addAll(h);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


}
