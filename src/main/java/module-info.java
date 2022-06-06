module com.example.chess {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.jetbrains.annotations;
    requires jdk.accessibility;


    opens com.example.chess to javafx.fxml;
    exports com.example.chess;
}