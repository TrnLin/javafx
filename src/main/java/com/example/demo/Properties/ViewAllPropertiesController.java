package com.example.demo.Properties;

import com.example.demo.ComponentsController.PropertyInfoController;
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

public class ViewAllPropertiesController {
    public final ToggleGroup statusToggleGroup = new ToggleGroup();
    public TextField searchInput;
    public Button searchBtn;
    public Button cancelBtn;
    public TableView<Property> propertyTable;
    public RadioButton statusAvailable;
    public RadioButton statusRented;
    public Button resetFilterBtn;
    public RadioButton statusUnderMaintenance;
    public RadioButton statusAll;

    ObservableList<Property> properties = FXCollections.observableArrayList();

    private static Property getProperty(String line) {
        String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        int ownerId = Integer.parseInt(parts[0]);
        float pricing = Float.parseFloat(parts[1]);
        int propertyId = Integer.parseInt(parts[2]);
        String address = parts[3].replace("\"", "");
        String status = parts[4];

//                System.out.println("Owner ID: " + ownerId + ", Pricing: " + pricing + ", Property ID: " + propertyId + ", Address: " + address + ", Status: " + status);

        // Create a new Property object
        Property property = new Property(ownerId, pricing, propertyId, address, status);
        return property;
    }

    // Initialize the controller
    public void initialize() {
        System.out.println("Visitor Controller Initialized");
        // Add listener to search button
        searchInput.setOnAction(event -> searchProperties(new ActionEvent()));

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

        //set default radio button
        statusToggleGroup.selectToggle(statusAll);

        // Add listener to search button
        searchInput.setOnAction(event -> searchProperties(new ActionEvent()));

        // Add listener to ChoiceBox


        // Add listener to TableView
        propertyTable.setRowFactory(tv -> {
            TableRow<Property> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    Property clickedProperty = row.getItem();
                    showPropertyInfo(clickedProperty);
                }
            });
            return row;
        });


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
                Property property = getProperty(line);
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

    public void filterBtnClicked(ActionEvent event) {
        ObservableList<Property> filteredProperties = FXCollections.observableArrayList();

        String status = getStatus() != null ? getStatus() : "";

        switch (status) {
            case "AVAILABLE":
                for (Property property : properties) {
                    if (property.getStatus().equals("AVAILABLE")) {
                        filteredProperties.add(property);
                    }
                }
                break;
            case "RENTED":
                for (Property property : properties) {
                    if (property.getStatus().equals("RENTED")) {
                        filteredProperties.add(property);
                    }
                }
                break;
            case "UNDER_MAINTENANCE":
                for (Property property : properties) {
                    if (property.getStatus().equals("UNDER_MAINTENANCE")) {
                        filteredProperties.add(property);
                    }
                }
                break;
            default:
                filteredProperties = properties;
                break;
        }

        propertyTable.setItems(filteredProperties);
    }

    public String getStatus() {
        RadioButton selectedRadioButton = (RadioButton) statusToggleGroup.getSelectedToggle();
        return (String) selectedRadioButton.getUserData();
    }

    public void showPropertyInfo(Property property) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Components/propertyInfo.fxml"));
            Parent root = loader.load();

            // Get the controller of propertyInfo.fxml
            PropertyInfoController controller = loader.getController();

            // Pass the property data to the controller
            controller.setProperty(property);

            Stage stage = new Stage();
            stage.setTitle("Property Information");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


