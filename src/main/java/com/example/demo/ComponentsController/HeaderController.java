package com.example.demo.ComponentsController;

import com.example.demo.Observer.AuthObserver;
import com.example.demo.Observer.AuthStatus;
import com.example.demo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HeaderController implements AuthObserver {
    public Button authBtn;
    boolean isLoggedIn = false;

    @FXML
    public void initialize() {
        AuthStatus.getInstance().addObserver(this);
        authBtn.setOnAction(e -> checkAuth(e));

        if (AuthStatus.getInstance().isLoggedIn()) {
            authBtn.setText("View Info");
        } else {
            authBtn.setText("Login");
        }
    }

    @Override
    public void onLoginStatusChanged(boolean isLoggedIn, User currentUser) {
        System.out.println("Login status changed: " + isLoggedIn);
        if (!isLoggedIn) {
            redirectToLoginScreen();
        }
    }

    public void checkAuth(ActionEvent event) {
        if (AuthStatus.getInstance().isLoggedIn()) {
            System.out.println("Logged in");
            loadInfoScreen();
        } else {
            System.out.println("Not logged in");
            redirectToLoginScreen();

        }
    }

    private void loadInfoScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/components/viewAccountInfo.fxml"));
            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            newStage.setScene(scene);
            newStage.setTitle("Information Screen");
            newStage.show();
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
