module com.example {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example to javafx.fxml;
    opens com.example.features.main to javafx.fxml;
    opens com.example.features.sidebar to javafx.fxml;
    opens com.example.features.monitoring to javafx.fxml;
    // opens com.example.features.pesanan to javafx.fxml;
    exports com.example;
}
