package main;
import javafx.application.Application;
import javafx.stage.Stage;
import model.consultas;
import view.LoginUsuario;
import dao.conexaoBanco;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {

    private static final ExecutorService executor =
            Executors.newSingleThreadExecutor();

    @Override
    public void start(Stage stage) {

      
        try {
            conexaoBanco.fecharTudo();
        } catch (Exception e) {
            System.out.println("Sem conexões antigas para fechar ou erro ao limpar.");
        }

        // 🔹 inicia lembretes de forma controlada
        iniciarLembretes();

        // 🔹 abre login
        new LoginUsuario().start(stage);
    }

    public void iniciarLembretes() {

        executor.submit(() -> {

            while (true) {

                try {

                    consultas.enviarLembretesConsultas();

                    Thread.sleep(3600000); // 1 hora

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}