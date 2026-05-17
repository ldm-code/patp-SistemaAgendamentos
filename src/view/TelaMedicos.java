package view;

import dao.MedicoDiasDAO;
import dao.medicosDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TelaMedicos {

    public void start(Runnable onUpdate) {

        Stage janela = new Stage();
        janela.initModality(Modality.APPLICATION_MODAL);

        // ===== TÍTULO =====
        Label titulo = new Label("Cadastro de Médicos");
        titulo.setFont(new Font("Arial", 28));
        titulo.setTextFill(Color.web("#FFD700"));

        // ===== CAMPOS =====
        TextField campoNome = new TextField();
        campoNome.setPromptText("Digite o nome");
        campoNome.setMaxWidth(250);
        TextField campoTipo = new TextField();
        campoTipo.setPromptText("Digite o tipo");
        campoTipo.setMaxWidth(250);
        String estiloInput =
                "-fx-background-color: #1e1e1e;" +
                "-fx-control-inner-background: #1e1e1e;" +
                "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: #aaaaaa;" +
                "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;" +
                "-fx-padding: 5;";

        campoNome.setStyle(estiloInput);
        campoTipo.setStyle(estiloInput);

        // ===== DIAS (SEG - SEX) =====
        Label labelDias = new Label("Dias de atendimento:");
        labelDias.setTextFill(Color.WHITE);

        CheckBox seg = new CheckBox("Segunda");
        CheckBox ter = new CheckBox("Terça");
        CheckBox qua = new CheckBox("Quarta");
        CheckBox qui = new CheckBox("Quinta");
        CheckBox sex = new CheckBox("Sexta");

        estilizar(seg);
        estilizar(ter);
        estilizar(qua);
        estilizar(qui);
        estilizar(sex);

        HBox diasBox = new HBox(10, seg, ter, qua, qui, sex);
        diasBox.setAlignment(Pos.CENTER);

        // ===== FEEDBACK =====
        Label feedback = new Label();

        // ===== BOTÕES =====
        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        btnSalvar.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 10 20;");
        btnCancelar.setStyle("-fx-background-color: transparent; -fx-text-fill: #FFD700; -fx-underline: true;");

        // ===== AÇÃO SALVAR =====
        btnSalvar.setOnAction(e -> {

            String nome = campoNome.getText();
            String tipo = campoTipo.getText();

            if (nome.isEmpty() || tipo.isEmpty()) {
                feedback.setText("Preencha todos os campos!");
                feedback.setTextFill(Color.ORANGE);
                return;
            }

            try {

                // 1. coleta dias selecionados
                List<String> diasSelecionados = new ArrayList<>();

                if (seg.isSelected()) diasSelecionados.add("SEGUNDA");
                if (ter.isSelected()) diasSelecionados.add("TERCA");
                if (qua.isSelected()) diasSelecionados.add("QUARTA");
                if (qui.isSelected()) diasSelecionados.add("QUINTA");
                if (sex.isSelected()) diasSelecionados.add("SEXTA");

                if (diasSelecionados.isEmpty()) {
                    feedback.setText("Selecione pelo menos um dia!");
                    feedback.setTextFill(Color.ORANGE);
                    return;
                }

                // 2. cadastra médico e pega ID
                int medicoId = medicosDAO.inserir(nome, tipo);

                // 3. salva dias no banco
                for (String dia : diasSelecionados) {
                    MedicoDiasDAO.inserirDia(medicoId, dia);
                }

                feedback.setText("Médico cadastrado com sucesso!");
                feedback.setTextFill(Color.LIGHTGREEN);

                onUpdate.run();
                janela.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                feedback.setText("Erro ao cadastrar médico!");
                feedback.setTextFill(Color.RED);
            }
        });

        btnCancelar.setOnAction(e -> janela.close());

        // ===== LAYOUT =====
        VBox layout = new VBox(15,
                titulo,
                campoNome,
                campoTipo,
                labelDias,
                diasBox,
                btnSalvar,
                btnCancelar,
                feedback
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: #0f3d2e;");

        Scene scene = new Scene(layout, 500, 380);

        janela.setScene(scene);
        janela.setTitle("Cadastro de Médicos");
        janela.show();
       
    }

    private void estilizar(CheckBox cb) {
        cb.setTextFill(Color.WHITE);
    }
}