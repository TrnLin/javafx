module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.model;
    exports com.example.demo.stric;
    opens com.example.demo.stric to javafx.fxml;
}