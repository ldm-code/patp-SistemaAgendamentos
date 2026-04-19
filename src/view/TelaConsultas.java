package view;

// ===== IMPORTS =====
// Importações do JavaFX para construção da interface gráfica
import javafx.geometry.Insets;      // Espaçamento interno (padding)
import java.time.LocalDate;
import javafx.geometry.Pos;         // Alinhamento de elementos
import javafx.scene.Scene;          // Cena (tela)
import javafx.scene.control.*;      // Componentes (Button, Label, ScrollPane, etc)
import javafx.scene.layout.*;       // Layouts (VBox, HBox)
import javafx.scene.paint.Color;    // Manipulação de cores
import javafx.scene.text.Font;      // Fonte de texto
import javafx.stage.Stage;          // Janela principal
// Importações de utilidades Java
import java.time.format.DateTimeFormatter; // Formatar datas
import java.util.List;                     // Lista de objetos

// Importações do seu projeto
import dao.ConsultaDAO;   // Classe responsável por acessar o banco
import List.Consulta;     // Classe modelo da consulta (dados)
import model.consultas;   // Classe com métodos de ação (cancelar, concluir, etc)
import utils.DateUtil;

public class TelaConsultas {

    // ===== COMPONENTES PRINCIPAIS =====

    // VBox que vai armazenar a lista de consultas (cada consulta vira um "card")
    private VBox listaConsultas = new VBox(10);

    // Label usado para mostrar mensagens ao usuário (feedback)
    private Label mensagemFeedback = new Label();

