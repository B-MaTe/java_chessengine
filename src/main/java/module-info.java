module com.example.chess {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.chess to javafx.fxml;
    exports com.example.chess;
}