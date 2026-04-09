package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginUsuario;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        new LoginUsuario().start(stage); 
    }

    public static void main(String[] args) {
        launch();
    }
}
