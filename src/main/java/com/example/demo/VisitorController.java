package com.example.demo;

import com.example.demo.model.CommercialProperty;
import com.example.demo.model.Property;
import com.example.demo.stric.PetFriendlyLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VisitorController {
    public TableView<Property> propertyTable;
    public TableView<CommercialProperty> comPropertyTable;
    public TableView<Property> rePropertyTable;

    public String newValue = "All Properties";
    public Button searchBtn;
    public TextField searchInput;
    public Button cancelBtn;
    public ChoiceBox viewOptions;
    public Button test;
    ObservableList<Property> properties = FXCollections.observableArrayList();
    ObservableList<CommercialProperty> commercialProperties = FXCollections.observableArrayList();

    public void initialize() {
        System.out.println("Visitor Controller Initialized");
//                 Add listener to ChoiceBox
        viewOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleChoiceBoxChange(newValue.toString());
            this.newValue = newValue.toString();
        });

        // Add listener to search button
        searchInput.setOnAction(event -> searchProperties(new ActionEvent()));

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

        TableColumn<Property, PetFriendlyLevel> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.getStyleClass().add("table-column");


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

        TableColumn<CommercialProperty, PetFriendlyLevel> comStatusCol = new TableColumn<>("Status");
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
        propertyTable.getColumns().addAll(propertyIdCol, ownerIdCol, pricingCol, addressCol, statusCol);
        comPropertyTable.getColumns().addAll(comPropertyIdCol, comOwnerIdCol, comPricingCol, comAddressCol, comStatusCol, comParkingSpaceCol, comSquareFootageCol, comBusinessTypeCol);


        // Load data from the file and display in the table
        ObservableList<Property> properties = readPropertiesFromFileProperty();
        propertyTable.setItems(properties);

        ObservableList<CommercialProperty> commercialProperties = readPropertiesFromFileCommercial();
        comPropertyTable.setItems(commercialProperties);


        // Set the default view to All Properties
        viewOptions.setValue("All Properties");
        handleChoiceBoxChange("All Properties");


        propertyTable.setManaged(true);
        comPropertyTable.setManaged(false);
//        rePropertyTable.setDisable(true);


    }

    private ObservableList<Property> readPropertiesFromFileProperty() {

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

        System.out.println("Commercial Properties loaded: " + commercialProperties.size());
//        for (CommercialProperty commercialProperty : commercialProperties) {
//            System.out.println(commercialProperty);
//        }
        return commercialProperties;
    }

    public void searchProperties(ActionEvent event) {
        String search = searchInput.getText();
        ObservableList<Property> searchResults = FXCollections.observableArrayList();
        ObservableList<CommercialProperty> searchResultsCom = FXCollections.observableArrayList();
        switch (viewOptions.getValue().toString()) {
            case "All Properties" -> {
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
            case "Commercial Properties" -> {
                for (Property property : properties) {
                    for (CommercialProperty commercialProperty : commercialProperties) {
                        if (property.getPropertyId() == commercialProperty.getPropertyId() && commercialProperty.getAddress().toLowerCase().contains(search.toLowerCase())) {
                            searchResultsCom.add(commercialProperty);
                        }
                    }
                }

                if (searchResultsCom.isEmpty()) {
                    System.out.println("No results found");
                } else {
                    comPropertyTable.setItems(searchResultsCom);
                    cancelBtn.setVisible(true);
                }
            }
        }
    }

    public void resetTable(ActionEvent event) {
        switch (newValue) {
            case "All Properties" -> {
                propertyTable.setItems(properties);
            }
            case "Commercial Properties" -> {
                comPropertyTable.setItems(commercialProperties);
            }
        }
        searchInput.clear();
        cancelBtn.setVisible(false);
    }

    private void handleChoiceBoxChange(String newValue) {
        boolean showComPropertyTable = false;
        switch (newValue) {
            case "All Properties" -> {
                propertyTable.toFront();
                comPropertyTable.toBack();

                propertyTable.setVisible(!showComPropertyTable);
                propertyTable.setManaged(!showComPropertyTable);
                comPropertyTable.setVisible(showComPropertyTable);
                comPropertyTable.setManaged(showComPropertyTable);

                propertyTable.setItems(properties);
                comPropertyTable.setItems(commercialProperties);
                System.out.println("All Properties selected");

            }
            case "Commercial Properties" -> {
                propertyTable.toBack();
                comPropertyTable.toFront();

                propertyTable.setVisible(showComPropertyTable);
                propertyTable.setManaged(showComPropertyTable);
                comPropertyTable.setVisible(!showComPropertyTable);
                comPropertyTable.setManaged(!showComPropertyTable);

                propertyTable.setItems(properties);
                comPropertyTable.setItems(commercialProperties);
                System.out.println("Commercial Properties selected");
            }
        }
    }


}

