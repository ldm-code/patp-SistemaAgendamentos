package view;

import javafx.geometry.Insets;
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
import dao.ConsultaDAO;
import model.consultas;

public class TelaConsultas {

    private VBox listaConsultas = new VBox(10);

    public void start(Stage stage) {

        // ===== TÍTULO =====
        Label titulo = new Label("Consultas");
        titulo.setFont(new Font("Arial", 28));
        titulo.setTextFill(Color.web("#FFD700"));

        // ===== BOTÃO AGENDAR =====
        Button btnAgendar = new Button("Agendar Consulta");
        btnAgendar.setStyle(
                "-fx-background-color: #FFD700;" +
                "-fx-text-fill: black;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;"
        );

        btnAgendar.setOnAction(e -> {
            System.out.println("abrir tela de agendamento");
        });

        // ===== LISTA =====
        listaConsultas.setPadding(new Insets(10));

        ScrollPane scroll = new ScrollPane(listaConsultas);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent;");

        carregarConsultas();

        // ===== LAYOUT =====
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);

        layout.getChildren().addAll(titulo, btnAgendar, scroll);

        layout.setStyle("-fx-background-color: #0f3d2e;");

        Scene scene = new Scene(layout, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Consultas");
        stage.show();
    }

    // ===== MÉTODO QUE BUSCA DO BANCO =====
    private void carregarConsultas() {

        listaConsultas.getChildren().clear();

        List<Consulta> consultas;
		try {
			consultas = ConsultaDAO.listarConsultas();

        for (Consulta c : consultas) {
            listaConsultas.getChildren().add(criarCardConsulta(c));
        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // ===== CARD DE CADA CONSULTA =====
    private HBox criarCardConsulta(Consulta consulta) {

    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    	Label info = new Label(
    	    "Paciente: " + consulta.getNomeUsuario() +
    	    "\nMédico: " + consulta.getNomeMedico() +
    	    "\nEspecialidade: " + consulta.getEspecialidade() +
    	    "\nData: " + consulta.getDataConsulta().format(formatter) +
    	    "\nStatus: " + consulta.getStatus()
    	);

        info.setTextFill(Color.WHITE);

        Button btnCancelar = new Button("Cancelar");
        Button btnConcluir = new Button("Concluir");

        // ===== ESTILO BOTÕES =====
        btnCancelar.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
        btnConcluir.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        // ===== AÇÕES =====
        btnCancelar.setOnAction(e -> {
        	
            try {
				consultas.cancelarConsulta(consulta.getId());
                carregarConsultas(); // atualiza lista
            } catch (Exception e1) {
            	// TODO Auto-generated catch block
            	e1.printStackTrace();
            }
        });

        btnConcluir.setOnAction(e -> {
            try {
				consultas.concluirConsulta(consulta.getId());
                carregarConsultas();
            } catch (Exception e1) {
            	// TODO Auto-generated catch block
            	e1.printStackTrace();
            }
        });

        VBox botoes = new VBox(5, btnCancelar, btnConcluir);

        HBox card = new HBox(20);
        card.setPadding(new Insets(15));
        card.setAlignment(Pos.CENTER_LEFT);

        card.getChildren().addAll(info, botoes);

        card.setStyle(
                "-fx-background-color: #1e1e1e;" +
                "-fx-background-radius: 10;"
        );

        return card;
    }
}
