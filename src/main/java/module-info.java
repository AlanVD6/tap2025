module com.example.tab2025 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tab2025 to javafx.fxml;
    exports com.example.tab2025;
}