package com.example.demo.ComponentsController;

import com.example.demo.Observer.AuthObserver;
import com.example.demo.Observer.AuthStatus;
import com.example.demo.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ViewAccountInfo implements AuthObserver {
    @FXML
    public TextField fullNameField;
    @FXML
    public TextField contactInfoField;
    @FXML
    public TextField DoBField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button changePassBtn;
    @FXML
    public Button updateInfoBtn;
    public Text updateStatusText;

    @FXML
    public void initialize() {
        // Register this class as an observer
        AuthStatus.getInstance().addObserver(this);

        // Disable fields by default until user is logged in
        fullNameField.setDisable(true);
        contactInfoField.setDisable(true);
        DoBField.setDisable(true);

        changePassBtn.setDisable(true);
        updateInfoBtn.setDisable(true);

        // Set user info if already logged in
        if (AuthStatus.getInstance().isLoggedIn()) {
            User currentUser = AuthStatus.getInstance().getCurrentUser();
            updateUserInfo(currentUser);
        }

        updateStatusText.setText("");
        updateStatusText.setVisible(false);
    }

    @Override
    public void onLoginStatusChanged(boolean isLoggedIn, User currentUser) {
        if (isLoggedIn) {
            updateUserInfo(currentUser);
        } else {
            clearUserInfo();
        }
    }

    private void updateUserInfo(User user) {
        if (user != null) {
            // Enable fields and buttons
            fullNameField.setDisable(false);
            contactInfoField.setDisable(false);
            DoBField.setDisable(false);

            changePassBtn.setDisable(false);
            updateInfoBtn.setDisable(false);

            // Set user details in fields
            fullNameField.setText(user.getFullName());
            contactInfoField.setText(user.getContact());
            DoBField.setText(new SimpleDateFormat("yyyy-MM-dd").format(user.getDob()));
            passwordField.setText(user.getPassword());
        }
    }

    private void clearUserInfo() {
        // Disable fields and buttons
        fullNameField.setDisable(true);
        contactInfoField.setDisable(true);
        DoBField.setDisable(true);
        changePassBtn.setDisable(true);
        updateInfoBtn.setDisable(true);

        // Clear fields
        fullNameField.clear();
        contactInfoField.clear();
        DoBField.clear();
        passwordField.clear();
    }

    @FXML
    public void onChangePassword() {
        // Handle password change logic here
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/components/changePassword.fxml"));
            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            newStage.setScene(scene);
            newStage.setTitle("Change Password");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateInfo() throws ParseException {
        // Handle update info logic here
        System.out.println("Update info clicked!");
        String fullName = fullNameField.getText();
        String contact = contactInfoField.getText();
        String dob = DoBField.getText();
        String password = passwordField.getText();

        // Validate input
        if (fullName.isEmpty() || contact.isEmpty() || dob.isEmpty() || password.isEmpty()) {
            System.out.println("Please fill in all fields!");
            updateStatusText.setText("Please fill in all fields!");
            updateStatusText.setStyle("-fx-fill: red;");
            return;
        }

        // Update user info
        User currentUser = AuthStatus.getInstance().getCurrentUser();
        currentUser.setFullName(fullName);
        currentUser.setContact(contact);
        currentUser.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(dob));
        currentUser.setPassword(password);

        //Todo: Update user info in the database



        // Simulate saving updated info (you'd replace this with actual logic)
        System.out.println("Updated Info:");
        System.out.println("Full Name: " + fullName);
        System.out.println("Contact: " + contact);
        System.out.println("Date of Birth: " + dob);
        System.out.println("Password: " + password);
        updateStatusText.setText("Information updated successfully!");
        updateStatusText.setStyle("-fx-fill: green;");
        updateStatusText.setVisible(true);

        // Update user logic here
    }
}
