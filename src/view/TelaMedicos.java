package view;
import model.medicos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TelaMedicos {



	    public void start( Runnable onUpdate) {

	        Stage janela = new Stage();
	        janela.initModality(Modality.APPLICATION_MODAL);

	        // ===== TÍTULO =====
	        Label titulo = new Label("Editar Dados");
	        titulo.setFont(new Font("Arial", 28));
	        titulo.setTextFill(Color.web("#FFD700"));

	        // ===== CAMPOS =====
	        TextField campoNome = new TextField();
	        campoNome.setPromptText("Digite o nome");

	        TextField campoTipo = new TextField();
	        campoTipo.setPromptText("Digite o tipo");

	        // 🔥 ESTILO (mesmo do DatePicker)
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

	        // 🔥 PRÉ-CARREGAR
	        campoNome.setPromptText("nome do medico");
	        campoTipo.setPromptText("Tipo/especialidade do medico");

	        Label feedback = new Label();

	        Button btnSalvar = new Button("Salvar");
	        Button btnCancelar = new Button("Cancelar");

	        // ===== SALVAR =====
	        btnSalvar.setOnAction(e -> {
	            String nome = campoNome.getText();
	            String tipo = campoTipo.getText();

	            if (nome.isEmpty() || tipo.isEmpty()) {
	                feedback.setText("Preencha todos os campos!");
	                feedback.setTextFill(Color.ORANGE);
	                return;
	            }

	            // Aqui tu pode chamar teu método de atualização
	            // Ex: consultas.editar(...)

	            feedback.setText("Atualizado com sucesso!");
	            feedback.setTextFill(Color.LIGHTGREEN);
	            medicos.cadastrarMedico(nome, tipo);

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
	        VBox layout = new VBox(15, titulo, campoNome, campoTipo, btnSalvar, btnCancelar, feedback);
	        layout.setAlignment(Pos.CENTER);
	        layout.setPadding(new Insets(30));
	        layout.setStyle("-fx-background-color: #0f3d2e;");

	        Scene scene = new Scene(layout, 400, 300);

	        janela.setScene(scene);
	        janela.setTitle("Editar Dados");
	        janela.show();
	    }
	
}
