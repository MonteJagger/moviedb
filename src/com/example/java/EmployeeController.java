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
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Created by Hiumathy on 5/3/17.
 */
public class EmployeeController implements Initializable{
    // button IDs
    @FXML
    Button homepageBut, movieBut, theaterBut, employeeBut, signUpBut, signInBut, searchBut;
    @FXML
    ComboBox theaterChoice, jobTypeChoice;

    ObservableList<String> theaterDrop = FXCollections.observableArrayList("Select Theater", "AMC",
            "Cinemark", "Tinseltown", "Studio Movie Grill", "Alamo", "Look", "Rave");
    ObservableList<String> jobTypeDrop = FXCollections.observableArrayList("Select Job Type","Ticket " +
            "Seller", "Usher", "Manager", "Concession");

    // list IDs
    @FXML
    VBox movieList, theaterList, employeeList;

    @FXML
    TextArea employeeTitleBox;

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
        theaterChoice.setItems(theaterDrop);
        jobTypeChoice.setItems(jobTypeDrop);


        Drive myConn = new Drive(); // get connection
        try {
            Statement myStmt = myConn.Connect().createStatement();
            allEmployees(myStmt);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MOVIE PAGE FUNCTIONS
    @FXML
    private void handleEmployeePage(ActionEvent event) throws IOException, SQLException {
        Drive myConn = new Drive(); // check connection
        Statement myStmt = myConn.Connect().createStatement(); // write query
        employeeBut.setOnAction(e -> allEmployees(myStmt));

        String userSearch = employeeTitleBox.getText();
        String userTheaterChoice = (String) theaterChoice.getValue();
        String userJobChoice = (String) jobTypeChoice.getValue();
        searchBut.setOnAction(e -> searchEmployees(myStmt, userTheaterChoice, userJobChoice, userSearch));
    }

    @FXML
    private void allEmployees(Statement myStmt) {
        try {
            ResultSet rs = myStmt.executeQuery("SELECT * " + "FROM EMPLOYEE");
            employeeList.getChildren().clear();
            while (rs.next()) {
                Text t = new Text("SSN: " + rs.getString("SSN") +"\nName: "+
                        rs.getString("Name") + "\nJob type: " + rs.getString("Job_Type")+ "\n");
                employeeList.getChildren().add(t);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void searchMovie(Statement myStmt, String employee) {
//        try {
//            ResultSet chosenTheater = myStmt.executeQuery("SELECT Title, Rating, Release_Date\n" +
//                    "FROM MOVIE\n" +
//                    "WHERE Title = '" + movie + "'");
//
//            theaterList.getChildren().clear();
//            while (chosenTheater.next()) {
//                String title = chosenTheater.getString("Theater_Name");
//                Hyperlink h = new Hyperlink(title); // title link
//                h.setOnAction(e -> getTheaterShowings(myStmt, title));
//                Text t = new Text("\nRated: " + chosenTheater.getString("Rating") + "\nReleased: " +
//                        chosenTheater.getDate("Release_Date"));
//                h.setId(title);
//                Button deleteBut = new Button("Delete");
//                movieList.getChildren().addAll(h, t, deleteBut,
//                        new Text("\n-----------------------------------------------------------------------------------------------------"));
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void getTheaterShowings(Statement myStmt, String movieTitle){
//        movieList.getChildren().clear(); // clear the list the is already there
//        try {
//            // create the ResultSet
//            ResultSet showTimes = myStmt.executeQuery("SELECT Theater_Name, " + "Location, Title, Movie_Time FROM THEATRE NATURAL JOIN (SELECT * FROM SHOWS NATURAL JOIN MOVIE WHERE Title = '"+ movieTitle + "') AS G ORDER BY Movie_Time");
//
//            movieList.getChildren().add(new Text(movieTitle + "\n\n")); // print the title of the movie
//
//            while (showTimes.next()) {
//                movieList.getChildren().add(new Text("Time: " + showTimes.getTime("Movie_Time") +"\n" + "Theater: " + showTimes.getString("Theater_Name") + "\n" + "Showing Time: " + showTimes.getString("Location") + "\n\n"));
//            }
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        }
    }

    // Number of tickets sold by #theater at #location??
    @FXML
    private void searchEmployees(Statement myStmt, String tName, String job_type, String
            userSearch) {

        if ((tName != null || tName.equals("Select Theater")) && (job_type != null || job_type.equals("Select Job Type")) && userSearch != null) {
            try {
                System.out.println(tName + " " + job_type);
                ResultSet rs = myStmt.executeQuery("SELECT * FROM EMPLOYEE NATURAL JOIN THEATRE\n" +
                        "WHERE Theater_Name = '" + tName + "' AND Job_Type = '" + job_type + "'");

                employeeList.getChildren().clear();
                while (rs.next()) {
                    Text t = new Text("SSN: " + rs.getString("SSN") + "\nName: " + rs.getString("Name")
                            + "\nJob type: " + job_type + "\nTheater name: " + tName + "\n");
                    employeeList.getChildren().add(t);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (userSearch != null) {
//            ResultSet rs = myStmt.executeQuery("SELECT * FROM EMPLOYEE NATURAL JOIN THEATRE\n" +
//                    "WHERE Theater_Name = '" + tName + "' AND Job_Type = '" + job_type + "'");
            System.out.println("fuck");
        }
        else System.out.println("fuck you");
    }
}

