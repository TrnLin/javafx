package com.example.demo.Properties;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class AddPropertyForm {
    //FXML fields
    @FXML
    public TextField addressField;
    @FXML
    public TextField pricingField;
    @FXML
    public RadioButton propertyTypeCom;
    @FXML
    public RadioButton propertyTypeRes;
    @FXML
    public TextField parkingSpaceField;
    @FXML
    public TextField businessTypeField;
    @FXML
    public TextField squareFootageField;
    @FXML
    public TextField bedroomField;
    @FXML
    public CheckBox hasGardenField;
    @FXML
    public RadioButton petFriendlyLow;
    @FXML
    public RadioButton petFriendlyMedium;
    @FXML
    public RadioButton petFriendlyHigh;
    @FXML
    public RadioButton propertyStatusRented;
    @FXML
    public RadioButton propertyStatusAvailable;
    @FXML
    public RadioButton propertyStatusUnderMaintenance;
    @FXML
    public Button cancelBtn;
    @FXML
    public Button addPropertyBtn;

    //Toggle Group for property type
    @FXML
    public ToggleGroup propertyTypeToggleGroup = new ToggleGroup();

    //Toggle Group for pet friendly
    @FXML
    public ToggleGroup petFriendlyToggleGroup = new ToggleGroup();

    //Toggle Group for property status
    @FXML
    public ToggleGroup propertyStatusToggleGroup = new ToggleGroup();

    //Status text
    @FXML
    public Text addressStatus;
    @FXML
    public Text typeStatus;
    @FXML
    public Text parkingSpaceStatus;
    @FXML
    public Text businessTypeStatus;
    @FXML
    public Text squareFootageStatus;
    @FXML
    public Text bedroomStatus;
    @FXML
    public Text pricingStatus;
    @FXML
    public Text addPropertyStatus;

    @FXML
    public void initialize() {
        //set up the property type radio buttons
        propertyTypeCom.setToggleGroup(propertyTypeToggleGroup);
        propertyTypeRes.setToggleGroup(propertyTypeToggleGroup);
        propertyTypeCom.setUserData("commercial");
        propertyTypeRes.setUserData("residential");

        //set up the pet friendly radio buttons
        petFriendlyLow.setToggleGroup(petFriendlyToggleGroup);
        petFriendlyMedium.setToggleGroup(petFriendlyToggleGroup);
        petFriendlyHigh.setToggleGroup(petFriendlyToggleGroup);
        petFriendlyLow.setUserData("LOW");
        petFriendlyMedium.setUserData("MEDIUM");
        petFriendlyHigh.setUserData("HIGH");

        //set up the property status radio buttons
        propertyStatusRented.setToggleGroup(propertyStatusToggleGroup);
        propertyStatusAvailable.setToggleGroup(propertyStatusToggleGroup);
        propertyStatusUnderMaintenance.setToggleGroup(propertyStatusToggleGroup);
        propertyStatusRented.setUserData("RENTED");
        propertyStatusAvailable.setUserData("AVAILABLE");
        propertyStatusUnderMaintenance.setUserData("UNDER_MAINTENANCE");

        //set default view option
        propertyTypeToggleGroup.selectToggle(propertyTypeCom);
        petFriendlyToggleGroup.selectToggle(petFriendlyLow);
        propertyStatusToggleGroup.selectToggle(propertyStatusAvailable);

        // Add listener to property type radio buttons
        propertyTypeCom.setOnAction(this::typeSelected);
        propertyTypeRes.setOnAction(this::typeSelected);

        //disable residential fields
        bedroomField.setDisable(true);
        hasGardenField.setDisable(true);
        petFriendlyHigh.setDisable(true);
        petFriendlyMedium.setDisable(true);
        petFriendlyLow.setDisable(true);

        //set visible status to false
        addressStatus.setVisible(false);
        typeStatus.setVisible(false);
        parkingSpaceStatus.setVisible(false);
        businessTypeStatus.setVisible(false);
        squareFootageStatus.setVisible(false);
        bedroomStatus.setVisible(false);
        pricingStatus.setVisible(false);
        addPropertyStatus.setVisible(false);

        // Add listener to pricing field to allow only digits
        pricingField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9]*")) { // Allow only digits
                return change;
            }
            return null; // Reject the input
        }));

        // Add listener to parking space field to allow only digits
        setTextFormatter(parkingSpaceField);
        // Add listener to square footage field to allow only digits
        setTextFormatter(squareFootageField);
        // Add listener to bedroom field to allow only digits
        setTextFormatter(bedroomField);

        // Add listener to pricing field to allow only digits
        pricingField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            // Allow empty input or a valid floating-point number
            if (newText.isEmpty() || newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null; // Reject the input
        }));

    }


    public void typeSelected(ActionEvent event) {
        String selectedType = (String) propertyTypeToggleGroup.getSelectedToggle().getUserData();
        switch (selectedType) {
            case "commercial":
                System.out.println("Property Type Selected: " + selectedType);
                typeSelectedCom();
                break;
            case "residential":
                System.out.println("Property Type Selected: " + selectedType);
                typeSelectedRes();
                break;
            default:
                System.out.println("Property Type Selected: " + selectedType);
                break;
        }

    }

    public void typeSelectedCom() {
        //disable residential fields
        bedroomField.setDisable(true);
        hasGardenField.setDisable(true);
        petFriendlyHigh.setDisable(true);
        petFriendlyMedium.setDisable(true);
        petFriendlyLow.setDisable(true);

        //activate commercial fields
        parkingSpaceField.setDisable(false);
        businessTypeField.setDisable(false);
        squareFootageField.setDisable(false);
    }

    public void typeSelectedRes() {
        //disable commercial fields
        parkingSpaceField.setDisable(true);
        businessTypeField.setDisable(true);
        squareFootageField.setDisable(true);

        //activate residential fields
        bedroomField.setDisable(false);
        hasGardenField.setDisable(false);
        petFriendlyHigh.setDisable(false);
        petFriendlyMedium.setDisable(false);
        petFriendlyLow.setDisable(false);
    }

    public void onAddProperty(ActionEvent event) {
        if (validateInputs()) {
            return; // If validation fails, stop further processing
        }
        addPropertyStatus.setVisible(true);
        addPropertyStatus.setText("Property added successfully!");
        // Proceed with property addition logic
        String address = addressField.getText();
        float pricing = Float.parseFloat(pricingField.getText());
        String type = (String) propertyTypeToggleGroup.getSelectedToggle().getUserData();
        int parkingSpace = type.equals("commercial") ? Integer.parseInt(parkingSpaceField.getText()) : 0;
        int squareFootage = type.equals("commercial") ? Integer.parseInt(squareFootageField.getText()) : 0;
        String businessType = type.equals("commercial") ? businessTypeField.getText() : "";
        int bedroom = type.equals("residential") ? Integer.parseInt(bedroomField.getText()) : 0;
        boolean hasGarden = type.equals("residential") && hasGardenField.isSelected();
        String petFriendly = type.equals("residential") ? (String) petFriendlyToggleGroup.getSelectedToggle().getUserData() : "";
        String propertyStatus = (String) propertyStatusToggleGroup.getSelectedToggle().getUserData();

        // Perform your add property logic here
        System.out.println("Property added: " + address);
        System.out.println("Pricing: " + pricing);
        System.out.println("Type: " + type);
        System.out.println("Parking Space: " + parkingSpace);
        System.out.println("Square Footage: " + squareFootage);
        System.out.println("Business Type: " + businessType);
        System.out.println("Bedroom: " + bedroom);
        System.out.println("Has Garden: " + hasGarden);
        System.out.println("Pet Friendly: " + petFriendly);
        System.out.println("Property Status: " + propertyStatus);

        // Add the property to the database
        //Todo: Add property logic here
        cancelAddProperty(event);


    }

    private boolean validateInputs() {
        boolean hasError = false;

        // Validate address
        if (addressField.getText().isEmpty()) {
            addressStatus.setVisible(true);
            addressStatus.setText("*Address cannot be empty!");
            hasError = true;
        } else {
            addressStatus.setVisible(false);
        }

        // Validate pricing
        try {
            float pricing = Float.parseFloat(pricingField.getText());
            if (pricing <= 0) {
                pricingStatus.setVisible(true);
                pricingStatus.setText("*Pricing must be greater than 0!");
                hasError = true;
            } else {
                pricingStatus.setVisible(false);
            }
        } catch (NumberFormatException e) {
            pricingStatus.setVisible(true);
            pricingStatus.setText("*Invalid pricing format!");
            hasError = true;
        }

        // Validate commercial/residential-specific inputs
        String type = (String) propertyTypeToggleGroup.getSelectedToggle().getUserData();
        if (type.equals("commercial")) {
            hasError = validateCommercialInputs() || hasError;
        } else if (type.equals("residential")) {
            hasError = validateResidentialInputs() || hasError;
        }

        return hasError;
    }

    private boolean validateCommercialInputs() {
        boolean hasError = false;

        // Validate parking space
        try {
            int parkingSpace = Integer.parseInt(parkingSpaceField.getText());
            if (parkingSpace <= 0) {
                parkingSpaceStatus.setVisible(true);
                parkingSpaceStatus.setText("*Parking Space must be greater than 0!");
                hasError = true;
            } else {
                parkingSpaceStatus.setVisible(false);
            }
        } catch (NumberFormatException e) {
            parkingSpaceStatus.setVisible(true);
            parkingSpaceStatus.setText("*Invalid parking space format!");
            hasError = true;
        }

        // Validate square footage
        try {
            int squareFootage = Integer.parseInt(squareFootageField.getText());
            if (squareFootage <= 0) {
                squareFootageStatus.setVisible(true);
                squareFootageStatus.setText("*Square Footage must be greater than 0!");
                hasError = true;
            } else {
                squareFootageStatus.setVisible(false);
            }
        } catch (NumberFormatException e) {
            squareFootageStatus.setVisible(true);
            squareFootageStatus.setText("*Invalid square footage format!");
            hasError = true;
        }

        // Validate business type
        if (businessTypeField.getText().isEmpty()) {
            businessTypeStatus.setVisible(true);
            businessTypeStatus.setText("*Business Type cannot be empty!");
            hasError = true;
        } else {
            businessTypeStatus.setVisible(false);
        }

        return hasError;
    }

    private boolean validateResidentialInputs() {
        boolean hasError = false;

        // Validate bedroom
        try {
            int bedroom = Integer.parseInt(bedroomField.getText());
            if (bedroom <= 0) {
                bedroomStatus.setVisible(true);
                bedroomStatus.setText("*Bedroom must be greater than 0!");
                hasError = true;
            } else {
                bedroomStatus.setVisible(false);
            }
        } catch (NumberFormatException e) {
            bedroomStatus.setVisible(true);
            bedroomStatus.setText("*Invalid bedroom format!");
            hasError = true;
        }

        return hasError;
    }

    public void cancelAddProperty(ActionEvent event) {
        // Close the form
        addPropertyStatus.setVisible(false);
        addressField.clear();
        pricingField.clear();
        propertyTypeToggleGroup.selectToggle(propertyTypeCom);
        parkingSpaceField.clear();
        squareFootageField.clear();
        businessTypeField.clear();
        bedroomField.clear();
        hasGardenField.setSelected(false);
        petFriendlyToggleGroup.selectToggle(petFriendlyLow);
        propertyStatusToggleGroup.selectToggle(propertyStatusAvailable);
    }

    public void setTextFormatter(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9]*")) { // Allow only digits
                return change;
            }
            return null; // Reject the input
        }));
    }

}
