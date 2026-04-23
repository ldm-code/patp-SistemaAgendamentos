package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

import dao.agendamentosDAO;
import List.Agendamentos;
import utils.SessaoUsuario;

public class AgendamentosView {

    private VBox listaConsultas = new VBox(10);

    public void start(Stage stage) {

        // ===== TÍTULO =====
        Label titulo = new Label("Consultas Concluídas");
        titulo.setFont(new Font("Arial", 28));
        titulo.setTextFill(Color.web("#FFD700"));

        // ===== LISTA =====
        listaConsultas.setPadding(new Insets(10));

        ScrollPane scroll = new ScrollPane(listaConsultas);
        scroll.setStyle(
                "-fx-background: transparent;" +
                "-fx-background-color: transparent;" +
                "-fx-control-inner-background: transparent;"
        );
        scroll.setFitToWidth(true);

        carregarConsultas();

        // ===== LAYOUT =====
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #0f3d2e;");

        layout.getChildren().addAll(titulo, scroll);

        Scene scene = new Scene(layout, 1366, 700);
        stage.setScene(scene);
        stage.setTitle("Consultas");
        stage.show();
    }

    // ===== CARREGAR DO BANCO (AGORA USANDO AGENDAMENTOS DAO) =====
    private void carregarConsultas() {

        listaConsultas.getChildren().clear();

        try {
            List<Agendamentos> lista = agendamentosDAO.listarAgendamentosConcluidos();

            if (lista.isEmpty()) {
                listaConsultas.getChildren().add(criarCardVazio());
                return;
            }

            for (Agendamentos a : lista) {
                HBox wrapper = new HBox(criarCardConsulta(a));
                wrapper.setAlignment(Pos.CENTER);
                listaConsultas.getChildren().add(wrapper);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== CARD VAZIO =====
    private HBox criarCardVazio() {

        Label mensagem = new Label("Nenhuma consulta concluída");
        mensagem.setTextFill(Color.WHITE);
        mensagem.setFont(new Font("Arial", 16));

        Label dica = new Label("Ainda não há consultas finalizadas no sistema");
        dica.setTextFill(Color.web("#aaaaaa"));

        VBox conteudo = new VBox(5, mensagem, dica);
        conteudo.setAlignment(Pos.CENTER_LEFT);

        HBox card = new HBox();
        card.setPadding(new Insets(50));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPrefWidth(400);
        card.setMaxWidth(400);

        card.getChildren().add(conteudo);

        card.setStyle(
                "-fx-background-color: #1e1e1e;" +
                "-fx-background-radius: 10;"
        );

        return card;
    }

    // ===== CARD CONSULTA =====
    private HBox criarCardConsulta(Agendamentos a) {

        Label info = new Label(
                "Paciente: " + a.getNomeUsuario() +
                "\nMédico: " + a.getNomeMedico() +
                "\nTipo: " + a.getTipoMedico() +
                "\nData: " + a.getDataConsulta() +
                "\nStatus: " + a.getStatus()
        );

        info.setTextFill(Color.WHITE);

        HBox card = new HBox(20);
        card.setPadding(new Insets(15));
        card.setAlignment(Pos.CENTER_LEFT);

        card.getChildren().add(info);

        card.setStyle(
                "-fx-background-color: #1e1e1e;" +
                "-fx-background-radius: 10;"
        );

        card.setPrefWidth(400);
        card.setMaxWidth(400);

        return card;
    }
}