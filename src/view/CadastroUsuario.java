package view;

import List.Usuario;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.usuario;
import utils.SessaoUsuario;

public class CadastroUsuario extends Application {

    @Override
    public void start(Stage stage) {

        // ===== TÍTULO =====
        Label titulo = new Label("Agendamentos cotriel");
        titulo.setFont(new Font("Arial", 28));
        titulo.setTextFill(Color.web("#FFD700"));

        // ===== ESTILO PADRÃO =====
        String estiloCampos = "-fx-background-color: #1e1e1e; " +
                              "-fx-text-fill: white; " +
                              "-fx-prompt-text-fill: #aaaaaa; " +
                              "-fx-background-radius: 10; " +
                              "-fx-padding: 10;";

        // ===== CAMPOS =====
        TextField matricula = new TextField();
        matricula.setPromptText("Matrícula");
        matricula.setStyle(estiloCampos);

        TextField nome = new TextField();
        nome.setPromptText("Nome");
        nome.setStyle(estiloCampos);

        TextField email = new TextField();
        email.setPromptText("Email");
        email.setStyle(estiloCampos);

        TextField cpf = new TextField();
        cpf.setPromptText("CPF (insira 11 numeros)");
        cpf.setStyle(estiloCampos);

        // ===== SENHA COM OLHO =====
        PasswordField senha = new PasswordField();
        senha.setPromptText("Senha (minimo 6 digitos)");

        TextField senhaVisivel = new TextField();
        senhaVisivel.setPromptText("Senha (minimo 6 digitos)");

        senhaVisivel.setVisible(false);
        senhaVisivel.setManaged(false);

        // padding maior para caber o olho
        senha.setStyle(estiloCampos + "-fx-padding: 10 35 10 10;");
        senhaVisivel.setStyle(estiloCampos + "-fx-padding: 10 35 10 10;");

        Button olho = new Button("👁");
        olho.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-cursor: hand;" +
                "-fx-font-size: 14;"
        );

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

        // STACKPANE (campo + olho dentro)
        StackPane campoSenha = new StackPane();
        campoSenha.getChildren().addAll(senha, senhaVisivel, olho);

        StackPane.setAlignment(olho, Pos.CENTER_RIGHT);
        StackPane.setMargin(olho, new Insets(0, 10, 0, 0));

        // ===== BOTÃO =====
        Button botao = new Button("Cadastrar");
        botao.setStyle("-fx-background-color: #FFD700; " +
                       "-fx-text-fill: black; " +
                       "-fx-font-weight: bold; " +
                       "-fx-background-radius: 10; " +
                       "-fx-padding: 10 20;");

        Label mensagem = new Label();
        mensagem.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        // ===== AÇÃO =====
        botao.setOnAction(e -> {

            String m = matricula.getText();
            String n = nome.getText();
            String em = email.getText();

            String s = senha.isVisible() ? senha.getText() : senhaVisivel.getText();

            String c = cpf.getText();

            String erro = usuario.cadastrarUsuario(m, n, em, s, c);

            if (erro != null) {
                mensagem.setText(erro);
                mensagem.setStyle("-fx-text-fill: red;");
                return;
            }

            mensagem.setText("Usuário cadastrado com sucesso!");
            mensagem.setStyle("-fx-text-fill: #00ff88; -fx-font-weight: bold;");

            matricula.clear();
            nome.clear();
            email.clear();
            senha.clear();
            senhaVisivel.clear();
            cpf.clear();
			try {
				Usuario user;
				user = usuario.buscarUsuario(em, s);
				SessaoUsuario.usuarioLogado = user;
				new TelaConsultasUser().start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        matricula.setMaxWidth(300);
        cpf.setMaxWidth(300);
        nome.setMaxWidth(300);
        email.setMaxWidth(300);
        campoSenha.setMaxWidth(300);
        // ===== LAYOUT =====
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        layout.getChildren().addAll(
                titulo,
                matricula,
                nome,
                email,
                campoSenha,
                cpf,
                botao,
                mensagem
        );

        layout.setStyle("-fx-background-color: #0f3d2e;");

        Scene scene = new Scene(layout, 1366, 700);

        stage.setTitle("Tela de Cadastro");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}