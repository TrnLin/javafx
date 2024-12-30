package com.example.demo;

import com.example.demo.model.CommercialProperty;
import com.example.demo.model.Property;
import com.example.demo.model.ResidentialProperty;
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
    public TableView<ResidentialProperty> resPropertyTable;

    public String newValue = "All Properties";
    public Button searchBtn;
    public TextField searchInput;
    public Button cancelBtn;
    public ChoiceBox viewOptions;

    ObservableList<Property> properties = FXCollections.observableArrayList();
    ObservableList<CommercialProperty> commercialProperties = FXCollections.observableArrayList();
    ObservableList<ResidentialProperty> residentialProperties = FXCollections.observableArrayList();

    // Initialize the controller
    public void initialize() {
        System.out.println("Visitor Controller Initialized");
        //Add listener to ChoiceBox
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

        TableColumn<Property, String> statusCol = new TableColumn<>("Status");
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

        //Residential Property Table
        TableColumn<ResidentialProperty, Integer> rePropertyIdCol = new TableColumn<>("Property ID");
        rePropertyIdCol.setCellValueFactory(new PropertyValueFactory<>("propertyId"));
        rePropertyIdCol.getStyleClass().add("table-column");

        TableColumn<ResidentialProperty, Integer> reOwnerIdCol = new TableColumn<>("Owner ID");
        reOwnerIdCol.setCellValueFactory(new PropertyValueFactory<>("ownerId"));
        reOwnerIdCol.getStyleClass().add("table-column");

        TableColumn<ResidentialProperty, Float> rePricingCol = new TableColumn<>("Pricing");
        rePricingCol.setCellValueFactory(new PropertyValueFactory<>("pricing"));
        rePricingCol.getStyleClass().add("table-column");

        TableColumn<ResidentialProperty, String> reAddressCol = new TableColumn<>("Address");
        reAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        reAddressCol.getStyleClass().add("table-column");

        TableColumn<ResidentialProperty, String> reStatusCol = new TableColumn<>("Status");
        reStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        reStatusCol.getStyleClass().add("table-column");

        TableColumn<ResidentialProperty, Integer> reBedroomCol = new TableColumn<>("Bedroom");
        reBedroomCol.setCellValueFactory(new PropertyValueFactory<>("bedrooms"));
        reBedroomCol.getStyleClass().add("table-column");

        TableColumn<ResidentialProperty, Boolean> reHasGardenCol = new TableColumn<>("Has Garden");
        reHasGardenCol.setCellValueFactory(new PropertyValueFactory<>("hasGarden"));
        reHasGardenCol.getStyleClass().add("table-column");

        TableColumn<ResidentialProperty, String> rePetFriendlyLevelCol = new TableColumn<>("Pet Friendly Level");
        rePetFriendlyLevelCol.setCellValueFactory(new PropertyValueFactory<>("petFriendlyLevel"));
        rePetFriendlyLevelCol.getStyleClass().add("table-column");


        // Add columns to the TableView
        propertyTable.getColumns().addAll(propertyIdCol, ownerIdCol, pricingCol, addressCol, statusCol);
        comPropertyTable.getColumns().addAll(comPropertyIdCol, comOwnerIdCol, comPricingCol, comAddressCol, comStatusCol, comParkingSpaceCol, comSquareFootageCol, comBusinessTypeCol);
        resPropertyTable.getColumns().addAll(rePropertyIdCol, reOwnerIdCol, rePricingCol, reAddressCol, reStatusCol, reBedroomCol, reHasGardenCol, rePetFriendlyLevelCol);


        // Load data from the file and display in the table
        ObservableList<Property> properties = readPropertiesFromFileProperty();
        propertyTable.setItems(properties);

        ObservableList<CommercialProperty> commercialProperties = readPropertiesFromFileCommercial();
        comPropertyTable.setItems(commercialProperties);

        ObservableList<ResidentialProperty> residentialProperties = readPropertiesFromFileResidential();
        resPropertyTable.setItems(residentialProperties);


        // Set the default view to All Properties
        viewOptions.setValue("All Properties");
        handleChoiceBoxChange("All Properties");


        propertyTable.setManaged(true);
        comPropertyTable.setManaged(false);
        resPropertyTable.setManaged(false);
    }

    // Read data from the file and return an ObservableList of Property objects
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

    private ObservableList<ResidentialProperty> readPropertiesFromFileResidential() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/example/demo/data/residential_properties_rows(in).csv"))) {
            // Skip the header if the file has one
            String header = reader.readLine(); // Comment this line if no header exists
            String line;
            while ((line = reader.readLine()) != null) {
                // Split by comma for CSV format
                String[] parts = line.split(",");
                int bedroom = Integer.parseInt(parts[0]);
                boolean hasGarden = Boolean.parseBoolean(parts[1]);
                int propertyId = Integer.parseInt(parts[2]);
                String petFriendlyLevel = parts[3];

                System.out.println(petFriendlyLevel);

                for (Property property : properties) {
                    if (property.getPropertyId() == propertyId) {
                        int ownerId = property.getOwnerId();
                        float pricing = property.getPricing();
                        String address = property.getAddress();
                        String status = property.getStatus();
                        ResidentialProperty residentialProperty = new ResidentialProperty(ownerId, pricing, propertyId, address, status, bedroom, hasGarden, petFriendlyLevel);
                        residentialProperties.add(residentialProperty);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        System.out.println("Residential Properties loaded: " + residentialProperties.size());
        return residentialProperties;
    }

    //Search for properties
    public void searchProperties(ActionEvent event) {
        String search = searchInput.getText();
        ObservableList<Property> searchResults = FXCollections.observableArrayList();
        ObservableList<CommercialProperty> searchResultsCom = FXCollections.observableArrayList();
        ObservableList<ResidentialProperty> searchResultsRe = FXCollections.observableArrayList();
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

            case "Residential Properties" -> {
                for (Property property : properties) {
                    for (ResidentialProperty residentialProperty : residentialProperties) {
                        if (property.getPropertyId() == residentialProperty.getPropertyId() && residentialProperty.getAddress().toLowerCase().contains(search.toLowerCase())) {
                            searchResults.add(residentialProperty);
                        }
                    }
                }

                if (searchResults.isEmpty()) {
                    System.out.println("No results found");
                } else {
                    resPropertyTable.setItems(searchResultsRe);
                    cancelBtn.setVisible(true);
                }
            }
        }
    }

    //Reset the table
    public void resetTable(ActionEvent event) {
        switch (newValue) {
            case "All Properties" -> {
                propertyTable.setItems(properties);
            }
            case "Commercial Properties" -> {
                comPropertyTable.setItems(commercialProperties);
            }
            case "Residential Properties" -> {
                resPropertyTable.setItems(residentialProperties);
            }
        }
        searchInput.clear();
        cancelBtn.setVisible(false);
    }

    //Handle the change in the ChoiceBox
    private void handleChoiceBoxChange(String newValue) {
        boolean showComPropertyTable = false;
        switch (newValue) {
            case "All Properties" -> {
                propertyTable.toFront();
                comPropertyTable.toBack();
                resPropertyTable.toBack();

                propertyTable.setVisible(!showComPropertyTable);
                propertyTable.setManaged(!showComPropertyTable);
                comPropertyTable.setVisible(showComPropertyTable);
                comPropertyTable.setManaged(showComPropertyTable);
                resPropertyTable.setVisible(showComPropertyTable);
                resPropertyTable.setManaged(showComPropertyTable);

                propertyTable.setItems(properties);
                comPropertyTable.setItems(commercialProperties);
                resPropertyTable.setItems(residentialProperties);
                System.out.println("All Properties selected");

            }
            case "Commercial Properties" -> {
                propertyTable.toBack();
                comPropertyTable.toFront();
                resPropertyTable.toBack();

                propertyTable.setVisible(showComPropertyTable);
                propertyTable.setManaged(showComPropertyTable);
                comPropertyTable.setVisible(!showComPropertyTable);
                comPropertyTable.setManaged(!showComPropertyTable);
                resPropertyTable.setVisible(showComPropertyTable);
                resPropertyTable.setManaged(showComPropertyTable);

                propertyTable.setItems(properties);
                comPropertyTable.setItems(commercialProperties);
                resPropertyTable.setItems(residentialProperties);
                System.out.println("Commercial Properties selected");
            }
            case "Residential Properties" -> {
                propertyTable.toBack();
                comPropertyTable.toBack();
                resPropertyTable.toFront();

                propertyTable.setVisible(showComPropertyTable);
                propertyTable.setManaged(showComPropertyTable);
                comPropertyTable.setVisible(showComPropertyTable);
                comPropertyTable.setManaged(showComPropertyTable);
                resPropertyTable.setVisible(!showComPropertyTable);
                resPropertyTable.setManaged(!showComPropertyTable);

                propertyTable.setItems(properties);
                comPropertyTable.setItems(commercialProperties);
                resPropertyTable.setItems(residentialProperties);
                System.out.println("Residential Properties selected");
            }
        }
    }
}

