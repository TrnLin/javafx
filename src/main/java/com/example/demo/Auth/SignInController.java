package com.example.demo.Auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignInController {

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
    public Button loginBtn;
    @FXML
    public Text roleStatus;
    @FXML
    public RadioButton managerRadioButton;
    @FXML
    public TextField emailTextField;
    @FXML
    public Label emailStatus;
    @FXML
    public Button visitorBtn;
    @FXML
    public Button toSignUpViewBtn;
    @FXML// ToggleGroup for role management
    public Button signUpBtn;

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
        guestRadioButton.setUserData("tenant");
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

    public void onLoginButtonClick(ActionEvent event) throws Exception {
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
            //TODO: Perform login logic here
            login(email, password, selectedRole, event);
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

    public void onGuessButtonClick(ActionEvent event) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/Properties/viewAllProperties.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Visitor");
            primaryStage.show();
//            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            // Handle general exceptions
            status.setVisible(true);
            status.setText("An unexpected error occurred. Please try again.");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();

        }
    }

    public void onSignUpViewBtnClick(ActionEvent event) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/Auth/signup.fxml"));
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

    public void login(String email, String password, String selectedRole, ActionEvent event) {
        // Assuming you have a UserService to handle user authentication
//        UserService userService = new UserService();
        try {
//            User user = userService.authenticateUser(email, password, role);
//            if (user != null) {
//                System.out.println("Login successful: " + email);
//                // Redirect to the home page or dashboard
//                redirectToHomePage();
//            } else {
//                System.err.println("Invalid email, password, or role");
//                status.setVisible(true);
//                status.setText("Invalid email, password, or role. Please try again.");
//            }

            // Log role, email, and password (for debugging or logging purposes)
            System.out.println("Role: " + selectedRole);
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
            redirectToHomePage(event);
        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
            e.printStackTrace();
            status.setVisible(true);
            status.setText("An unexpected error occurred. Please try again.");
        }
    }

    private void redirectToHomePage(ActionEvent event) {
        // Implement the logic to redirect to the home page or dashboard
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/Properties/viewAllProperties.fxml"));
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

