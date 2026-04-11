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
import model.usuario;

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
        cpf.setPromptText("CPF");
        cpf.setStyle(estiloCampos);

        // ===== SENHA COM OLHO =====
        PasswordField senha = new PasswordField();
        senha.setPromptText("Senha");

        TextField senhaVisivel = new TextField();
        senhaVisivel.setPromptText("Senha");

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

            // pega senha correta
            String s = senha.isVisible() ? senha.getText() : senhaVisivel.getText();

            String c = cpf.getText();
            c = c.replaceAll("[^\\d]", "").trim();

            if (m.isEmpty() || n.isEmpty() || em.isEmpty() || s.isEmpty() || c.isEmpty()) {

                mensagem.setText("Todos os campos são obrigatórios");
                mensagem.setStyle("-fx-text-fill: red;");

            } else if (!em.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {

                mensagem.setText("Email inválido");

            } else if (!m.matches("\\d+")) {

                mensagem.setText("Matrícula inválida");

            } else if (c.length() != 11) {

                mensagem.setText("CPF inválido");

            } else if (s.length() < 6) {

                mensagem.setText("Senha muito curta");

            } else {

                usuario.cadastrarUsuario(m, n, em, s, c);

                mensagem.setText("Usuário cadastrado com sucesso!");
                mensagem.setStyle("-fx-text-fill: #00ff88; -fx-font-weight: bold;");

                matricula.clear();
                nome.clear();
                email.clear();
                senha.clear();
                senhaVisivel.clear();
                cpf.clear();
            }
        });

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

        Scene scene = new Scene(layout, 400, 500);

        stage.setTitle("Tela de Cadastro");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}