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

        System.out.println(userSearch + "\t" + userTheaterChoice + "\t" + userJobChoice + ".space");

        if (userSearch != null && !userSearch.isEmpty())
        {
            System.out.println("execute");
            searchBut.setOnAction(e -> searchEmployeeName(myStmt, employeeTitleBox.getText()));
        }
        else if ( !(userTheaterChoice==null || userTheaterChoice.equals("Select Theater")) && !
                (userJobChoice==null || userJobChoice.equals("Select Job Type")) && userSearch.equals("")) {
            searchBut.setOnAction(e -> searchEmployeesInfo(myStmt, userTheaterChoice, userJobChoice,
                    userSearch));
        }
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

    private void searchEmployeeName(Statement myStmt, String name) {
        System.out.println("works");
            try {
                ResultSet rs = myStmt.executeQuery("SELECT * FROM EMPLOYEE NATURAL JOIN THEATRE " +
                        "WHERE Name = '" + name + "'");
                employeeList.getChildren().clear();
                Text title = new Text("Employees that named '" + name + "'\n");
                title.setId("fuck");
                employeeList.getChildren().add(title);
                while (rs.next()) {
                    Text t = new Text("SSN: " + rs.getString("SSN") + "\nName: " + rs.getString("Name")
                            + "\nJob type: " + rs.getString("Job_Type") + "\nTheater name: " + rs.getString("Theater_Name")+
                            "\n");
                    employeeList.getChildren().add(t);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    // Number of tickets sold by #theater at #location??
    private void searchEmployeesInfo(Statement myStmt, String tName, String job_type, String userSearch){
        System.out.println(".space");
        // the user selects both drop downs and the leaves the text field blank

            System.out.println(tName + " " + job_type);
            ResultSet rs = null;
            try {
                rs = myStmt.executeQuery("SELECT * FROM EMPLOYEE NATURAL JOIN THEATRE\n" +
                        "WHERE Theater_Name = '" + tName + "' AND Job_Type = '" + job_type + "'");
                employeeList.getChildren().clear();
                Text title = new Text("Employees that work at '"+ tName + "' "+ "and has job type" + " " + "'" + job_type + "'\n");
                title.setId("fuck");
                employeeList.getChildren().add(title);
                while (rs.next()) {
                    Text t = new Text("SSN: " + rs.getString("SSN") + "\nName: " + rs.getString("Name")
                            + "\nJob type: " + job_type + "\nTheater name: " + tName + "\n");
                    employeeList.getChildren().add(t);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}

