module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.model;
    exports com.example.demo.Properties;
    opens com.example.demo.Properties to javafx.fxml;
    exports com.example.demo.Auth;
    opens com.example.demo.Auth to javafx.fxml;
    exports com.example.demo.Auth.Observer;
    opens com.example.demo.Auth.Observer to javafx.fxml;
    exports com.example.demo.ComponentsController;
    opens com.example.demo.ComponentsController to javafx.fxml;

}