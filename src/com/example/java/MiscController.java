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
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    // page buttons
    @FXML
    Button homepageBut, movieBut, theaterBut, employeeBut, miscBut, signUpBut, signInBut;

    // miscellaneous search buttons
    @FXML
    Button searchPastTickets,
            searchNumTickets,
            searchNumEmployees,
            searchNumSeats,
            searchRevenue;

    // miscellaneous text fields
    @FXML
    TextField pastTixText, // past ticket purchases
            tixSoldTheaterText, tixSoldLocationText, // number of tickets sold
            numEmpInt, numEmpTheaterText, numEmpLocateText, // number of employees
            numSeatsText; // number of seats left for ..

    @FXML
    ComboBox totalRevChoice;
    ObservableList<String> mostLeastList = FXCollections.observableArrayList("Most", "Least");

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
        } else if (event.getSource() == miscBut) {// if misc. butt is clicked go to
            // miscPage.fxml
            stage = (Stage) miscBut.getScene().getWindow();
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
        totalRevChoice.setItems(mostLeastList);

        Drive myConn = new Drive(); // get connection
        try {
            Statement myStmt = myConn.Connect().createStatement();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MOVIE PAGE FUNCTIONS
    @FXML
    private void handleMiscPage(ActionEvent event) throws IOException, SQLException {
        Drive myConn = new Drive(); // check connection
        Statement myStmt = myConn.Connect().createStatement();

        searchPastTickets.setOnAction(e -> pastTicketPurch(myStmt, pastTixText.getText()));
        searchNumTickets.setOnAction(e -> numTixSold(myStmt, tixSoldTheaterText.getText(), tixSoldLocationText.getText()));
        searchNumEmployees.setOnAction(e -> numOfEmployees(myStmt, numEmpInt.getText(), numEmpTheaterText.getText(), numEmpLocateText.getText()));
        searchNumSeats.setOnAction(e -> numOfSeats(myStmt, numSeatsText.getText()));

        searchRevenue.setOnAction(e -> totalRev(myStmt, (String) totalRevChoice.getValue()));
    }

    // past ticket purchases for 'user input name'
    public void pastTicketPurch(Statement myStmt, String pastTix) {
        miscList.getChildren().clear();
        Text title = new Text("Past ticket purchases for '" + pastTix + "'\n");
        title.setId("fuck");

        miscList.getChildren().add(title);
    }

    // Number of tickets sold by 'user select theater' at 'user select location'
    public void numTixSold(Statement myStmt, String tixSoldTheater, String tixSoldLocation) {
        miscList.getChildren().clear();
        Text title = new Text("Number of tickets sold by '" + tixSoldTheater + "' at '" + tixSoldLocation +"'\n");
        title.setId("fuck");

        miscList.getChildren().add(title);
    }

    // Theaters at location with more that x employees
    public void numOfEmployees(Statement myStmt, String numEmp, String numEmpTheater, String numEmpLocation) {



        miscList.getChildren().clear();

        Text title = new Text("'" + numEmpTheater + "' at '" + numEmpLocation + "' " + "with more" +
                " " + "than "+numEmp+" employees\n");
        title.setId("fuck");

        miscList.getChildren().add(title);
    }

    // number of seats left for 'user select movie
    public void numOfSeats(Statement myStmt, String numSeats) {
        miscList.getChildren().clear();
        Text title = new Text("Number of seats left for '" + numSeats + "'");
        title.setId("fuck");

        miscList.getChildren().add(title);
    }

    // total revenue in ticket purchases for each movie greater than $10.00 in order from
    // greatest to least
    public void totalRev(Statement stmnt, String mostLeast) {
        miscList.getChildren().clear();
        Text title = new Text("total revenue in ticket purchases for each movie greater than $10" + ".00 starting from '" + mostLeast + "'");
        title.setId("fuck");

        miscList.getChildren().add(title);
    }


    @FXML
    private void allMisc(Statement myStmt) {

    }

}

