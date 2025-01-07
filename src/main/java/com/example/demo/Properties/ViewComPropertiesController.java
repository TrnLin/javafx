package com.example.demo.Properties;

import com.example.demo.model.CommercialProperty;
import com.example.demo.model.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    public RadioButton statusUnderMaintenace;
    public Button resetFilterBtn;
    ObservableList<CommercialProperty> commercialProperties = FXCollections.observableArrayList();
    ObservableList<Property> properties = FXCollections.observableArrayList();

    public void initialize() {
        properties = readPropertiesFromFileProperty();
        commercialProperties = readPropertiesFromFileCommercial();

        //set up the status radio buttons
        statusAvailable.setToggleGroup(statusToggleGroup);
        statusRented.setToggleGroup(statusToggleGroup);
        statusUnderMaintenace.setToggleGroup(statusToggleGroup);

        statusAvailable.setUserData("AVAILABLE");
        statusRented.setUserData("RENTED");
        statusUnderMaintenace.setUserData("UNDER_MAINTENANCE");

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


        comPropertyTable.getColumns().addAll(comPropertyIdCol, comOwnerIdCol, comPricingCol, comAddressCol, comStatusCol, comParkingSpaceCol, comSquareFootageCol, comBusinessTypeCol);

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

            System.out.println("Properties loaded: " + properties.size());
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

                System.out.println(propertyId);

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

    public void searchProperties(ActionEvent event) {
    }

    public void resetTable(ActionEvent event) {
    }

    public void getStatusFilter(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) statusToggleGroup.getSelectedToggle();
        String status = (String) selectedRadioButton.getUserData();
        filterProperties(status);
    }

    public void filterProperties(String status) {
        ObservableList<CommercialProperty> filteredProperties = FXCollections.observableArrayList();
        for (Property property : properties) {
            for (CommercialProperty commercialProperty : commercialProperties) {
                if (property.getPropertyId() == commercialProperty.getPropertyId() && property.getStatus().equals(status)) {
                    filteredProperties.add(commercialProperty);
                }
            }
        }
        comPropertyTable.setItems(filteredProperties);
        resetFilterBtn.setVisible(true);
    }

    public void resetFilter(ActionEvent event) {
        comPropertyTable.setItems(commercialProperties);
        resetFilterBtn.setVisible(false);
    }
}
