package main;

import javafx.application.Application;
import javafx.stage.Stage;
import model.consultas;
import view.LoginUsuario;

// Classe Principal (Main) da Aplicação
public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // 🔹 Inicia sistema de lembretes automáticos
        iniciarLembretes();

        // 🔹 Abre tela de login
        new LoginUsuario().start(stage);
    }

    // 🔹 Thread responsável pelos lembretes
    public void iniciarLembretes() {

        new Thread(() -> {

            while (true) {

                try {

                    // 🔹 Executa verificação de consultas
                    consultas.enviarLembretesConsultas();

                    // 🔹 Espera 1 hora
                    Thread.sleep(3600000);

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

        }).start();
    }

    public static void main(String[] args) {

        // launch() inicia a aplicação JavaFX
        // e chama o método start()
        launch();
    }
}

/*
IMPLEMENTAÇÕES FUTURAS

- implementar filtro por médico.

- implementar email 1 dia antes da consulta.

- melhorar UX dos filtros em agendamentos
  e reagendamento:
  
    • letras mais legíveis
    • descrição acima de cada campo
    • melhor alinhamento visual
*/
// implementar filtro por medico.
// implementar email 1 dia antes da consulta.
// melhorar ux dos filtros em agendamentos e reagendamento,colocando letras mais legiveis e descrever cada campo encima.
