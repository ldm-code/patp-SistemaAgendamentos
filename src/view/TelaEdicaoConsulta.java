package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.consultas;
import dao.ConsultaDAO;
import List.Consulta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TelaEdicaoConsulta {

    public void start(Consulta consulta, Runnable onUpdate) {

        Stage janela = new Stage();
        janela.initModality(Modality.APPLICATION_MODAL);

        // ===== TÍTULO =====
        Label titulo = new Label("Editar Consulta");
        titulo.setFont(new Font("Arial", 28));
        titulo.setTextFill(Color.web("#FFD700"));

        // ===== CAMPOS =====
        DatePicker dataConsulta = new DatePicker();

        ComboBox<String> comboHora = new ComboBox<>();
        comboHora.getItems().addAll(
                "08:00","08:30","09:00","09:30","10:00","10:30",
                "11:00","11:30","13:00","13:30","14:00","14:30",
                "15:00","15:30","16:00","16:30","17:00","17:30"
        );
     String estiloDatePicker =
     		"-fx-background-color: #1e1e1e;" +
     				"-fx-control-inner-background: #1e1e1e;" +
     				"-fx-text-fill: white;" +
     				"-fx-prompt-text-fill: #aaaaaa;" +
     				"-fx-background-radius: 10;" +
     				"-fx-border-radius: 10;" +
     				"-fx-padding: 5;";

        // 🔥 PRÉ-CARREGAR
        LocalDateTime atual = consulta.getDataConsulta();
        dataConsulta.setValue(atual.toLocalDate());
        comboHora.setValue(atual.toLocalTime().toString());

        Label feedback = new Label();

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        // ===== SALVAR =====
        comboHora.setStyle(estiloDatePicker);
        dataConsulta.setStyle(estiloDatePicker);
        btnSalvar.setOnAction(e -> {
            try {
                LocalDate data = dataConsulta.getValue();
                String horaStr = comboHora.getValue();

                if (data == null || horaStr == null) {
                    feedback.setText("Preencha todos os campos!");
                    feedback.setTextFill(Color.ORANGE);
                    return;
                }

                LocalTime hora = LocalTime.parse(horaStr);
                LocalDateTime novaData = LocalDateTime.of(data, hora);

                String resultado = consultas.editarConsulta(consulta, novaData);

                feedback.setText(resultado);

                if (resultado.contains("sucesso")) {
                    feedback.setTextFill(Color.LIGHTGREEN);

                    onUpdate.run(); // atualiza tela principal
                    janela.close();
                } else {
                    feedback.setTextFill(Color.RED);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                feedback.setText("Erro ao atualizar!");
                feedback.setTextFill(Color.RED);
            }
        });

        btnCancelar.setOnAction(e -> janela.close());
        
        btnSalvar.setStyle("-fx-background-color: #FFD700; " +
                "-fx-text-fill: black; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 10 20;");
       btnCancelar.setStyle("-fx-background-color: #A9A9A9; " +
                "-fx-text-fill: black; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 10 20;");



        // ===== LAYOUT =====
        VBox layout = new VBox(15, titulo, dataConsulta, comboHora, btnSalvar, btnCancelar, feedback);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: #0f3d2e;");

        Scene scene = new Scene(layout, 400, 300);

        janela.setScene(scene);
        janela.setTitle("Editar Consulta");
        janela.show();
    }

    private boolean confirmar(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(msg);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

}