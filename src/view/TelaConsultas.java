
package view;

import dao.medicosDAO;
import javafx.geometry.Insets;
import java.time.LocalDate;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dao.ConsultaDAO;
import List.Consulta;
import model.consultas;
import model.medicos;
import utils.DateUtil;

public class TelaConsultas {

    // Mais respiro entre cards
    private VBox listaConsultas = new VBox(18);

    private Label mensagemFeedback = new Label();

    public void start(Stage stage) {

        Label titulo = new Label("Consultas");
        titulo.setFont(new Font("Arial",28));
        titulo.setTextFill(Color.web("#FFD700"));

        mensagemFeedback.setTextFill(Color.LIGHTGREEN);
        mensagemFeedback.setFont(new Font("Arial",14));

        // =========================
        // FILTRO
        // =========================

        DatePicker filtroData = new DatePicker();
        filtroData.setPromptText("Filtrar por data");

        filtroData.setStyle(
                "-fx-background-color: #1e1e1e;"+
                "-fx-control-inner-background: #1e1e1e;"+
                "-fx-text-fill: white;"+
                "-fx-prompt-text-fill: white;"+
                "-fx-border-radius:10;"+
                "-fx-background-radius:10;"+
                "-fx-padding:5;"
        );

        ((TextField) filtroData.getEditor()).setStyle(
                "-fx-text-fill:white;"+
                "-fx-prompt-text-fill:white;"
        );

        Button btnFiltrar = new Button("Filtrar");
        btnFiltrar.setStyle(
                "-fx-background-color:#FFD700;"+
                "-fx-text-fill:black;"+
                "-fx-font-weight:bold;"+
                "-fx-background-radius:10;"+
                "-fx-padding:10 20;"
        );


        // =========================
        // BOTÕES TOPO
        // =========================

        Button btnEncerradas = new Button("Agendamentos Encerrados");
        btnEncerradas.setStyle(
                "-fx-background-color:#FFD700;"+
                "-fx-text-fill:black;"+
                "-fx-font-weight:bold;"+
                "-fx-background-radius:10;"+
                "-fx-padding:10 20;"
        );


        Button btnMedicos = new Button("Cadastrar Médicos");
        btnMedicos.setStyle(
                "-fx-background-color:#FFD700;"+
                "-fx-text-fill:black;"+
                "-fx-font-weight:bold;"+
                "-fx-background-radius:10;"+
                "-fx-padding:10 20;"
        );


        HBox filtros = new HBox(15,
                filtroData,
                btnFiltrar
        );
        filtros.setAlignment(Pos.CENTER_LEFT);


        HBox acoesDireita = new HBox(
                15,
                btnEncerradas,
                btnMedicos
        );
        acoesDireita.setAlignment(Pos.CENTER_RIGHT);


        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        HBox barraTopo = new HBox(
                20,
                filtros,
                spacer,
                acoesDireita
        );

        barraTopo.setAlignment(Pos.CENTER);
        barraTopo.setMaxWidth(1050);


        // =========================
        // AGENDAR
        // =========================

        Button btnAgendar = new Button("Agendar Consulta");

        btnAgendar.setStyle(
                "-fx-background-color:#FFD700;"+
                "-fx-text-fill:black;"+
                "-fx-font-weight:bold;"+
                "-fx-background-radius:10;"
        );

        btnAgendar.setOnAction(e->
                new TelaAgendamento().start(stage)
        );

        btnMedicos.setOnAction(e-> {
            new TelaMedicos().start(() -> {
                carregarConsultas();
            });
        });

        btnEncerradas.setOnAction(e -> {
            // Abre tela de agendamentos 
           new AgendamentosView().start(stage);
        });


        // =========================
        // LISTA
        // =========================

        listaConsultas.setPadding(new Insets(10));
        listaConsultas.setAlignment(Pos.CENTER);

        ScrollPane scroll = new ScrollPane(listaConsultas);
        scroll.setFitToWidth(true);

        scroll.setStyle(
                "-fx-background:transparent;"+
                "-fx-background-color:transparent;"+
                "-fx-control-inner-background:transparent;"
        );

        carregarConsultas();

        btnFiltrar.setOnAction(e -> {
            LocalDate data = filtroData.getValue();

            try {
                if(data==null){
                    carregarConsultas();
                }
                else{
                    carregarConsultasPorData(data);
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });


        // =========================
        // LAYOUT PRINCIPAL
        // =========================

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);

        HBox linhaAcoes = new HBox(btnAgendar);
        linhaAcoes.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
                titulo,
                linhaAcoes,
                barraTopo,
                mensagemFeedback,
                scroll
        );

        layout.setStyle("-fx-background-color:#0f3d2e;");

        Scene scene = new Scene(layout,1366,700);
        stage.setScene(scene);
        stage.setTitle("Consultas");
        stage.show();
    }