    public void start(Stage stage) {

        // ===== TÍTULO DA TELA =====

        Label titulo = new Label("Consultas");

        // Define fonte e tamanho do texto
        titulo.setFont(new Font("Arial", 28));

        // Define cor do texto (dourado)
        titulo.setTextFill(Color.web("#FFD700"));

        // ===== CONFIGURAÇÃO DO FEEDBACK =====

        mensagemFeedback.setTextFill(Color.LIGHTGREEN); // cor padrão
        mensagemFeedback.setFont(new Font("Arial", 14)); // tamanho da fonte
        
        HBox barraTopo = new HBox(10);
        barraTopo.setAlignment(Pos.CENTER_LEFT);
        
        DatePicker filtroData = new DatePicker();
        filtroData.setPromptText("Filtrar por data");
        
        filtroData.setStyle(
        	    "-fx-background-color: #1e1e1e;" +
        	    "-fx-control-inner-background: #1e1e1e;" +
        	    "-fx-text-fill: white;" +               
        	    "-fx-prompt-text-fill: white;" +        
        	    "-fx-border-radius: 10;" +
        	    "-fx-background-radius: 10;" +
        	    "-fx-padding: 5;"
        	);
        ((TextField) filtroData.getEditor()).setStyle(
        	    "-fx-text-fill: white;" +
        	    "-fx-prompt-text-fill: white;"
        	);
        
        Button btnFiltrar = new Button("Filtrar");

        btnFiltrar.setStyle(
        		"-fx-background-color: #FFD700; " +
                          "-fx-text-fill: black; " +
                          "-fx-font-weight: bold; " +
                          "-fx-background-radius: 10; " +
                          "-fx-padding: 10 20;"
        );
        
        barraTopo.getChildren().addAll(filtroData, btnFiltrar);

        // ===== BOTÃO AGENDAR =====

        Button btnAgendar = new Button("Agendar Consulta");
       
        // Estilização usando CSS inline
        btnAgendar.setStyle(
                "-fx-background-color: #FFD700;" +   // fundo dourado
                "-fx-text-fill: black;" +            // texto preto
                "-fx-font-weight: bold;" +           // negrito
                "-fx-background-radius: 10;"         // bordas arredondadas
        );

        // Ação ao clicar no botão
        btnAgendar.setOnAction(e -> {
            new TelaAgendamento().start(stage); 
        });

        // ===== LISTA DE CONSULTAS =====

        // Define um padding interno na lista
        listaConsultas.setPadding(new Insets(10));

        // ScrollPane permite rolar a lista (caso tenha muitas consultas)
        ScrollPane scroll = new ScrollPane(listaConsultas);

        // Faz o conteúdo ocupar toda a largura
        scroll.setFitToWidth(true);
        // Remove fundo padrão do scroll
        scroll.setStyle(
        	    "-fx-background: transparent;" +
        	    "-fx-background-color: transparent;" +
        	    "-fx-control-inner-background: transparent;"
        	);

        // Carrega os dados do banco ao iniciar a tela
        carregarConsultas();
        btnFiltrar.setOnAction(e -> {
            LocalDate data = filtroData.getValue();

            try {
                if (data == null) {
                    carregarConsultas(); // 🔥 traz tudo
                } else {
                    carregarConsultasPorData(data); // 🔥 filtrado
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        listaConsultas.setAlignment(Pos.CENTER);

        // ===== LAYOUT PRINCIPAL =====

        // VBox organiza elementos verticalmente
        VBox layout = new VBox(15); // 15 = espaçamento entre elementos

        layout.setPadding(new Insets(20));      // espaçamento interno
        layout.setAlignment(Pos.TOP_CENTER);    // alinhamento no topo e centralizado

        // Adiciona elementos na tela (ordem importa!)
        layout.getChildren().addAll(
                titulo,
                btnAgendar,
                barraTopo,
                mensagemFeedback,
                scroll
        );

        // Cor de fundo da tela
        layout.setStyle("-fx-background-color: #0f3d2e;");

        // Cria a cena (tela visível)
        Scene scene = new Scene(layout,1366, 700);

        // Define a cena na janela
        stage.setScene(scene);

        // Título da janela
        stage.setTitle("Consultas");

        // Exibe a janela
        stage.show();
    }
    private boolean confirmarAcao(String mensagem) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Confirmação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não");

        alert.getButtonTypes().setAll(btnSim, btnNao);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
        	  
        	     "-fx-background-color: #FFFFFF;"  // fundo escuro
        	   
        	);
       
        dialogPane.lookupButton(btnSim).setStyle(
        	    "-fx-background-color: #4CAF50; -fx-text-fill: white;"
        	);

        dialogPane.lookupButton(btnNao).setStyle(
        	    "-fx-background-color: #ff4d4d; -fx-text-fill: white;"
        	);
        
        // showAndWait() pausa execução até o usuário responder
        return alert.showAndWait().orElse(btnNao) == btnSim;
    }

    // ===== MÉTODO QUE BUSCA DADOS DO BANCO =====
    private void carregarConsultasPorData(LocalDate data) {

        // Limpa a lista antes de recarregar
        listaConsultas.getChildren().clear();

        List<Consulta> consultas;

        try {
            // Busca todas as consultas no banco
            consultas = ConsultaDAO.buscarPorData(data);
            if (consultas == null || consultas.isEmpty()) {
                listaConsultas.setAlignment(Pos.CENTER);
                listaConsultas.getChildren().add(criarCardVazio());
                return;
            }

            // Para cada consulta encontrada...
            for (Consulta c : consultas) {

                // Cria um "card" visual e adiciona na tela
            	HBox wrapper = new HBox(criarCardConsulta(c));
            	wrapper.setAlignment(Pos.CENTER);

            	listaConsultas.getChildren().add(wrapper);
            }

        } catch (Exception e) {
            // Caso ocorra erro, imprime no console
            e.printStackTrace();
        }
    }
    private void carregarConsultas() {

        // Limpa a lista antes de recarregar
        listaConsultas.getChildren().clear();

        List<Consulta> consultas;

        try {
            // Busca todas as consultas no banco
            consultas = ConsultaDAO.listarConsultas();

            // Para cada consulta encontrada...
            for (Consulta c : consultas) {

                // Cria um "card" visual e adiciona na tela
            	HBox wrapper = new HBox(criarCardConsulta(c));
            	wrapper.setAlignment(Pos.CENTER);

            	listaConsultas.getChildren().add(wrapper);
            }

        } catch (Exception e) {
            // Caso ocorra erro, imprime no console
            e.printStackTrace();
        }
    }
    // metodo para formatacao visual do campo de status
    private String formatarStatus(String status) {
     // nulo retorna nada
        if (status == null) return "";
         // switch para condicionar o que exibido
        switch (status.toLowerCase()) { // toLoweCase() transforma em minúsculo
        // mudar para agenadada:
            case "agendada":
                return "Agendada"; // agendada vira Agendada na tela

            case "concluida":
                return "Concluída"; // concluida vira   Concluída   na tela

            case "cancelada":
                return "Cancelada"; // cancelada vida Cancelada na tela

            default:
                return status; // retorna o valor 
        }
    }

    // ===== MÉTODO QUE CRIA O CARD VISUAL DE UMA CONSULTA =====
    private HBox criarCardConsulta(Consulta consulta) {

        // Formata a data para o padrão brasileiro
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // Label com todas as informações da consulta
        Label info = new Label(
            "Paciente: " + consulta.getNomeUsuario() +
            "\nMédico: " + consulta.getNomeMedico() +
            "\nEspecialidade: " + consulta.getEspecialidade() +
            "\nData: " + DateUtil.format(consulta.getDataConsulta()) +
            "\nStatus: " +  formatarStatus(consulta.getStatus()) // campo de status formatado para melhor exibicao visual
        );

        // Define cor do texto
        info.setTextFill(Color.WHITE);
        // largura fixa

        // ===== BOTÕES DE AÇÃO =====

        Button btnCancelar = new Button("Cancelar"); // cancelar
        Button btnConcluir = new Button("Concluir"); // concluir
        Button btnEspera = new Button("Agendada");  // marcar como agendada
        Button btnEditar = new Button("Editar");

        // Estilo visual dos botões
        btnCancelar.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
        btnConcluir.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnEspera.setStyle("-fx-background-color: #ffaa00; -fx-text-fill: black;");
        

        btnEditar.setStyle(
            "-fx-background-color: #3399ff;" +
            "-fx-text-fill: white;"
        );

        
        // Obtém o status da consulta
        String status = consulta.getStatus();

        // ===== REGRAS DE NEGÓCIO =====
        // desbilita botao de edicao caso nao esteja agendada
        if (status.equalsIgnoreCase("concluida") || status.equalsIgnoreCase("cancelada")) {
            btnEditar.setDisable(true);
        }
        
        // Se já estiver concluído ,desbilita botao de concluir
        if (status.equalsIgnoreCase("concluida")) {
            btnConcluir.setDisable(true);
        }
        // se ja esta caneclado, desbilita botao de cancelar
        else if( status.equalsIgnoreCase("cancelada")) {
        	
        	btnCancelar.setDisable(true);
        }
        // se esta em espera (agendada) desabiliita o botao de agendar
        else if( status.equalsIgnoreCase("agendada")) {
        	
        	btnEspera.setDisable(true);
        }

        // ===== AÇÕES DOS BOTÕES =====
        
        // Editar data e hora
        btnEditar.setOnAction(e -> {
        	if (!confirmarAcao("Deseja editar esta consulta?")) {
        		return; // usuário clicou em NÃO → sai
        	}
        	new TelaEdicaoConsulta().start(
        		    consulta,
        		    () -> carregarConsultas()
        		);
        });
        // Cancelar consulta
        btnCancelar.setOnAction(e -> {
        	if (!confirmarAcao("Deseja cancelar esta consulta?")) {
        		return; // usuário clicou em NÃO → sai
        	}
            try {
                consultas.cancelarConsulta(consulta.getId());

                mensagemFeedback.setText("Consulta cancelada com sucesso!");
                mensagemFeedback.setTextFill(Color.RED);

                carregarConsultas(); // atualiza tela

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Concluir consulta
        btnConcluir.setOnAction(e -> {
        	if (!confirmarAcao("Deseja marcar como concluida esta consulta?")) {
        		return; // usuário clicou em NÃO → sai
        	}
        	
            try {
                consultas.concluirConsulta(consulta.getId());

                mensagemFeedback.setText("Consulta concluída!");
                mensagemFeedback.setTextFill(Color.LIGHTGREEN);

                carregarConsultas();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Marcar como em espera
        btnEspera.setOnAction(e -> {
        	if (!confirmarAcao("Deseja marcar como agendada esta consulta?")) {
        		return; // usuário clicou em NÃO → sai
        	}
            try {
            	String resultado = consultas.marcarAgendamento(
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

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // VBox para organizar os botões verticalmente
        VBox botoes = new VBox(5,btnEditar, btnCancelar, btnConcluir, btnEspera);

        // ===== CARD VISUAL =====

        // HBox organiza horizontalmente (info + botões)
        HBox card = new HBox(20);
        card.setPrefWidth(400);
        card.setMaxWidth(400);

        card.setPadding(new Insets(15));         // espaçamento interno
        card.setAlignment(Pos.CENTER_LEFT);     // alinhamento

        // Adiciona os elementos no card
        card.getChildren().addAll(info, botoes);

        // Estilo do card
        card.setStyle(
            "-fx-background-color: #1e1e1e;" +   // fundo escuro
            "-fx-background-radius: 10;"         // bordas arredondadas
        );

        return card;
    }
    private HBox criarCardVazio() {

        Label mensagem = new Label("Nenhuma consulta agendada.");
        mensagem.setTextFill(Color.WHITE);
        mensagem.setFont(new Font("Arial", 16));

        Label dica = new Label("Aguarde ate alguem marcar uma consulta.");
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
}