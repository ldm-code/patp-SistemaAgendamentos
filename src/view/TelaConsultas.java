
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
import List.MedicosSelect;
import model.consultas;
import model.medicos;
import utils.DateUtil;

public class TelaConsultas {
    // Mais respiro entre cards
    private static VBox listaConsultas = new VBox(18);

    
    public static LocalDate data=LocalDate.now();
    public static String status = "agendada";
    public static String nomeUsuario = "";
    public static int idMedico = 0;
    
    public void start(Stage stage) {

        Label titulo = new Label("Consultas");
        titulo.setFont(new Font("Arial",28));
        titulo.setTextFill(Color.web("#FFD700"));

  
        

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

        // =========================
        // NOVO FILTRO STATUS
        // =========================

        ComboBox<String> filtroStatus = new ComboBox<>();

        filtroStatus.getItems().addAll(
                "Todos",
                "agendada",
                "concluida",
                "cancelada"
        );

        filtroStatus.setValue("agendada");

        filtroStatus.setStyle(
        		 "-fx-background-color: #1e1e1e;"+
        				 "-fx-text-color: #1e1e1e;"+
        	                "-fx-control-inner-background: #1e1e1e;"+
        	                "-fx-text-fill: white;"+
        	                
        	                "-fx-border-radius:10;"+
        	                "-fx-background-radius:10;"+
        	                "-fx-padding:5;"+
        	               " -fx-prompt-text-fill: white;"
        );
        filtroStatus.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                setText(empty || item == null ? null : item);
                setTextFill(Color.WHITE);
            }
        });
        TextField filtroNome = new TextField();
      
        filtroNome.setPromptText("Buscar paciente:");
        	
        filtroNome.setStyle(
        		 "-fx-background-color: #1e1e1e;"+
        	                "-fx-control-inner-background: #1e1e1e;"+
        	                "-fx-text-fill: white;"+
        	                "-fx-prompt-text-fill: white;"+
        	                "-fx-border-radius:10;"+
        	                "-fx-background-radius:10;"+
        	                "-fx-padding:5;"
        );
        Button btnFiltrar = new Button("Filtrar");

        btnFiltrar.setStyle(
                "-fx-background-color:#FFD700;"+
                "-fx-text-fill:black;"+
                "-fx-font-weight:bold;"+
                "-fx-background-radius:10;"+
                "-fx-padding:10 20;"
        );
        ComboBox<MedicosSelect> filtroMedico = new ComboBox<>();

        filtroMedico.setPromptText("Filtrar médico");
        filtroMedico.setCellFactory(param -> new ListCell<>() {

            @Override
            protected void updateItem(MedicosSelect m, boolean empty) {

                super.updateItem(m, empty);

                setText(
                	    empty || m == null
                	    ? null
                	    : m.getTipo() == null || m.getTipo().isBlank()
                	        ? m.getNome()
                	        : m.getNome() + " - " + m.getTipo()
                	);

                setTextFill(Color.web("#d3d3d3"));

                setStyle(
                    "-fx-background-color:#1e1e1e;"
                );
            }
        });
        filtroMedico.setButtonCell(new ListCell<>() {

            @Override
            protected void updateItem(MedicosSelect m, boolean empty) {

                super.updateItem(m, empty);

                setText(
                    empty || m == null
                    ? null
                    : m.getNome()
                );

                setTextFill(
                    Color.web("#d3d3d3")
                );
            }
        });
        filtroMedico.setStyle(
        		 "-fx-background-color: #1e1e1e;"+
     	                "-fx-control-inner-background: #1e1e1e;"+
     	                "-fx-text-fill: white;"+
     	                "-fx-prompt-text-fill: white;"+
     	                "-fx-border-radius:10;"+
     	                "-fx-background-radius:10;"+
     	                "-fx-padding:5;"
        );
        try {

            List<MedicosSelect> medicos =
                    medicosDAO.select();

            // ITEM PADRÃO
            MedicosSelect todos =
                    new MedicosSelect();

            todos.setId(0);
            todos.setNome("Todos os Medicos:");
            todos.setTipo("");

            // adiciona "Todos"
            filtroMedico.getItems().add(todos);

            // adiciona médicos reais
            filtroMedico.getItems().addAll(medicos);

            // valor inicial
            filtroMedico.setValue(todos);

        } catch (Exception e) {

            e.printStackTrace();
        }
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
       
        // =========================
        // HBOX FILTROS
        // =========================

     // =========================
     // FILTROS ESQUERDA
     // =========================

     HBox filtros = new HBox(
             10,
             filtroData,
             filtroStatus,
             filtroNome,
             filtroMedico,
             btnFiltrar
     );

     filtros.setAlignment(Pos.CENTER_LEFT);

     // =========================
     // BOTÕES DIREITA
     // =========================

     HBox acoesDireita = new HBox(
             10,
             btnEncerradas,
             btnMedicos
     );

     acoesDireita.setAlignment(Pos.CENTER_RIGHT);

     // =========================
     // ESPAÇADOR RESPONSIVO
     // =========================

     Region spacer = new Region();

     HBox.setHgrow(
             spacer,
             Priority.ALWAYS
     );

     // =========================
     // BARRA TOPO
     // =========================

     HBox barraTopo = new HBox(
             20,
             filtros,
             spacer,
             acoesDireita
     );

     barraTopo.setAlignment(Pos.CENTER);

     barraTopo.setPrefWidth(1200);

     barraTopo.setMaxWidth(
             Double.MAX_VALUE
     );

     barraTopo.setPadding(
             new Insets(10, 20, 10, 20)
     );
   

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

        btnAgendar.setOnAction(e->{

            new TelaAgendamento().start(stage);
            condicionarExibicao();
        });

        btnMedicos.setOnAction(e-> {
            new TelaMedicos().start(() -> {
                carregarMedicos(filtroMedico);
                condicionarExibicao();
                
            });
        });

        btnEncerradas.setOnAction(e -> {
            new AgendamentosView().start(stage);
            condicionarExibicao();
        });

        // =========================
        // BOTÃO FILTRAR
        // =========================

        btnFiltrar.setOnAction(e -> {

            data = filtroData.getValue();

            status = filtroStatus.getValue();

            nomeUsuario = filtroNome.getText();

            // PEGA MÉDICO SELECIONADO
            MedicosSelect medicoSelecionado =
                    filtroMedico.getValue();

            if(medicoSelecionado != null){

                idMedico = medicoSelecionado.getId();

            }
            else{

                idMedico = 0;
            }

            condicionarExibicao();
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

        if (data != null) {
            filtroData.setValue(data);
            condicionarExibicao();
        }

        condicionarExibicao();

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
                barraTopo,
                linhaAcoes,
               
                scroll
        );

        layout.setStyle("-fx-background-color:#0f3d2e;");

        Scene scene = new Scene(layout,1366,700);

        stage.setScene(scene);
        stage.setTitle("Consultas");
        stage.show();
    }
    public static void condicionarExibicao() {
        try {
        	
        	if(
        		    data == null
        		    && status.equals("Todos")
        		    && nomeUsuario.isBlank()
        		    && idMedico == 0
        		){
        		    carregarConsultas();
        		}

        		else if (
        		    status.equals("Todos")
        		    && data != null
        		    && nomeUsuario.isBlank()
        		    && idMedico == 0
        		) {

        		    carregarConsultasPorData(data);
        		}

        		else if (
        		    !status.equals("Todos")
        		    && data != null
        		    && nomeUsuario.isBlank()
        		    && idMedico == 0
        		) {

        		    carregarConsultasPorStatusEdata(status);
        		}

        		else if (
        		    data == null
        		    && !status.equals("Todos")
        		    && nomeUsuario.isBlank()
        		    && idMedico == 0
        		) {

        		    carregarConsultasPorStatusEdata(status);
        		}

        		else if (
        		    data == null
        		    && status.equals("Todos")
        		    && !nomeUsuario.isBlank()
        		    && idMedico == 0
        		) {

        		    carregarConsultasPorStatusEdata(status);
        		}

        		else if (
        		    data != null
        		    && status.equals("Todos")
        		    && !nomeUsuario.isBlank()
        		    && idMedico == 0
        		) {

        		    carregarConsultasPorStatusEdata(status);
        		}

        		else if (
        		    data != null
        		    && !status.equals("Todos")
        		    && !nomeUsuario.isBlank()
        		    && idMedico == 0
        		) {

        		    carregarConsultasPorStatusEdata(status);
        		}

        		else if (
        		    data == null
        		    && !status.equals("Todos")
        		    && !nomeUsuario.isBlank()
        		    && idMedico == 0
        		) {

        		    carregarConsultasPorStatusEdata(status);
        		}

        		// =========================
        		// QUALQUER CASO COM MÉDICO
        		// =========================

        		else {

        		    carregarConsultasPorStatusEdata(status);
        		}
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void carregarMedicos(ComboBox<MedicosSelect> filtroMedico) {

        try {

            filtroMedico.getItems().clear();

            List<MedicosSelect> medicos =
                    medicosDAO.select();

            MedicosSelect todos = new MedicosSelect();

            todos.setId(0);
            todos.setNome("Todos os Medicos:");
            todos.setTipo("");

            filtroMedico.getItems().add(todos);
            filtroMedico.getItems().addAll(medicos);

            filtroMedico.setValue(todos);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
  


    private static boolean confirmarAcao(String mensagem){

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


    private static void carregarConsultasPorData(LocalDate data){

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
    private static void carregarConsultasPorStatusEdata(String status){

        listaConsultas.getChildren().clear();

        try{
           
            List<Consulta> consultas =
                    ConsultaDAO.buscarFiltradas(data, status, nomeUsuario,idMedico);

            if(consultas == null || consultas.isEmpty()){

                listaConsultas.getChildren().add(
                        criarCardVazio()
                );

                return;
            }

            for(Consulta c : consultas){

                HBox wrapper = new HBox(
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


    private static void carregarConsultas(){

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


    private static String formatarStatus(String status){

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
    private static void atualizarLista() {
        condicionarExibicao();
    }


    private static HBox criarCardConsulta(Consulta consulta){

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

//        if(status.equalsIgnoreCase("concluida")
//        || status.equalsIgnoreCase("cancelada")){
//            btnEditar.setDisable(true);
//            
//        }

        if(status.equalsIgnoreCase("concluida")){
            btnConcluir.setDisable(true);
            btnCancelar.setDisable(true);
            btnEditar.setDisable(true);
        }
        else if(status.equalsIgnoreCase("cancelada")){
        	  btnConcluir.setDisable(true);
              btnCancelar.setDisable(true);
              btnEditar.setDisable(true);
        }
        else if(status.equalsIgnoreCase("agendada")){
            btnEspera.setDisable(true);
        }
       


        btnEditar.setOnAction(e->{

            if(!confirmarAcao(
                    "Deseja editar esta consulta?"
            )) return;

            try {
				new TelaEdicaoConsulta().start(
				        consulta,
				        ()->atualizarLista() 
				);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });


        btnCancelar.setOnAction(e->{

            if(!confirmarAcao(
                    "Deseja cancelar esta consulta?"
            )) return;

            new MotivoTelaCancelamento().start(
                    consulta.getId(),
                    ()->atualizarLista()
            );
        });


        btnConcluir.setOnAction(e->{

            if(!confirmarAcao(
                    "Deseja marcar como concluida esta consulta?"
            )) return;

            try{
                consultas.concluirConsulta(consulta.getId());
                
                mostrarAlert("Consulta Concluida","Consulta finalizada com sucesso!",Alert.AlertType.INFORMATION);
                atualizarLista();
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
                condicionarExibicao();
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


    private static HBox criarCardVazio(){

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
    private static void mostrarAlert(String titulo, String msg, Alert.AlertType tipo) {

        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.show();
    }
}