    private boolean confirmarAcao(String mensagem){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Confirmação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não");

        alert.getButtonTypes().setAll(btnSim,btnNao);

        DialogPane dialogPane=alert.getDialogPane();
        dialogPane.setStyle(
                "-fx-background-color:#FFFFFF;"
        );

        dialogPane.lookupButton(btnSim)
                .setStyle("-fx-background-color:#4CAF50;-fx-text-fill:white;");

        dialogPane.lookupButton(btnNao)
                .setStyle("-fx-background-color:#ff4d4d;-fx-text-fill:white;");

        return alert.showAndWait().orElse(btnNao)==btnSim;
    }


    private void carregarConsultasPorData(LocalDate data){

        listaConsultas.getChildren().clear();

        try{

            List<Consulta> consultas=ConsultaDAO.buscarPorData(data);

            if(consultas==null || consultas.isEmpty()){
                listaConsultas.getChildren().add(criarCardVazio());
                return;
            }

            for(Consulta c:consultas){

                HBox wrapper=new HBox(
                        criarCardConsulta(c)
                );

                wrapper.setAlignment(Pos.CENTER);

                listaConsultas.getChildren().add(wrapper);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    private void carregarConsultas(){

        listaConsultas.getChildren().clear();

        try{

            List<Consulta> consultas=ConsultaDAO.listarConsultas();

            for(Consulta c:consultas){

                HBox wrapper=new HBox(
                        criarCardConsulta(c)
                );

                wrapper.setAlignment(Pos.CENTER);

                listaConsultas.getChildren().add(wrapper);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    private String formatarStatus(String status){

        if(status==null) return "";

        switch(status.toLowerCase()){

            case "agendada":
                return "Agendada";

            case "concluida":
                return "Concluída";

            case "cancelada":
                return "Cancelada";

            default:
                return status;
        }
    }


    private HBox criarCardConsulta(Consulta consulta){

    	Label info = new Label(
    	        "Paciente: " + consulta.getNomeUsuario() +
    	        "\nMédico: " + consulta.getNomeMedico() +
    	        "\nEspecialidade: " + consulta.getEspecialidade() +

    	        "\nAgendada em: " +
    	        DateUtil.format(
    	            consulta.getDataAgendamento()
    	        ) +

    	        "\nConsulta para: " +
    	        DateUtil.format(
    	            consulta.getDataConsulta()
    	        ) +

    	        "\nStatus: " +
    	        formatarStatus(
    	            consulta.getStatus()
    	        )
    	);
        info.setTextFill(Color.WHITE);


        Button btnCancelar = new Button("Cancelar");
        Button btnConcluir = new Button("Concluir");
        Button btnEspera = new Button("Agendada");
        Button btnEditar = new Button("Reagendar");


        btnCancelar.setStyle(
                "-fx-background-color:#ff4d4d;-fx-text-fill:white;"
        );

        btnConcluir.setStyle(
                "-fx-background-color:#4CAF50;-fx-text-fill:white;"
        );

        btnEspera.setStyle(
                "-fx-background-color:#ffaa00;-fx-text-fill:black;"
        );

        btnEditar.setStyle(
                "-fx-background-color:#3399ff;"+
                "-fx-text-fill:white;"
        );


        // =========================
        // ALINHAMENTO FIXO DOS BOTÕES
        // =========================

        // Botões voltam ao estilo antigo, sem ficarem largos demais
        // removido alinhamento por largura fixa e trocado por coluna alinhada

        String status=consulta.getStatus();

        if(status.equalsIgnoreCase("concluida")
        || status.equalsIgnoreCase("cancelada")){
            btnEditar.setDisable(true);
        }

        if(status.equalsIgnoreCase("concluida")){
            btnConcluir.setDisable(true);
        }
        else if(status.equalsIgnoreCase("cancelada")){
            btnCancelar.setDisable(true);
        }
        else if(status.equalsIgnoreCase("agendada")){
            btnEspera.setDisable(true);
        }
       


        btnEditar.setOnAction(e->{

            if(!confirmarAcao(
                    "Deseja editar esta consulta?"
            )) return;

            new TelaEdicaoConsulta().start(
                    consulta,
                    ()->carregarConsultas()
            );
        });


        btnCancelar.setOnAction(e->{

            if(!confirmarAcao(
                    "Deseja cancelar esta consulta?"
            )) return;

            new MotivoTelaCancelamento().start(
                    consulta.getId(),
                    ()->carregarConsultas()
            );
        });


        btnConcluir.setOnAction(e->{

            if(!confirmarAcao(
                    "Deseja marcar como concluida esta consulta?"
            )) return;

            try{
                consultas.concluirConsulta(consulta.getId());
                mensagemFeedback.setText("Consulta concluída!");
                mensagemFeedback.setTextFill(Color.LIGHTGREEN);
                carregarConsultas();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });

        btnEspera.setOnAction(e->{
            if(!confirmarAcao("Deseja marcar como agendada esta consulta?")) return;
            try{
                String resultado=consultas.marcarAgendamento(
                        consulta.getId(),
                        consulta.getIdMedico(),
                        consulta.getDataConsulta(),
                        consulta.getIdUsuario()
                );
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ação não permitida");
                alert.setHeaderText(null);
                alert.setContentText(resultado);
                alert.showAndWait();
                carregarConsultas();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });

        // coluna fixa do conteúdo
        info.setMinWidth(220);
        info.setPrefWidth(220);

        // botões tamanho natural, alinhados em coluna
        VBox botoes = new VBox(
                3,
                btnEditar,
                btnCancelar,
                btnConcluir,
                btnEspera
        );
        botoes.setAlignment(Pos.CENTER_LEFT);
        botoes.setMinWidth(100);

        // card preservado com espaçamento mais natural
        HBox card = new HBox(15);

        card.setPrefWidth(400);
        card.setMaxWidth(400);

        card.setPadding(
                new Insets(15)
        );

        card.setAlignment(
                Pos.CENTER_LEFT
        );

        card.getChildren().addAll(
                info,
                botoes
        );

        card.setStyle(
                "-fx-background-color:#1e1e1e;"+
                "-fx-background-radius:10;"
        );

        return card;
    }


    private HBox criarCardVazio(){

        Label mensagem = new Label(
                "Nenhuma consulta agendada."
        );

        mensagem.setTextFill(Color.WHITE);
        mensagem.setFont(new Font("Arial",16));


        Label dica = new Label(
                "Aguarde ate alguem marcar uma consulta."
        );

        dica.setTextFill(
                Color.web("#aaaaaa")
        );


        VBox conteudo = new VBox(
                5,
                mensagem,
                dica
        );

        conteudo.setAlignment(
                Pos.CENTER_LEFT
        );


        HBox card = new HBox();
        card.setPadding(new Insets(50));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPrefWidth(400);
        card.setMaxWidth(400);

        card.getChildren().add(conteudo);

        card.setStyle(
                "-fx-background-color:#1e1e1e;"+
                "-fx-background-radius:10;"
        );

        return card;
    }
}
