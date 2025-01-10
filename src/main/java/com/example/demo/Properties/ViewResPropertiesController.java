package com.example.demo.Properties;

import com.example.demo.model.Property;
import com.example.demo.model.ResidentialProperty;
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

public class ViewResPropertiesController {
    //Toggle Groups
    public final ToggleGroup statusToggleGroup = new ToggleGroup();
    public final ToggleGroup petFriendlyToggleGroup = new ToggleGroup();

    public TableView<ResidentialProperty> resPropertyTable;
    public Button filterBtn;
    public Button cancelFilterBtn;
    public TextField searchInput;
    public Button searchBtn;
    public Button cancelBtn;
    public TableView<Property> propertyTable;
    public RadioButton statusAvailable;
    public RadioButton statusRented;
    public Button resetFilterBtn;
    public RadioButton statusUnderMaintenance;
    public RadioButton statusAll;

    // Residential Property Filter
    public RadioButton petHigh;
    public RadioButton petMedium;
    public RadioButton petLow;
    public TextField minBedroom;
    public TextField maxBedroom;
    public CheckBox hasGardenCheck;
    public RadioButton petNone;

    ObservableList<ResidentialProperty> residentialProperties = FXCollections.observableArrayList();
    ObservableList<Property> properties = FXCollections.observableArrayList();
    ObservableList<ResidentialProperty> filteredProperties = FXCollections.observableArrayList();

    public void initialize() {
        //set up the status radio buttons
        statusAll.setToggleGroup(statusToggleGroup);
        statusAvailable.setToggleGroup(statusToggleGroup);
        statusRented.setToggleGroup(statusToggleGroup);
        statusUnderMaintenance.setToggleGroup(statusToggleGroup);

        // Set user data for radio buttons
        statusAll.setUserData("ALL");
        statusAvailable.setUserData("AVAILABLE");
        statusRented.setUserData("RENTED");
        statusUnderMaintenance.setUserData("UNDER_MAINTENANCE");

        //set default view option
        statusToggleGroup.selectToggle(statusAll);

        // Set up the pet friendly radio buttons
        petNone.setToggleGroup(petFriendlyToggleGroup);
        petHigh.setToggleGroup(petFriendlyToggleGroup);
        petMedium.setToggleGroup(petFriendlyToggleGroup);
        petLow.setToggleGroup(petFriendlyToggleGroup);

        // Set user data for radio buttons
        petNone.setUserData("NONE");
        petHigh.setUserData("HIGH");
        petMedium.setUserData("MEDIUM");
        petLow.setUserData("LOW");

        // Set default view option
        petFriendlyToggleGroup.selectToggle(petNone);

        // Add listener to search button
        searchInput.setOnAction(event -> searchProperties(new ActionEvent()));

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

        resPropertyTable.getColumns().addAll(rePropertyIdCol, reOwnerIdCol, rePricingCol, reAddressCol, reStatusCol, reBedroomCol, reHasGardenCol, rePetFriendlyLevelCol);

        properties = readPropertiesFromFileProperty();
        residentialProperties = readPropertiesFromFileResidential();

        resPropertyTable.setItems(residentialProperties);
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

    public void searchProperties(ActionEvent event) {
        String search = searchInput.getText().toLowerCase();
        ObservableList<ResidentialProperty> searchResults = FXCollections.observableArrayList();
        for (ResidentialProperty property : residentialProperties) {
            if (property.getAddress().toLowerCase().contains(search)) {
                searchResults.add(property);
            }
        }
        resPropertyTable.setItems(searchResults);
        cancelBtn.setVisible(true);
    }

    public void resetTable(ActionEvent event) {
        resPropertyTable.setItems(residentialProperties);
        searchInput.clear();
        cancelBtn.setVisible(false);
    }

    public void getStatusFilter(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) statusToggleGroup.getSelectedToggle();
        String status = (String) selectedRadioButton.getUserData();
        filterProperties(status);
    }

    public void filterProperties(String status) {
        ObservableList<ResidentialProperty> filteredProperties = FXCollections.observableArrayList();
        for (ResidentialProperty property : residentialProperties) {
            if (property.getStatus().equals(status)) {
                filteredProperties.add(property);
            }
        }
        resPropertyTable.setItems(filteredProperties);
        resetFilterBtn.setVisible(true);
    }

    public void resetFilter(ActionEvent event) {
        resPropertyTable.setItems(residentialProperties);
        resetFilterBtn.setVisible(false);
    }

    public void cancelFilterBtnClicked(ActionEvent event) {
    }

    public void filterBtnClicked(ActionEvent event) {
        ObservableList<ResidentialProperty> filteredProperties = FXCollections.observableArrayList();

        String status = getStatus() != null ? getStatus() : "ALL";
        String petFriendlyLevel = getPetFriendlyLevel() != null ? getPetFriendlyLevel() : "NONE";
        int minBedroom = this.minBedroom.getText().isEmpty() ? 0 : Integer.parseInt(this.minBedroom.getText());
        int maxBedroom = this.maxBedroom.getText().isEmpty() ? 100 : Integer.parseInt(this.maxBedroom.getText());
        boolean hasGarden = hasGardenCheck.isSelected();

        for (ResidentialProperty property : residentialProperties) {
            boolean matchesStatus = status.equals("ALL") || property.getStatus().equals(status);
            boolean matchesPetFriendlyLevel = petFriendlyLevel.equals("NONE") || property.getPetFriendlyLevel().equals(petFriendlyLevel);
            boolean matchesBedroom = property.getBedrooms() >= minBedroom && property.getBedrooms() <= maxBedroom;
            boolean matchesGarden = !hasGarden || property.isHasGarden();

            if (matchesStatus && matchesPetFriendlyLevel && matchesBedroom && matchesGarden) {
                filteredProperties.add(property);
            }
        }

        resPropertyTable.setItems(filteredProperties);
        cancelFilterBtn.setVisible(true);
    }

    public void cancelBtnClicked(ActionEvent event) {
        resPropertyTable.setItems(residentialProperties);
        cancelFilterBtn.setVisible(false);
        hasGardenCheck.setSelected(false);
        minBedroom.clear();
        maxBedroom.clear();
        petFriendlyToggleGroup.selectToggle(petNone);
        statusToggleGroup.selectToggle(statusAll);
    }


    public String getStatus() {
        RadioButton selectedRadioButton = (RadioButton) statusToggleGroup.getSelectedToggle();
        return (String) selectedRadioButton.getUserData();
    }

    public String getPetFriendlyLevel() {
        RadioButton selectedRadioButton = (RadioButton) petFriendlyToggleGroup.getSelectedToggle();
        return (String) selectedRadioButton.getUserData();
    }
}
