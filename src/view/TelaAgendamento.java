package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.consultas;
import dao.UsuarioDAO;
import dao.medicosDAO;

import List.Usuario;
import List.MedicosSelect;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class TelaAgendamento {

    public void start(Stage stage) {

        // ===== TÍTULO =====
        Label titulo = new Label("Agendar Consulta");
        titulo.setFont(new Font("Arial", 28));
        titulo.setTextFill(Color.web("#FFD700"));

        // ===== CAMPOS =====
        TextField campoEmail = new TextField();
        campoEmail.setPromptText("Email do paciente");

        ComboBox<MedicosSelect> selectMedico = new ComboBox<>();
        selectMedico.setPromptText("Selecione um médico");

        DatePicker dataConsulta = new DatePicker();

        ComboBox<String> comboHora = new ComboBox<>();
        comboHora.setPromptText("Horário");

        comboHora.getItems().addAll(
                "08:00","08:30", "09:00","09:30", "10:00","10:30", "11:00","11:30",
                "13:00","13:30", "14:00","14:30", "15:00","15:30", "16:00","16:30",
                "17:00","17:30"
        );

        // ===== CARREGAR MÉDICOS =====
        try {
            List<MedicosSelect> medicos = medicosDAO.select();
            selectMedico.getItems().addAll(medicos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mostrar nome do médico
        selectMedico.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(MedicosSelect m, boolean empty) {
                super.updateItem(m, empty);
                setText(empty || m == null ? "" : m.getNome() + " - " + m.getTipo());
            }
        });

        selectMedico.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(MedicosSelect m, boolean empty) {
                super.updateItem(m, empty);
                setText(empty || m == null ? "" : m.getNome());
            }
        });

        // ===== FEEDBACK =====
        Label feedback = new Label();

        // ===== BOTÕES =====
        Button btnAgendar = new Button("Agendar");
        Button btnVoltar = new Button("Voltar");

        // ===== AÇÃO AGENDAR =====
        btnAgendar.setOnAction(e -> {
            try {
                String email = campoEmail.getText();
                MedicosSelect medico = selectMedico.getValue();
                LocalDate data = dataConsulta.getValue();
                String horaStr = comboHora.getValue();

                // validação
                if (email.isEmpty() || medico == null || data == null || horaStr == null) {
                    feedback.setText("Preencha todos os campos!");
                    feedback.setTextFill(Color.ORANGE);
                    return;
                }

                Usuario usuario = UsuarioDAO.buscarPorEmail(email);

                if (usuario == null) {
                    feedback.setText("Usuário não encontrado!");
                    feedback.setTextFill(Color.RED);
                    return;
                }
              

                // 🔥 CONVERSÃO FINAL
                LocalTime hora = LocalTime.parse(horaStr);
                LocalDateTime dataCompleta = LocalDateTime.of(data, hora);

                String resultado = consultas.cadastrarConsultas(
                        usuario.getId(),
                        medico.getId(),
                        dataCompleta,
                        email
                );

                feedback.setText(resultado);

                if (resultado.contains("sucesso")) {
                    feedback.setTextFill(Color.LIGHTGREEN);
                    new TelaConsultas().start(stage);
                    TelaConsultas.condicionarExibicao();
                } else {
                    feedback.setTextFill(Color.RED);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                feedback.setText("Erro ao agendar!");
                feedback.setTextFill(Color.RED);
            }
        });
        
        dataConsulta.setPromptText("Dia da consulta:");

        // ===== AÇÃO VOLTAR =====
        btnVoltar.setOnAction(e -> {
            new TelaConsultas().start(stage);
            TelaConsultas.condicionarExibicao();
        });

        // ===== ESTILO CAMPOS (igual login) =====
        String estiloCampos =
                "-fx-background-color: #1e1e1e;" +
                "-fx-control-inner-background: #1e1e1e;" +
                "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: #aaaaaa;" +
                "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;" +
                "-fx-padding: 8;";
        String estiloDatePicker =
        		"-fx-background-color: #1e1e1e;" +
        				"-fx-control-inner-background: #1e1e1e;" +
        				"-fx-text-fill: white;" +
        				"-fx-prompt-text-fill: #aaaaaa;" +
        				"-fx-background-radius: 10;" +
        				"-fx-border-radius: 10;" +
        				"-fx-padding: 5;";
        campoEmail.setStyle(estiloCampos);
        selectMedico.setStyle(estiloCampos  +   "-fx-control-inner-background: #1e1e1e;" );

        dataConsulta.setStyle(estiloDatePicker);

        // 🔥 ESSENCIAL: estilizar o campo interno (editor)
        dataConsulta.getEditor().setStyle(
                "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: #aaaaaa;"
        );
        comboHora.setStyle(  
        		"-fx-background-color: #1e1e1e;" +
        	    "-fx-control-inner-background: #1e1e1e;" +
        	    "-fx-text-inner-color: white;" +
        	    "-fx-prompt-text-fill: #aaaaaa;" +   // 🔥 aqui resolve o invisível
        	    "-fx-border-color: #FFD700;" +
        	    "-fx-border-radius: 10;" +
        	    "-fx-background-radius: 10;" +
        	    "-fx-padding: 5;");

        // ===== ESTILO BOTÕES =====
        btnAgendar.setStyle("-fx-background-color: #FFD700; " +
                            "-fx-text-fill: black; " +
                            "-fx-font-weight: bold; " +
                            "-fx-background-radius: 10; " +
                            "-fx-padding: 10 20;");

        btnVoltar.setStyle("-fx-background-color: transparent; " +
                           "-fx-text-fill: #FFD700; " +
                           "-fx-underline: true;");

        btnAgendar.setMaxWidth(Double.MAX_VALUE);
        btnVoltar.setMaxWidth(Double.MAX_VALUE);
        campoEmail.setMaxWidth(300);
        btnAgendar.setMaxWidth(300);
        selectMedico.setMaxWidth(300);
        dataConsulta.setMaxWidth(300);
        comboHora.setMaxWidth(300);

        // ===== LAYOUT =====
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        layout.getChildren().addAll(
                titulo,
                campoEmail,
                selectMedico,
                dataConsulta,
                comboHora,
                btnAgendar,
                btnVoltar,
                feedback
        );

        layout.setStyle("-fx-background-color: #0f3d2e;");

        Scene scene = new Scene(layout, 1366, 700);
        stage.setTitle("Agendamento");
        stage.setScene(scene);
        stage.show();
    }
}