module org.example.sistemaeventos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.sistemaeventos to javafx.fxml;
    opens org.example.sistemaeventos.controller to javafx.fxml;

    exports org.example.sistemaeventos.app;
    exports org.example.sistemaeventos.controller;
    exports org.example.sistemaeventos.model;
    exports org.example.sistemaeventos.dao;
    exports org.example.sistemaeventos.db;
}