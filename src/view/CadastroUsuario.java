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

	        // Ação do botão
	        botao.setOnAction(e -> {
	            usuario.cadastrarUsuario(matricula.getText(),nome.getText() , email.getText(), senha.getText(),cpf.getText());
	            System.out.println("Matrícula: " + matricula.getText());
	            System.out.println("Nome: " + nome.getText());
	            System.out.println("Email: " + email.getText());
	            System.out.println("Senha: " + senha.getText());
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
	                botao
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

