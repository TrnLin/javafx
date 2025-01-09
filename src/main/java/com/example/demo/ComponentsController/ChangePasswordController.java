package com.example.demo.ComponentsController;

import com.example.demo.Observer.AuthObserver;
import com.example.demo.Observer.AuthStatus;
import com.example.demo.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;

public class ChangePasswordController implements AuthObserver {
    @FXML
    public PasswordField currentPassField;
    @FXML
    public PasswordField newPassField;
    @FXML
    public PasswordField conPassField;
    @FXML
    public Button cancelBtn;
    @FXML
    public Button changePassBtn;
    @FXML
    public Text currentPassStatus;
    @FXML
    public Text newPassStatus;
    @FXML
    public Text conPassStatus;

    private User currentUser;

    @FXML
    public void initialize() {
        // Register this class as an observer
        AuthStatus.getInstance().addObserver(this);

        // Check if the user is already logged in
        if (AuthStatus.getInstance().isLoggedIn()) {
            currentUser = AuthStatus.getInstance().getCurrentUser();
            changePassBtn.setDisable(false);
        } else {
            changePassBtn.setDisable(true);
        }

        // Reset error messages and disable by default
        resetStatusMessages();
    }

    @Override
    public void onLoginStatusChanged(boolean isLoggedIn, User currentUser) {
        if (isLoggedIn) {
            this.currentUser = currentUser;
            changePassBtn.setDisable(false);
        } else {
            this.currentUser = null;
            changePassBtn.setDisable(true);
        }
        resetStatusMessages();
    }

    @FXML
    public void onChangePassword() {
        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No user is currently logged in.");
            return;
        }

        // Debugging logs
        System.out.println("Change password clicked!");
        System.out.println("Current user: " + currentUser.getFullName());

        // Validate inputs
        if (!validateInputs()) {
            return;
        }

        // Check if the current password matches the user's existing password
        if (!currentUser.getPassword().equals(currentPassField.getText())) {
            currentPassStatus.setText("*Current password is incorrect.");
            currentPassStatus.setVisible(true);
            return;
        }

        // Update password
        currentUser.setPassword(newPassField.getText());
        showAlert(Alert.AlertType.INFORMATION, "Password Changed", "Your password has been successfully updated!");

        // Clear fields after a successful change
        clearFields();
    }

    @FXML
    public void onCancel() {
        clearFields();
        resetStatusMessages();
    }

    private boolean validateInputs() {
        boolean isValid = true;

        resetStatusMessages();

        if (currentPassField.getText().isEmpty()) {
            currentPassStatus.setText("*Current password is required.");
            currentPassStatus.setVisible(true);
            isValid = false;
        }

        if (newPassField.getText().isEmpty()) {
            newPassStatus.setText("*New password is required.");
            newPassStatus.setVisible(true);
            isValid = false;
        } else if (newPassField.getText().length() < 8) {
            newPassStatus.setText("*Password must be at least 8 characters.");
            newPassStatus.setVisible(true);
            isValid = false;
        }

        if (conPassField.getText().isEmpty()) {
            conPassStatus.setText("*Confirm password is required.");
            conPassStatus.setVisible(true);
            isValid = false;
        } else if (!conPassField.getText().equals(newPassField.getText())) {
            conPassStatus.setText("*Passwords do not match.");
            conPassStatus.setVisible(true);
            isValid = false;
        }

        return isValid;
    }

    private void clearFields() {
        currentPassField.clear();
        newPassField.clear();
        conPassField.clear();
    }

    private void resetStatusMessages() {
        currentPassStatus.setText("");
        newPassStatus.setText("");
        conPassStatus.setText("");
        currentPassStatus.setVisible(false);
        newPassStatus.setVisible(false);
        conPassStatus.setVisible(false);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
