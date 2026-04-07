/**
 * 
 */
/**
 * 
 */
module patp {
    requires java.sql;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.mail;
    requires jakarta.activation;

    // EXPORTS
    exports view;
    exports model;
    exports dao;
    exports utils;
    exports main;

    // OPEN (para JavaFX reflection)
    opens view to javafx.fxml;
}