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

import model.agendamentos;
import List.Agendamentos;
import List.AgendamentosCancelados;
import utils.DateUtil;

public class AgendamentosView {

    private VBox listaConsultas = new VBox(18);

    private TextField campoFiltro = new TextField();

    private ComboBox<String> filtroStatus = new ComboBox<>();

    public void start(Stage stage) {

        // =========================
        // TITULO
        // =========================
        Label titulo = new Label("Consultas Encerradas");
        titulo.setFont(new Font("Arial", 28));
        titulo.setTextFill(Color.web("#FFD700"));

        // =========================
        // FILTROS (ESTILO PADRÃO)
        // =========================
        campoFiltro.setPromptText("Filtrar por nome");

        campoFiltro.setStyle(
                "-fx-background-color:#1e1e1e;" +
                "-fx-text-fill:white;" +
                "-fx-prompt-text-fill:#aaaaaa;" +
                "-fx-background-radius:10;" +
                "-fx-padding:8;"
        );

        filtroStatus.getItems().addAll("concluida", "cancelada");
        filtroStatus.setValue("concluida");

        filtroStatus.setStyle(
                "-fx-background-color:#1e1e1e;" +
                "-fx-text-fill:white;" +
                "-fx-background-radius:10;"
        );

        Button btnFiltrar = new Button("Filtrar");
        btnFiltrar.setStyle(
                "-fx-background-color:#FFD700;" +
                "-fx-text-fill:black;" +
                "-fx-font-weight:bold;" +
                "-fx-background-radius:10;" +
                "-fx-padding:10 20;"
        );

        HBox filtros = new HBox(15, campoFiltro, filtroStatus, btnFiltrar);
        filtros.setAlignment(Pos.CENTER_LEFT);

        // =========================
        // VOLTAR (PADRÃO DOURADO)
        // =========================
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setStyle(
                "-fx-background-color:#FFD700;" +
                "-fx-text-fill:black;" +
                "-fx-font-weight:bold;" +
                "-fx-background-radius:10;" +
                "-fx-padding:10 20;"
        );

        btnVoltar.setOnAction(e ->
                new TelaConsultas().start(stage)
        );

        HBox acoesDireita = new HBox(btnVoltar);
        acoesDireita.setAlignment(Pos.CENTER_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox barraTopo = new HBox(20, filtros, spacer, acoesDireita);
        barraTopo.setAlignment(Pos.CENTER);
        barraTopo.setMaxWidth(1050);

        // =========================
        // LISTA
        // =========================
        listaConsultas.setPadding(new Insets(10));
        listaConsultas.setAlignment(Pos.CENTER);

        ScrollPane scroll = new ScrollPane(listaConsultas);
        scroll.setFitToWidth(true);

        scroll.setStyle(
                "-fx-background:transparent;" +
                "-fx-background-color:transparent;"
        );

        carregarConsultas("", "concluida");

        btnFiltrar.setOnAction(e ->
                carregarConsultas(
                        campoFiltro.getText(),
                        filtroStatus.getValue()
                )
        );

        filtroStatus.setOnAction(e ->
                carregarConsultas(
                        campoFiltro.getText(),
                        filtroStatus.getValue()
                )
        );

        campoFiltro.setOnAction(e ->
                carregarConsultas(
                        campoFiltro.getText(),
                        filtroStatus.getValue()
                )
        );

        // =========================
        // LAYOUT PRINCIPAL (IGUAL OUTRA TELA)
        // =========================
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color:#0f3d2e;");

        layout.getChildren().addAll(
                titulo,
                barraTopo,
                scroll
        );

        Scene scene = new Scene(layout, 1366, 700);
        stage.setScene(scene);
        stage.setTitle("Consultas Encerradas");
        stage.show();
    }

    // =========================
    // CARREGAMENTO (SEM MUDANÇA)
    // =========================
    private void carregarConsultas(String nome, String status) {

        listaConsultas.getChildren().clear();

        try {

            boolean encontrou = false;

            if (status.equals("concluida")) {

                List<Agendamentos> lista =
                        agendamentos.listarConcluidos();

                for (Agendamentos a : lista) {

                    if (!nome.isBlank()
                            && !a.getNomeUsuario()
                            .toLowerCase()
                            .contains(nome.toLowerCase())) {
                        continue;
                    }

                    HBox wrapper = new HBox(criarCardConsulta(a));
                    wrapper.setAlignment(Pos.CENTER);

                    listaConsultas.getChildren().add(wrapper);

                    encontrou = true;
                }
            }

            if (status.equals("cancelada")) {

                List<AgendamentosCancelados> lista =
                        agendamentos.listarEncerradasFiltradas(nome, status);

                for (AgendamentosCancelados a : lista) {

                    HBox wrapper = new HBox(criarCardCancelado(a));
                    wrapper.setAlignment(Pos.CENTER);

                    listaConsultas.getChildren().add(wrapper);

                    encontrou = true;
                }
            }

            if (!encontrou) {
                listaConsultas.getChildren().add(criarCardVazio());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // =========================
    // CARD VAZIO (PADRÃO UI)
    // =========================
    private HBox criarCardVazio() {

        Label msg = new Label("Nenhuma consulta encontrada");
        msg.setTextFill(Color.WHITE);

        VBox box = new VBox(msg);
        box.setAlignment(Pos.CENTER_LEFT);

        HBox card = new HBox(box);
        card.setPadding(new Insets(40));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPrefWidth(400);
        card.setMaxWidth(400);

        card.setStyle(
                "-fx-background-color:#1e1e1e;" +
                "-fx-background-radius:10;"
        );

        return card;
    }

    // =========================
    // CONCLUÍDA (CARD PADRÃO)
    // =========================
    private HBox criarCardConsulta(Agendamentos a) {

    	Label info = new Label(
    	        "Paciente: " + a.getNomeUsuario() +
    	                "\nMédico: " + a.getNomeMedico() +
    	                "\nTipo: " + a.getTipoMedico() +
    	                "\nData: " + DateUtil.format(a.getDataConsulta()) +
    	                "\nStatus: " + a.getStatus() 
    	);

    	info.setTextFill(Color.WHITE);

    	// 👇 ESSENCIAL PARA NÃO CORTAR TEXTO
    	info.setWrapText(true);
    	info.setMaxWidth(250);


        HBox card = new HBox(info);

        card.setAlignment(Pos.CENTER_LEFT);

        card.setPadding(new Insets(15));
        card.setPrefWidth(400);
        card.setMaxWidth(400);

        card.setStyle(
                "-fx-background-color:#1e1e1e;" +
                "-fx-background-radius:10;"
        );

        return card;
    }
    // =========================
    // CANCELADA (CARD PADRÃO)
    // =========================
    private HBox criarCardCancelado(AgendamentosCancelados a) {

        String motivo =
                (a.getMotivoCancelamento() == null
                        || a.getMotivoCancelamento().isBlank())
                        ? ""
                        : "\nMotivo: " + a.getMotivoCancelamento();

        Label info = new Label(
                "Paciente: " + a.getNomeUsuario() +
                        "\nMédico: " + a.getNomeMedico() +
                        "\nTipo: " + a.getTipoMedico() +
                        "\nData: " + DateUtil.format(a.getDataConsulta()) +
                        "\nStatus: " + a.getStatus() +
                        motivo
        );

        HBox card = new HBox(info);

        card.setAlignment(Pos.CENTER_LEFT);

        card.setPadding(new Insets(15));
        card.setPrefWidth(400);
        card.setMaxWidth(400);

        card.setStyle(
                "-fx-background-color:#1e1e1e;" +
                "-fx-background-radius:10;"
        );

        return card;
    }
}