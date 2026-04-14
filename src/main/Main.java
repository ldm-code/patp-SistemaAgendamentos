package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginUsuario;

public class Main extends Application {
	/**
	 * Classe Principal (Main) da Aplicação 
	 */
    @Override
    public void start(Stage stage) {
    	// abre tela de login
        new LoginUsuario().start(stage); 
    }

    public static void main(String[] args) {
    	 // launch() inicia a aplicação JavaFX e chama o método start()
        launch();
     
    }
}
