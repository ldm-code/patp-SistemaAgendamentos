package view;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.usuario;;

public class CadastroUsuario extends Application {

	    @Override
	    public void start(Stage stage) {

	        // ===== TÍTULO =====
	        Label titulo = new Label("Agendamentos cotriel");
	        titulo.setFont(new Font("Arial", 28));
	        titulo.setTextFill(Color.web("#FFD700")); 
	        // ===== CAMPOS =====
	        TextField matricula = new TextField();
	        matricula.setPromptText("Matrícula");

	        TextField nome = new TextField();
	        nome.setPromptText("Nome");

	        TextField email = new TextField();
	        email.setPromptText("Email");

	        PasswordField senha = new PasswordField();
	        senha.setPromptText("Senha");
	        
	        TextField cpf = new TextField();
	        cpf.setPromptText("CPF");

	        // ===== BOTÃO =====
	        Button botao = new Button("Cadastrar");
	        Label mensagem = new Label();
	        mensagem.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
	        // Ação do botão
	        botao.setOnAction(e -> {

	            String m = matricula.getText();
	            String n = nome.getText();
	            String em = email.getText();
	            String s = senha.getText();
	            String c = cpf.getText();

	            // limpa cpf
	            c = c.replaceAll("[^\\d]", "").trim();

	            // ===== VALIDAÇÕES =====

	            if (m.isEmpty() || n.isEmpty() || em.isEmpty() || s.isEmpty() || c.isEmpty()) {

	                mensagem.setText("Todos os campos são obrigatórios");
	                mensagem.setStyle("-fx-text-fill: red;");

	            } else if (!em.contains("@") || !em.contains(".")) {

	                mensagem.setText("Email inválido");
	                mensagem.setStyle("-fx-text-fill: red;");

	            } else if (!m.matches("\\d+")) {

	                mensagem.setText("Matrícula inválida");
	                mensagem.setStyle("-fx-text-fill: red;");

	            } else if (c.length() != 11) {

	                mensagem.setText("CPF inválido");
	                mensagem.setStyle("-fx-text-fill: red;");

	            } else if (s.length() < 6) {

	                mensagem.setText("Senha muito curta");
	                mensagem.setStyle("-fx-text-fill: red;");

	            } else {

	                // ✔ passou tudo
	                usuario.cadastrarUsuario(m, n, em, s, c);

	                mensagem.setText("Usuário cadastrado com sucesso!");
	                mensagem.setStyle("-fx-text-fill: #00ff88; -fx-font-weight: bold;");

	                // limpa campos
	                matricula.clear();
	                nome.clear();
	                email.clear();
	                senha.clear();
	                cpf.clear();
	            }
	        });

	        // ===== ESTILIZAÇÃO DOS CAMPOS =====
	        String estiloCampos = "-fx-background-color: #1e1e1e; " +
	                              "-fx-text-fill: white; " +
	                              "-fx-prompt-text-fill: #aaaaaa; " +
	                              "-fx-background-radius: 10; " +
	                              "-fx-padding: 10;";

	        matricula.setStyle(estiloCampos);
	        nome.setStyle(estiloCampos);
	        email.setStyle(estiloCampos);
	        senha.setStyle(estiloCampos);
	        cpf.setStyle(estiloCampos);

	        // ===== ESTILO BOTÃO =====
	        botao.setStyle("-fx-background-color: #FFD700; " +
	                       "-fx-text-fill: black; " +
	                       "-fx-font-weight: bold; " +
	                       "-fx-background-radius: 10; " +
	                       "-fx-padding: 10 20;");

	        // ===== LAYOUT =====
	        VBox layout = new VBox(15);
	        layout.setAlignment(Pos.CENTER);
	        layout.setPadding(new Insets(30));

	        layout.getChildren().addAll(
	                titulo,
	                matricula,
	                nome,
	                email,
	                senha,
	                cpf,
	                botao,
	                mensagem
	        );

	        // ===== FUNDO =====
	        layout.setStyle("-fx-background-color: #0f3d2e;"); 

	        // ===== CENA =====
	        Scene scene = new Scene(layout, 400, 500);

	        stage.setTitle("Tela de Cadastro");
	        stage.setScene(scene);
	        stage.show();
	    }

	    public static void main(String[] args) {
	        launch();
	    }
	}

