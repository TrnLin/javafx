package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
//            "/com/example/demo/Auth/login.fxml"
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/auth/login.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

