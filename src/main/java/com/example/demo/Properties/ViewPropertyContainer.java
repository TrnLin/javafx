package com.example.demo.Properties;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class ViewPropertyContainer {
    public StackPane contentPane;
    public ChoiceBox viewOptions;

    public void initialize() {

        viewOptions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                switch (newValue) {
                    case "All Properties":
                        loadPage("viewAllProperties.fxml");
                        break;
                    case "Commercial Properties":
                        loadPage("viewComProperties.fxml");
                        break;
                    case "Residential Properties":
                        loadPage("viewResProperties.fxml");
                        break;
                    default:
                        break;
                }

            }
        });
    }

    private void loadPage(String fxmlFile) {
        try {
            // Load the new FXML file into the content pane
            Node page = FXMLLoader.load(getClass().getResource(fxmlFile));
            // Load the CSS if required
            contentPane.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
