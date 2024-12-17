package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AuthController {

    public RadioButton hostRadioButton;
    public RadioButton ownerRadioButton;
    public RadioButton guestRadioButton;
    public Text status;
    public Button loginBtn;
    public Text roleStatus;
    public RadioButton managerRadioButton;
    public TextField emailTextField;
    public Label emailStatus;

    @FXML
    private VBox roleVBox; // Reference to the VBox containing the RadioButtons

    @FXML
    public final ToggleGroup roleToggleGroup = new ToggleGroup(); // ToggleGroup for role management

    @FXML
    private PasswordField passwordField; // Password field for hidden input

    @FXML
    private TextField passwordTextField; // Text field for plain text password

    @FXML
    private CheckBox showPasswordCheckBox; // Checkbox to toggle password visibility

    @FXML
    public void initialize() {
        // Add the ToggleGroup to each RadioButton
        hostRadioButton.setToggleGroup(roleToggleGroup);
        ownerRadioButton.setToggleGroup(roleToggleGroup);
        guestRadioButton.setToggleGroup(roleToggleGroup);
        managerRadioButton.setToggleGroup(roleToggleGroup);


        // Hide the status text
        status.setVisible(false);
        roleStatus.setVisible(false);
        emailStatus.setVisible(false);

        // Set values to each RadioButton
        hostRadioButton.setUserData("host");
        ownerRadioButton.setUserData("owner");
        guestRadioButton.setUserData("guest");
        managerRadioButton.setUserData("manager");


        // Bind password text fields
        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());

        // Add listener to CheckBox to toggle visibility
        showPasswordCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                passwordField.setVisible(false);
                passwordTextField.setVisible(true);
            } else {
                passwordField.setVisible(true);
                passwordTextField.setVisible(false);
            }
        });
    }

    public void onLoginButtonClick(ActionEvent event) {
        try {
            var email = emailTextField.getText();
            var password = passwordField.isVisible() ? passwordField.getText() : passwordTextField.getText();
            // Retrieve selected role using roleToggleGroup
            var selectedToggle = roleToggleGroup.getSelectedToggle();
            String selectedRole = null;
            if (selectedToggle != null) {
                selectedRole = (String) selectedToggle.getUserData();
            }

            // Validate email, role, and password
            boolean hasError = false;

            if (email.isEmpty()) {
                emailStatus.setVisible(true);
                emailStatus.setText("*Email cannot be empty!");
                hasError = true;
            } else {
                emailStatus.setVisible(false);
            }

            if (selectedRole == null) {
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

            // Stop processing if any field has an error
            if (hasError) {
                return;
            }

            // Log role, email, and password (for debugging or logging purposes)
            System.out.println("Role: " + selectedRole);
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);

            //TODO: Perform login logic here

        } catch (NullPointerException e) {
            // Handle cases where getSelectedToggle() or other potentially null objects might throw NullPointerException
            status.setVisible(true);
            status.setText("Please ensure all fields are filled correctly.");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Handle general exceptions
            status.setVisible(true);
            status.setText("An unexpected error occurred. Please try again.");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Getter to provide access to the ToggleGroup
    public ToggleGroup getRoleToggleGroup() {
        return roleToggleGroup;
    }
}