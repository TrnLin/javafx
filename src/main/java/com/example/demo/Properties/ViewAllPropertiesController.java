package com.example.demo.Properties;

import com.example.demo.model.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ViewAllPropertiesController {
    public final ToggleGroup statusToggleGroup = new ToggleGroup();
    public TextField searchInput;
    public Button searchBtn;
    public Button cancelBtn;
    public TableView<Property> propertyTable;
    public ChoiceBox viewOptions;
    public RadioButton statusAvailable;
    public RadioButton statusRented;
    public RadioButton statusUnderMaintenace;
    public Button resetFilterBtn;

    ObservableList<Property> properties = FXCollections.observableArrayList();
    // Initialize the controller
    public void initialize() {
        System.out.println("Visitor Controller Initialized");
        // Add listener to search button
        searchInput.setOnAction(event -> searchProperties(new ActionEvent()));

        //set up the status radio buttons
        statusAvailable.setToggleGroup(statusToggleGroup);
        statusRented.setToggleGroup(statusToggleGroup);
        statusUnderMaintenace.setToggleGroup(statusToggleGroup);

        statusAvailable.setUserData("AVAILABLE");
        statusRented.setUserData("RENTED");
        statusUnderMaintenace.setUserData("UNDER_MAINTENANCE");

        // Set up the TableView
        TableColumn<Property, Integer> propertyIdCol = new TableColumn<>("Property ID");
        propertyIdCol.setCellValueFactory(new PropertyValueFactory<>("propertyId"));
        propertyIdCol.getStyleClass().add("table-column");


        TableColumn<Property, Integer> ownerIdCol = new TableColumn<>("Owner ID");
        ownerIdCol.setCellValueFactory(new PropertyValueFactory<>("ownerId"));
        ownerIdCol.getStyleClass().add("table-column");

        TableColumn<Property, Float> pricingCol = new TableColumn<>("Pricing");
        pricingCol.setCellValueFactory(new PropertyValueFactory<>("pricing"));
        pricingCol.getStyleClass().add("table-column");

        TableColumn<Property, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressCol.getStyleClass().add("table-column");

        TableColumn<Property, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.getStyleClass().add("table-column");

        // Add columns to the TableView
        propertyTable.getColumns().addAll(propertyIdCol, ownerIdCol, pricingCol, addressCol, statusCol);

        // Load data from the file and display in the table
        ObservableList<Property> properties = readPropertiesFromFileProperty();
        propertyTable.setItems(properties);

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

    public void searchProperties(ActionEvent event) {
        String search = searchInput.getText();
        ObservableList<Property> searchResults = FXCollections.observableArrayList();
        for (Property property : properties) {
            if (property.getAddress().toLowerCase().contains(search.toLowerCase())) {
                searchResults.add(property);
            }
        }
        if (searchResults.isEmpty()) {
            System.out.println("No results found");
        } else {
            propertyTable.setItems(searchResults);
            cancelBtn.setVisible(true);
        }
    }

    public void resetTable(ActionEvent event) {
        propertyTable.setItems(properties);
        cancelBtn.setVisible(false);
    }

    public void getStatusFilter(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) statusToggleGroup.getSelectedToggle();
        String status = (String) selectedRadioButton.getUserData();
        filterProperties(status);
    }

    public void filterProperties(String status) {
        ObservableList<Property> filteredProperties = FXCollections.observableArrayList();
        for (Property property : properties) {
            if (property.getStatus().equals(status)) {
                filteredProperties.add(property);
            }
        }
        propertyTable.setItems(filteredProperties);
        resetFilterBtn.setVisible(true);
    }

    public void resetFilter(ActionEvent event) {
        propertyTable.setItems(properties);
        resetFilterBtn.setVisible(false);
    }
}


