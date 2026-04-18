package view;

import List.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.usuario;
import utils.SessaoUsuario;

public class LoginUsuario {

    public void start(Stage stage) {

        // ===== TÍTULO =====
        Label titulo = new Label("Sistema de Agendamentos - Cotriel");
        titulo.setFont(new Font("Arial", 28));
        titulo.setTextFill(Color.web("#FFD700"));

        // ===== CAMPOS =====
        TextField email = new TextField();
        email.setPromptText("Email");

        PasswordField senha = new PasswordField();
        senha.setPromptText("Senha (minimo 6 digitos)");
        TextField senhaVisivel = new TextField(); 
        senhaVisivel.setPromptText("Senha (minimo 6 digitos)");
        
        senhaVisivel.setVisible(false);
        senhaVisivel.setManaged(false);

        // ===== BOTÕES =====
        Button olho = new Button("👁");
        olho.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-cursor: hand;" +
                "-fx-font-size: 14;"
        );
        Button botaoLogin = new Button("Login");
        Button botaoCadastro = new Button("Não sou cadastrado");

        // ===== AÇÕES =====
        olho.setOnAction(e -> {
            if (senhaVisivel.isVisible()) {
                senha.setText(senhaVisivel.getText());

                senha.setVisible(true);
                senha.setManaged(true);

                senhaVisivel.setVisible(false);
                senhaVisivel.setManaged(false);
            } else {
                senhaVisivel.setText(senha.getText());

                senhaVisivel.setVisible(true);
                senhaVisivel.setManaged(true);

                senha.setVisible(false);
                senha.setManaged(false);
            }
        });
        Label mensagem = new Label();
        mensagem.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
         
        	botaoLogin.setOnAction(e -> {

        	    String emailDigitado = email.getText();
        	    String senhaDigitada;
        	    if (senhaVisivel.isVisible()) {
        	        senhaDigitada = senhaVisivel.getText();
        	    } else {
        	        senhaDigitada = senha.getText();
        	    }

        	    // 🔹 valida campos vazios
        	    if (emailDigitado.isEmpty() || senhaDigitada.isEmpty()) {
        	        mensagem.setText("Preencha todos os campos!");
        	        mensagem.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
        	        return;
        	    } else if (!emailDigitado.contains("@")) {
        	    	mensagem.setText("email invalido!");
           	        mensagem.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
           	        return;
        	    	
        	    }

        	    Usuario valido = usuario.validarLogin(emailDigitado, senhaDigitada);
        	    SessaoUsuario.usuarioLogado = valido;

        	    if (valido !=null) {

        	        mensagem.setText("Login realizado com sucesso!");
        	        mensagem.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");

        	        // opcional: trocar de tela
        	        if (SessaoUsuario.usuarioLogado.getTipo().equals("adm")) {
        	            new TelaConsultas().start(stage);
        	        	
        	        }
        	        else {
        	        	new TelaConsultasUser().start(stage);
        	        }
        	        email.clear();
        	        senha.clear();

        	    } else {

        	        mensagem.setText("Email ou senha incorretos!");
        	        mensagem.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        	    }
        	});
        	  StackPane campoSenha = new StackPane();
              campoSenha.getChildren().addAll(senha, senhaVisivel, olho);

              StackPane.setAlignment(olho, Pos.CENTER_RIGHT);
              StackPane.setMargin(olho, new Insets(0, 10, 0, 0));
 
      

        botaoCadastro.setOnAction(e -> {
            new CadastroUsuario().start(stage); // troca de tela
        });

        // ===== ESTILO DOS CAMPOS =====
        String estiloCampos = "-fx-background-color: #1e1e1e; " +
                              "-fx-text-fill: white; " +
                              "-fx-prompt-text-fill: #aaaaaa; " +
                              "-fx-background-radius: 10; " +
                              "-fx-padding: 10;";

        // padding maior para caber o olho
        senha.setStyle(estiloCampos + "-fx-padding: 10 35 10 10;");
        senhaVisivel.setStyle(estiloCampos + "-fx-padding: 10 35 10 10;");
        email.setStyle(estiloCampos);
        senha.setStyle(estiloCampos);
        campoSenha.setMaxWidth(300);
        email.setMaxWidth(300);

        // ===== ESTILO BOTÕES =====
        botaoLogin.setStyle("-fx-background-color: #FFD700; " +
                            "-fx-text-fill: black; " +
                            "-fx-font-weight: bold; " +
                            "-fx-background-radius: 10; " +
                            "-fx-padding: 10 20;");

        botaoCadastro.setStyle("-fx-background-color: transparent; " +
                               "-fx-text-fill: #FFD700; " +
                               "-fx-underline: true;");

        // ===== RESPONSIVIDADE DOS BOTÕES =====
        botaoLogin.setMaxWidth(Double.MAX_VALUE);
        botaoCadastro.setMaxWidth(Double.MAX_VALUE);
        botaoLogin.setMaxWidth(300);

        VBox boxBotoes = new VBox(10);
        boxBotoes.setAlignment(Pos.CENTER);
        boxBotoes.setFillWidth(true);
        boxBotoes.getChildren().addAll(botaoLogin, botaoCadastro);

        // ===== LAYOUT PRINCIPAL =====
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        layout.getChildren().addAll(
                titulo,
                email,
                campoSenha,
                boxBotoes,
                mensagem
        );

        // ===== FUNDO =====
        layout.setStyle("-fx-background-color: #0f3d2e;");

        // ===== CENA =====
        Scene scene = new Scene(layout, 1366, 700);

        stage.setTitle("Tela de Login");
        stage.setScene(scene);
        stage.show();
    }
}