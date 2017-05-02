package com.example.java;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
    @FXML
    VBox movieList;
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
        primaryStage.setTitle("Movie Maverick");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();

    }

    public static void main(String[] args) {

        launch(args);

    }
}
