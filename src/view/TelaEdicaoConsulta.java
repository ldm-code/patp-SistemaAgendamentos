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
import utils.AtualizadorHorarios;
import dao.ConsultaDAO;
import dao.MedicoDiasDAO;
import List.Consulta;
import List.MedicosSelect;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TelaEdicaoConsulta {

    public void start(Consulta consulta, Runnable onUpdate) throws Exception {

        Stage janela = new Stage();
        janela.initModality(Modality.APPLICATION_MODAL);

        // ===== TÍTULO =====
        Label titulo = new Label("Editar Consulta");
        titulo.setFont(new Font("Arial", 28));
        titulo.setTextFill(Color.web("#FFD700"));

        // ===== CAMPOS =====
        DatePicker dataConsulta = new DatePicker();
        final List<String> diasMedico =
                MedicoDiasDAO.buscarDias(consulta.getIdMedico());

      
        dataConsulta.setDayCellFactory(picker -> new DateCell() {

            @Override
            public void updateItem(LocalDate data, boolean empty) {

                super.updateItem(data, empty);

                // 🔥 reset obrigatório
                setDisable(false);
                setStyle("");

                // 🔴 células vazias
                if(empty || data == null) {

                    setText(null);
                    setGraphic(null);

                    return;
                }

                // 🔴 datas passadas
                if(data.isBefore(LocalDate.now())) {

                    setDisable(true);

                    setStyle(
                        "-fx-background-color: #3a3a3a;"
                    );

                    return;
                }

                // 🔥 dia da semana
                int diaSemana =
                        data.getDayOfWeek().getValue();

                boolean atende = false;

                // 🔥 verifica em memória
                for(String dia : diasMedico) {

                    int diaBanco =
                            utils.ValidacaoMedicoUtils
                            .converterDia(dia);

                    if(diaBanco == diaSemana) {

                        atende = true;
                        break;
                    }
                }

                // 🔴 médico não atende
                if(!atende) {

                    setDisable(true);

                    setStyle(
                        "-fx-background-color: #2b2b2b;"
                    );

                } else {

                    // 🟢 estilo normal
                    setStyle(
                        "-fx-background-color: #1e1e1e;" +
                        "-fx-text-fill: white;"
                    );
                }
            }
        });
        ComboBox<String> comboHora = new ComboBox<>();
        comboHora.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {

                super.updateItem(item, empty);

                if(empty || item == null) {
                    setText("Selecione a hora");
                    setTextFill(Color.WHITE);
                } else {
                    setText(item);
                    setTextFill(Color.WHITE);
                }
            }
        });
        comboHora.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {

                super.updateItem(item, empty);

                if(empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setTextFill(Color.WHITE);
                }

                setStyle("-fx-background-color: #1e1e1e;");
            }
        });
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
        AtualizadorHorarios.atualizarEdicao(
                comboHora,
                consulta.getIdMedico(),
                atual.toLocalDate(),
                atual.toLocalTime().toString()
        );
        dataConsulta.setOnAction(e -> {

            AtualizadorHorarios.atualizarEdicao(
                    comboHora,
                    consulta.getIdMedico(),
                    dataConsulta.getValue(),
                    atual.toLocalTime().toString()
            );
        });
    

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
                    mostrarAlert(
                        "Atenção",
                        "Preencha todos os campos!",
                        Alert.AlertType.WARNING
                    );
                    return;
                }

                LocalTime hora = LocalTime.parse(horaStr);
                LocalDateTime novaData = LocalDateTime.of(data, hora);

                String resultado = consultas.editarConsulta(consulta, novaData);

                if (resultado.contains("sucesso")) {

                    mostrarAlert(
                        "Sucesso",
                        "Consulta atualizada com sucesso!",
                        Alert.AlertType.INFORMATION
                    );

                    onUpdate.run();
                    janela.close();

                } else {

                    mostrarAlert(
                        "Erro",
                        resultado,
                        Alert.AlertType.ERROR
                    );
                }

            } catch (Exception ex) {
                ex.printStackTrace();

                mostrarAlert(
                    "Erro",
                    "Erro ao atualizar consulta!",
                    Alert.AlertType.ERROR
                );
            }
        });
        btnCancelar.setOnAction(e -> janela.close());
        
        btnSalvar.setStyle("-fx-background-color: #FFD700; " +
                "-fx-text-fill: black; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 10 20;");
       btnCancelar.setStyle("-fx-background-color: transparent; " +
               "-fx-text-fill: #FFD700; " +
               "-fx-underline: true;");



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
    private void mostrarAlert(String titulo, String msg, Alert.AlertType tipo) {

        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);

        // deixa ele mais leve (não bloqueia tanto visualmente)
        alert.show();
    }

}