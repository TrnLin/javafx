package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Header {
    public Button authBtn;
    boolean isLoggedIn = false;

    public void initialize() {
        authBtn.setOnAction(e -> {
            System.out.println("Auth button clicked");
        });


    }

    public void checkAuth(ActionEvent event) {

        // logic to check if user is logged in
        if (isLoggedIn) {
            loadInfoScreen();
        } else {
            redirectToLoginScreen();
        }
    }

    private void loadInfoScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/InfoScreen.fxml"));
            Stage stage = (Stage) authBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Information Screen");
            stage.show();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void redirectToLoginScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/Auth/login.fxml"));
            Stage stage = (Stage) authBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            // Handle general exceptions
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
