package com.example.demo.Properties;

import com.example.demo.model.CommercialProperty;
import com.example.demo.model.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ViewComPropertiesController {
    public final ToggleGroup statusToggleGroup = new ToggleGroup();
    public TableView<CommercialProperty> comPropertyTable;
    public TextField searchInput;
    public Button searchBtn;
    public Button cancelBtn;
    public ChoiceBox viewOptions;
    public RadioButton statusAvailable;
    public RadioButton statusRented;
    public RadioButton statusUnderMaintenance;
    public Button resetFilterBtn;
    public Button test;
    public Button filterBtn;
    public Button cancelFilterBtn;
    public TextField businessTypeInput;
    public TextField squareFootageFrom;
    public TextField squareFootageTo;
    public RadioButton statusAll;

    ObservableList<CommercialProperty> commercialProperties = FXCollections.observableArrayList();
    ObservableList<Property> properties = FXCollections.observableArrayList();
    ObservableList<CommercialProperty> filteredProperties = FXCollections.observableArrayList();

    public void initialize() {
        //set up the status radio buttons
        statusAvailable.setToggleGroup(statusToggleGroup);
        statusRented.setToggleGroup(statusToggleGroup);
        statusUnderMaintenance.setToggleGroup(statusToggleGroup);
        statusAll.setToggleGroup(statusToggleGroup);


        // Set user data for the radio buttons
        statusAll.setUserData("ALL");
        statusAvailable.setUserData("AVAILABLE");
        statusRented.setUserData("RENTED");
        statusUnderMaintenance.setUserData("UNDER_MAINTENANCE");

        // Set user data for the radio buttons
        statusToggleGroup.selectToggle(statusAll);

        // Add listener to search button
        searchInput.setOnAction(event -> searchProperties(new ActionEvent()));

        // Add listener to ChoiceBox
        viewOptions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                viewOptions(newValue);
            }
        });

        // Commercial Property Table
        TableColumn<CommercialProperty, Integer> comPropertyIdCol = new TableColumn<>("Property ID");
        comPropertyIdCol.setCellValueFactory(new PropertyValueFactory<>("propertyId"));
        comPropertyIdCol.getStyleClass().add("table-column");


        TableColumn<CommercialProperty, Integer> comOwnerIdCol = new TableColumn<>("Owner ID");
        comOwnerIdCol.setCellValueFactory(new PropertyValueFactory<>("ownerId"));
        comOwnerIdCol.getStyleClass().add("table-column");

        TableColumn<CommercialProperty, Float> comPricingCol = new TableColumn<>("Pricing");
        comPricingCol.setCellValueFactory(new PropertyValueFactory<>("pricing"));
        comPricingCol.getStyleClass().add("table-column");

        TableColumn<CommercialProperty, String> comAddressCol = new TableColumn<>("Address");
        comAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        comAddressCol.getStyleClass().add("table-column");

        TableColumn<CommercialProperty, String> comStatusCol = new TableColumn<>("Status");
        comStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        comStatusCol.getStyleClass().add("table-column");

        TableColumn<CommercialProperty, Integer> comParkingSpaceCol = new TableColumn<>("Parking Space");
        comParkingSpaceCol.setCellValueFactory(new PropertyValueFactory<>("parkingSpace"));
        comParkingSpaceCol.getStyleClass().add("table-column");

        TableColumn<CommercialProperty, Integer> comSquareFootageCol = new TableColumn<>("Square Footage");
        comSquareFootageCol.setCellValueFactory(new PropertyValueFactory<>("squareFootage"));
        comSquareFootageCol.getStyleClass().add(("table-column"));

        TableColumn<CommercialProperty, String> comBusinessTypeCol = new TableColumn<>("Business Type");
        comBusinessTypeCol.setCellValueFactory(new PropertyValueFactory<>("businessType"));
        comBusinessTypeCol.getStyleClass().add("table-column");

        // Add columns to the TableView
        comPropertyTable.getColumns().addAll(comPropertyIdCol, comOwnerIdCol, comPricingCol, comAddressCol, comStatusCol, comParkingSpaceCol, comSquareFootageCol, comBusinessTypeCol);

        // Load data from the file and display in the table
        properties = readPropertiesFromFileProperty();

        // Set the items in the table
        ObservableList<CommercialProperty> commercialProperties = readPropertiesFromFileCommercial();
        comPropertyTable.setItems(commercialProperties);
    }

    ObservableList<Property> readPropertiesFromFileProperty() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/example/demo/data/properties_rows(in).csv"))) {
            // Skip the header if the file has one
            String header = reader.readLine(); // Comment this line if no header exists
            String line;
            while ((line = reader.readLine()) != null) {
                // Split by comma for CSV format, considering quotes around address
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                int ownerId = Integer.parseInt(parts[0]);
                float pricing = Float.parseFloat(parts[1]);
                int propertyId = Integer.parseInt(parts[2]);
                String address = parts[3].replace("\"", "");
                String status = parts[4];

//                System.out.println("Owner ID: " + ownerId + ", Pricing: " + pricing + ", Property ID: " + propertyId + ", Address: " + address + ", Status: " + status);

                // Create a new Property object
                Property property = new Property(ownerId, pricing, propertyId, address, status);
                properties.add(property);
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return properties;
    }

    private ObservableList<CommercialProperty> readPropertiesFromFileCommercial() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/example/demo/data/commercial_properties_rows(in).csv"))) {
            // Skip the header if the file has one
            String header = reader.readLine(); // Comment this line if no header exists
            String line;
            while ((line = reader.readLine()) != null) {
                // Split by comma for CSV format
                String[] parts = line.split(",");
                int parkingSpace = Integer.parseInt(parts[0]);
                int propertyId = Integer.parseInt(parts[1]);
                int squareFootage = Integer.parseInt(parts[2]);
                String businessType = parts[3];

                for (Property property : properties) {
                    if (property.getPropertyId() == propertyId) {
                        int ownerId = property.getOwnerId();
                        float pricing = property.getPricing();
                        String address = property.getAddress();
                        String status = property.getStatus();
                        CommercialProperty commercialProperty = new CommercialProperty(ownerId, pricing, propertyId, address, status, parkingSpace, squareFootage, businessType);
                        commercialProperties.add(commercialProperty);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return commercialProperties;
    }

    // Search Properties
    public void searchProperties(ActionEvent event) {
        String search = searchInput.getText();
        ObservableList<CommercialProperty> searchResult = FXCollections.observableArrayList();

        // Search for the address in the commercial properties
        for (CommercialProperty commercialProperty : commercialProperties) {
            if (commercialProperty.getAddress().toLowerCase().contains(search.toLowerCase())) {
                searchResult.add(commercialProperty);
            }
        }

        if (searchResult.isEmpty()) {
            System.out.println("No results found");
        } else {
            comPropertyTable.setItems(searchResult);
            cancelBtn.setVisible(true);
        }

    }

    public void resetTable(ActionEvent event) {
        comPropertyTable.setItems(commercialProperties);
        cancelBtn.setVisible(false);
        searchInput.clear();
    }

    // Filter Properties
    // Method to filter properties
    public void filterBtnClicked(ActionEvent event) {
        ObservableList<CommercialProperty> filteredProperties = FXCollections.observableArrayList();

        String status = getStatus() != null ? getStatus() : "";
        String type = getBusinessType() != null ? getBusinessType() : "";
        int squareFootageFrom = getSquareFootageFrom();
        int squareFootageTo = getSquareFootageTo();


        if (status.equals("ALL")) {
            for (CommercialProperty commercialProperty : commercialProperties) {
                if (commercialProperty.getBusinessType().toLowerCase().contains(type.toLowerCase()) && commercialProperty.getSquareFootage() >= squareFootageFrom && commercialProperty.getSquareFootage() <= squareFootageTo) {
                    filteredProperties.add(commercialProperty);
                }
            }
        } else {
            for (CommercialProperty commercialProperty : commercialProperties) {
                if (commercialProperty.getBusinessType().toLowerCase().contains(type.toLowerCase()) && commercialProperty.getSquareFootage() >= squareFootageFrom && commercialProperty.getSquareFootage() <= squareFootageTo && commercialProperty.getStatus().equals(status)) {
                    filteredProperties.add(commercialProperty);
                }
            }
        }

        comPropertyTable.setItems(filteredProperties);
        cancelFilterBtn.setVisible(true);

    }

    // Cancel Filter
    public void cancelFilterBtnClicked(ActionEvent event) {
        comPropertyTable.setItems(commercialProperties);
        statusToggleGroup.selectToggle(statusAll);
        cancelFilterBtn.setVisible(false);
        businessTypeInput.clear();
        squareFootageFrom.clear();
        squareFootageTo.clear();
    }

    public String getBusinessType() {
        return businessTypeInput.getText();
    }

    public int getSquareFootageFrom() {
        return squareFootageFrom.getText().isEmpty() ? 0 : Integer.parseInt(squareFootageFrom.getText());
    }

    public int getSquareFootageTo() {
        return squareFootageTo.getText().isEmpty() ? 99999999 : Integer.parseInt(squareFootageTo.getText());
    }

    public String getStatus() {
        RadioButton selectedRadioButton = (RadioButton) statusToggleGroup.getSelectedToggle();
        return (String) selectedRadioButton.getUserData();
    }

    // View Options
    // Method to view options
    public void viewOptions(String option) {
        switch (option) {
            case "All Properties":
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/Properties/viewAllProperties.fxml"));
                    Stage stage = (Stage) viewOptions.getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                    stage.setTitle("All Properties");
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            case "Commercial Properties":
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/Properties/viewComProperties.fxml"));
                    Stage stage = (Stage) viewOptions.getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                    stage.setScene(scene);
                    stage.setTitle("Commercial Properties");
                    stage.show();
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            case "Residential Properties":
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/Properties/viewResProperties.fxml"));
                    Stage stage = (Stage) viewOptions.getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                    stage.setTitle("Residential Properties");
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Invalid option selected");
                break;
        }
    }
}
