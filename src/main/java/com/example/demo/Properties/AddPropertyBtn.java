package com.example.demo.Properties;

import com.example.demo.Auth.Observer.AuthObserver;
import com.example.demo.Auth.Observer.AuthStatus;
import com.example.demo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AddPropertyBtn implements AuthObserver {

    @FXML
    public Button addPropertyBtn;

    @FXML
    public void initialize() {
        // Register as an observer
        AuthStatus.getInstance().addObserver(this);

        // Disable the button initially
        addPropertyBtn.setDisable(true);

        // Enable the button if already logged in with the correct role
        if (AuthStatus.getInstance().isLoggedIn()) {
            updateAccess();
        }
    }

    @Override
    public void onLoginStatusChanged(boolean isLoggedIn, User currentUser) {
        updateAccess();
    }

    private void updateAccess() {
        String role = AuthStatus.getInstance().getRole();
        addPropertyBtn.setDisable(!"owner".equalsIgnoreCase(role) && !"manager".equalsIgnoreCase(role));
    }

    public void onAddProperty(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/Properties/addPropertyForm.fxml"));
            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            newStage.setScene(scene);
            newStage.setTitle("Add Property");
            newStage.show();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
