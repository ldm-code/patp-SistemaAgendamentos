package imagensPATP;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MotivoTelaCancelamento {

    public void start(String valorAtual, Runnable onUpdate) {

        Stage janela = new Stage();
        janela.initModality(Modality.APPLICATION_MODAL);

        // ===== TÍTULO =====
        Label titulo = new Label("Editar Dado");
        titulo.setFont(new Font("Arial", 28));
        titulo.setTextFill(Color.web("#FFD700"));

        // ===== CAMPO ÚNICO =====
        TextField campo = new TextField();
        campo.setPromptText("Digite o valor");

        String estiloInput =
                "-fx-background-color: #1e1e1e;" +
                "-fx-control-inner-background: #1e1e1e;" +
                "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: #aaaaaa;" +
                "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;" +
                "-fx-padding: 5;";

        campo.setStyle(estiloInput);
        campo.applyCss();

        // PRÉ-CARREGAR
        campo.setText(valorAtual);

        Label feedback = new Label();

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        btnSalvar.setOnAction(e -> {
            String valor = campo.getText();

            if (valor.isEmpty()) {
                feedback.setText("Preencha o campo!");
                feedback.setTextFill(Color.ORANGE);
                return;
            }

            feedback.setText("Atualizado com sucesso!");
            feedback.setTextFill(Color.LIGHTGREEN);

            onUpdate.run();
            janela.close();
        });

        btnCancelar.setOnAction(e -> janela.close());

        // ===== ESTILO BOTÕES =====
        btnSalvar.setStyle("-fx-background-color: #FFD700; " +
                "-fx-text-fill: black; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 10 20;");

        btnCancelar.setStyle("-fx-background-color: transparent; " +
                "-fx-text-fill: #FFD700; " +
                "-fx-underline: true;");

        // ===== LAYOUT =====
        VBox layout = new VBox(15, titulo, campo, btnSalvar, btnCancelar, feedback);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        layout.setStyle("-fx-background-color: #0f3d2e;");

        Scene scene = new Scene(layout, 400, 300);

        janela.setScene(scene);
        janela.setTitle("Editar Dado");
        janela.show();
    }
}