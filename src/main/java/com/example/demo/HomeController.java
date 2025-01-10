package com.example.demo;

import com.example.demo.Observer.AuthObserver;
import com.example.demo.Observer.AuthStatus;
import com.example.demo.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class HomeController implements AuthObserver {

    @FXML
    private StackPane contentPane;

    @FXML
    private Button viewPropertyBtn;

    @FXML
    private Button viewRentalAgreementsBtn;

    @FXML
    public void initialize() {
        // Register as an observer
        AuthStatus.getInstance().addObserver(this);

        // Set the default state of buttons based on login status
        updateButtonAccess();

        // Default screen load
        loadDefaultPage();
    }

    private void loadPage(String fxmlFile) {
        try {
            // Load the new FXML file into the content pane
            Node page = FXMLLoader.load(getClass().getResource(fxmlFile));
            // Load the CSS if required
            contentPane.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDefaultPage() {
        loadPage("Properties/viewPropertyContainer.fxml");
    }

    @FXML
    private void loadPage1() {
        if (AuthStatus.getInstance().isLoggedIn()) {
            loadPage("Properties/viewPropertyContainer.fxml");
        }
    }

    @FXML
    private void loadPage2() {
        if (AuthStatus.getInstance().isLoggedIn()) {
            loadPage("Auth/signup.fxml");
        }
    }

    @FXML
    private void loadPage3() {
        if (AuthStatus.getInstance().isLoggedIn()) {
            loadPage("page3.fxml");
        }
    }

    @FXML
    private void loadPage4() {
        if (AuthStatus.getInstance().isLoggedIn()) {
            loadPage("page4.fxml");
        }
    }

    @Override
    public void onLoginStatusChanged(boolean isLoggedIn, User currentUser) {
        updateButtonAccess();
    }

    private void updateButtonAccess() {
        boolean isLoggedIn = AuthStatus.getInstance().isLoggedIn();
        viewRentalAgreementsBtn.setDisable(!isLoggedIn);
    }
}
