package view;

// ===== IMPORTS =====
// Importações do JavaFX para construção da interface gráfica
import javafx.geometry.Insets;      // Espaçamento interno (padding)
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
            System.out.println("abrir tela de agendamento");
        });

        // ===== LISTA DE CONSULTAS =====

        // Define um padding interno na lista
        listaConsultas.setPadding(new Insets(10));

        // ScrollPane permite rolar a lista (caso tenha muitas consultas)
        ScrollPane scroll = new ScrollPane(listaConsultas);

        // Faz o conteúdo ocupar toda a largura
        scroll.setFitToWidth(true);

        // Remove fundo padrão do scroll
        scroll.setStyle("-fx-background: transparent;");

        // Carrega os dados do banco ao iniciar a tela
        carregarConsultas();

        // ===== LAYOUT PRINCIPAL =====

        // VBox organiza elementos verticalmente
        VBox layout = new VBox(15); // 15 = espaçamento entre elementos

        layout.setPadding(new Insets(20));      // espaçamento interno
        layout.setAlignment(Pos.TOP_CENTER);    // alinhamento no topo e centralizado

        // Adiciona elementos na tela (ordem importa!)
        layout.getChildren().addAll(
                titulo,
                btnAgendar,
                mensagemFeedback,
                scroll
        );

        // Cor de fundo da tela
        layout.setStyle("-fx-background-color: #0f3d2e;");

        // Cria a cena (tela visível)
        Scene scene = new Scene(layout, 500, 500);

        // Define a cena na janela
        stage.setScene(scene);

        // Título da janela
        stage.setTitle("Consultas");

        // Exibe a janela
        stage.show();
    }

    // ===== MÉTODO QUE BUSCA DADOS DO BANCO =====
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
                listaConsultas.getChildren().add(criarCardConsulta(c));
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
            case "em_espera":
                return "Em espera"; // em_espera vira Em espera na tela

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
            "\nData: " + consulta.getDataConsulta().format(formatter) +
            "\nStatus: " +  formatarStatus(consulta.getStatus()) // campo de status formatado para melhor exibicao visual
        );

        // Define cor do texto
        info.setTextFill(Color.WHITE);

        // ===== BOTÕES DE AÇÃO =====

        Button btnCancelar = new Button("Cancelar"); // cancelar
        Button btnConcluir = new Button("Concluir"); // concluir
        Button btnEspera = new Button("Em Espera");  // marcar em espera

        // Estilo visual dos botões
        btnCancelar.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
        btnConcluir.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnEspera.setStyle("-fx-background-color: #ffaa00; -fx-text-fill: black;");

        // Obtém o status da consulta
        String status = consulta.getStatus();

        // ===== REGRAS DE NEGÓCIO =====
        
        
        // Se já estiver concluído ,desbilita botao de concluir
        if (status.equalsIgnoreCase("concluida")) {
            btnConcluir.setDisable(true);
        }
        // se ja esta caneclado, desbilita botao de cancelar
        else if( status.equalsIgnoreCase("cancelada")) {
        	
        	btnCancelar.setDisable(true);
        }
        // se esta em espera (agendada) desabiliita o botao de agendar
        else if( status.equalsIgnoreCase("em_espera")) {
        	
        	btnEspera.setDisable(true);
        }

        // ===== AÇÕES DOS BOTÕES =====

        // Cancelar consulta
        btnCancelar.setOnAction(e -> {
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
            try {
                consultas.marcarEmEspera(consulta.getId());

                mensagemFeedback.setText("Consulta marcada como EM ESPERA");
                mensagemFeedback.setTextFill(Color.ORANGE);

                carregarConsultas();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // VBox para organizar os botões verticalmente
        VBox botoes = new VBox(5, btnCancelar, btnConcluir, btnEspera);

        // ===== CARD VISUAL =====

        // HBox organiza horizontalmente (info + botões)
        HBox card = new HBox(20);

        card.setPadding(new Insets(15));         // espaçamento interno
        card.setAlignment(Pos.CENTER_LEFT);      // alinhamento

        // Adiciona os elementos no card
        card.getChildren().addAll(info, botoes);

        // Estilo do card
        card.setStyle(
            "-fx-background-color: #1e1e1e;" +   // fundo escuro
            "-fx-background-radius: 10;"         // bordas arredondadas
        );

        return card;
    }
}