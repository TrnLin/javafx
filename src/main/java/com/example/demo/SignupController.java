package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignupController {

    @FXML
    public final ToggleGroup roleToggleGroup = new ToggleGroup();
    @FXML
    public RadioButton hostRadioButton;
    @FXML
    public RadioButton ownerRadioButton;
    @FXML
    public RadioButton guestRadioButton;
    @FXML
    public Text status;
    @FXML
    public Text roleStatus;
    @FXML
    public Label emailStatus;
    @FXML
    public TextField registPasswordTextField;
    @FXML
    public PasswordField registPassField;
    @FXML
    public TextField registPassTextField;
    @FXML
    public PasswordField registConfirmPassField;
    @FXML
    public TextField registConfirmPassTextField;
    @FXML
    public Text registStatus;
    @FXML
    public Button toLoginViewBtn;
    @FXML
    public TextField registEmailTextField;
    @FXML
    public PasswordField registPasswordField;
    @FXML// ToggleGroup for role management
    public Button signUpBtn;

    @FXML
    private CheckBox showPasswordCheckBox; // Checkbox to toggle password visibility

    @FXML
    public void initialize() {
        // Add the ToggleGroup to each RadioButton
        hostRadioButton.setToggleGroup(roleToggleGroup);
        ownerRadioButton.setToggleGroup(roleToggleGroup);
        guestRadioButton.setToggleGroup(roleToggleGroup);

        // Hide the status text
        status.setVisible(false);
        roleStatus.setVisible(false);
        emailStatus.setVisible(false);

        // Set values to each RadioButton
        hostRadioButton.setUserData("host");
        ownerRadioButton.setUserData("owner");
        guestRadioButton.setUserData("tenant");

        // Bind password text fields
        registPassTextField.textProperty().bindBidirectional(registPassField.textProperty());
        registConfirmPassTextField.textProperty().bindBidirectional(registConfirmPassField.textProperty());

        // Add listener to CheckBox to toggle visibility
        showPasswordCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                registPassField.setVisible(false);
                registPassTextField.setVisible(true);
                registConfirmPassField.setVisible(false);
                registConfirmPassTextField.setVisible(true);
            } else {
                registPassField.setVisible(true);
                registPassTextField.setVisible(false);
                registConfirmPassField.setVisible(true);
                registConfirmPassTextField.setVisible(false);
            }
        });
    }

    public void onSignUpButtonClick(ActionEvent event) throws Exception {
        try {
            var email = registEmailTextField.getText();
            var password = registPassField.isVisible() ? registPassField.getText() : registPassTextField.getText();
            var role = roleToggleGroup.getSelectedToggle().getUserData().toString();
            var confirmPass = registConfirmPassField.isVisible() ? registConfirmPassField.getText() : registConfirmPassTextField.getText();
            // Validate email, role, and password
            boolean hasError = false;

            if (email.isEmpty()) {
                emailStatus.setVisible(true);
                emailStatus.setText("*Email cannot be empty!");
                hasError = true;
            } else {
                emailStatus.setVisible(false);
            }

            if (role == null) {
                roleStatus.setVisible(true);
                roleStatus.setText("*Please select a role!");
                hasError = true;
            } else {
                roleStatus.setVisible(false);
            }

            if (password.isEmpty()) {
                status.setVisible(true);
                status.setText("*Password cannot be empty!");
                hasError = true;
            } else {
                status.setVisible(false);
            }

            if (confirmPass.isEmpty()) {
                registStatus.setVisible(true);
                registStatus.setText("*Please confirm password!");
                hasError = true;
            } else {
                registStatus.setVisible(false);
            }

            if (!password.equals(confirmPass)) {
                registStatus.setVisible(true);
                registStatus.setText("*Passwords do not match!");
                hasError = true;
            } else {
                registStatus.setVisible(false);
            }

            // Stop processing if any field has an error
            if (hasError) {
                return;
            }


            createNewUser(email, password, role, confirmPass, event);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createNewUser(String email, String password, String role, String confirmPass, ActionEvent event) {
        // Create a new user

//         UserService userService = new UserService();
        try {
            // Validate email, role, and password
//            User user = new User(email, password, role);
//            if (!user.isValid()) {
//                status.setVisible(true);
//                status.setText("Invalid email, password, or role. Please try again.");
//                return;
//            } else {
//                status.setVisible(false);
//            }
            //TODO: Create a new user
            System.out.println("Role: " + role);
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
            System.out.println("Confirm Password: " + confirmPass);

            // Redirect to the login page
            redirectToLoginPage(event);

        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }
        // Log role, email, and password (for debugging or logging purposes)

    }

    public void onLoginViewBtnClick(ActionEvent event) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            // Handle general exceptions
            status.setVisible(true);
            status.setText("An unexpected error occurred. Please try again.");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void redirectToLoginPage(ActionEvent event) {
        // Implement the logic to redirect to the login page
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            // Handle general exceptions
            status.setVisible(true);
            status.setText("An unexpected error occurred. Please try again.");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}


