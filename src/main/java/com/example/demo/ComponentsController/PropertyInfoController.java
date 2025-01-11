package com.example.demo.ComponentsController;

import com.example.demo.Auth.Observer.AuthObserver;
import com.example.demo.Auth.Observer.AuthStatus;
import com.example.demo.model.Property;
import com.example.demo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PropertyInfoController implements AuthObserver {

    @FXML
    public TextField propertyIdField;
    @FXML
    public TextField addressField;
    @FXML
    public TextField pricingField;
    @FXML
    public TextField statusField;
    public Button updateInfoBtn;
    public Button deleteBtn;

    private User currentUser;

    Property chosenProperty;

    @FXML
    public void initialize() {
        // Register as an observer
        AuthStatus.getInstance().addObserver(this);

        // Disable fields initially
        setFieldsEditable(false);

        // Set fields editable only if an Owner is logged in
        if (AuthStatus.getInstance().isLoggedIn()) {
            currentUser = AuthStatus.getInstance().getCurrentUser();
            updateAccess();
        }
    }

    @Override
    public void onLoginStatusChanged(boolean isLoggedIn, User currentUser) {
        this.currentUser = currentUser;
        updateAccess();
    }

    public void setProperty(Property property) {
        // Set property details in the fields
        propertyIdField.setText(String.valueOf(property.getPropertyId()));
        addressField.setText(property.getAddress());
        pricingField.setText(String.format("%.2f", property.getPricing()));
        statusField.setText(property.getStatus());

        //Todo: Implement update property logic
        chosenProperty = property;

        // Update access to ensure only Owners can modify
        updateAccess();
    }

    private void updateAccess() {
        String role = AuthStatus.getInstance().getRole();
        setFieldsEditable("owner".equalsIgnoreCase(role) || "manager".equalsIgnoreCase(role));
    }

    private void setFieldsEditable(boolean editable) {
        propertyIdField.setEditable(editable);
        addressField.setEditable(editable);
        pricingField.setEditable(editable);
        statusField.setEditable(editable);


        // Set the prompt text based on the editable status
        propertyIdField.setPromptText(editable ? "Property ID" : "");
        addressField.setPromptText(editable ? "Address" : "");
        pricingField.setPromptText(editable ? "Price" : "");
        statusField.setPromptText(editable ? "Status" : "");

        propertyIdField.setDisable(!editable);
        addressField.setDisable(!editable);
        pricingField.setDisable(!editable);
        statusField.setDisable(!editable);
        updateInfoBtn.setDisable(!editable);
        deleteBtn.setDisable(!editable);

    }

    public void onUpdateProperty(ActionEvent event) {
        //Todo: Implement update property logic
        updateProperty(chosenProperty);
    }

    public void updateProperty(Property chosenProperty) {
       //Todo: Implement update property logic

        System.out.println("Update property logic");
        String address = addressField.getText();
        float pricing = Float.parseFloat(pricingField.getText());
        String status = statusField.getText();

        chosenProperty.setAddress(address);
        chosenProperty.setPricing(pricing);
        chosenProperty.setStatus(status);

    }

    public void onDeleteProperty(ActionEvent event) {
        //Todo: Implement delete property logic
        System.out.println("Delete property logic");
    }


}
