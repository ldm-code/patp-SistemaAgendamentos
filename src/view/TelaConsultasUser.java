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
import utils.DateUtil;
import utils.SessaoUsuario;
import model.consultas;

public class TelaConsultasUser {
	  private VBox listaConsultas = new VBox(10);

	    public void start(Stage stage) {

	        // ===== TÍTULO =====
	        Label titulo = new Label("Consultas");
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
	        AnchorPane root = new AnchorPane();

	     // ===== SEU LAYOUT PRINCIPAL =====
	     VBox layout = new VBox(15);
	     layout.setPadding(new Insets(60, 20, 20, 20));
	     layout.setAlignment(Pos.TOP_CENTER);
	     layout.getChildren().addAll(titulo, scroll);
	     layout.setStyle("-fx-background-color: #0f3d2e;");

	     // Faz o layout ocupar toda a tela
	     AnchorPane.setTopAnchor(layout, 0.0);
	     AnchorPane.setBottomAnchor(layout, 0.0);
	     AnchorPane.setLeftAnchor(layout, 0.0);
	     AnchorPane.setRightAnchor(layout, 0.0);

	     // ===== BOTÃO NO CANTO =====
	     Button botaoVoltar = new Button("Voltar");

	     // Estilo (opcional, combinando com seu padrão)
	     botaoVoltar.setStyle(
	         "-fx-background-color: #FFD700;" +
	         "-fx-text-fill: black;" +
	         "-fx-font-weight: bold;"
	     );

	     // Posiciona no canto superior esquerdo
	     AnchorPane.setTopAnchor(botaoVoltar, 10.0);
	     AnchorPane.setLeftAnchor(botaoVoltar, 10.0);
         botaoVoltar.setOnAction(e->{
        	 new LoginUsuario().start(stage);
         });	   
         // ===== ADICIONA TUDO =====
	     root.getChildren().addAll(layout, botaoVoltar);
      
	     // Cena
	     Scene scene = new Scene(root, 1366, 700);
	        stage.setScene(scene);
	        stage.setTitle("Consultas");
	        stage.show();
	    }
	    private HBox criarCardVazio() {

	        Label mensagem = new Label("Nenhuma consulta agendada");
	        mensagem.setTextFill(Color.WHITE);
	        mensagem.setFont(new Font("Arial", 16));

	        Label dica = new Label("Contate um administrador para marcar uma consulta");
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

	    // ===== MÉTODO QUE BUSCA DO BANCO =====
	    private void carregarConsultas() {

	        listaConsultas.getChildren().clear();

	        try {
	            int id = SessaoUsuario.usuarioLogado.getId();
	            List<Consulta> consultas = ConsultaDAO.listarConsultasUser(id);

	            // 🔥 CASO NÃO TENHA CONSULTAS
	            if (consultas.isEmpty()) {
	            	HBox wrapper = new HBox(criarCardVazio());
	            	wrapper.setAlignment(Pos.CENTER);
	            	listaConsultas.getChildren().add(wrapper);
	                return;
	            }

	            // 🔹 CASO TENHA CONSULTAS
	            for (Consulta c : consultas) {
	             	HBox wrapper = new HBox(criarCardConsulta(c));
	            	wrapper.setAlignment(Pos.CENTER);

	            	listaConsultas.getChildren().add(wrapper);
	            }

	        } catch (Exception e) {
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
	    	    "\nData: " + DateUtil.format(consulta.getDataConsulta()) +
	    	    "\nStatus: " + consulta.getStatus()
	    	);

	        HBox card = new HBox(20);
	        card.setPadding(new Insets(15));
	        card.setAlignment(Pos.CENTER_LEFT);

	        card.getChildren().addAll(info);

	        card.setStyle(
	                "-fx-background-color: #1e1e1e;" +
	                "-fx-background-radius: 10;"
	        );
	        card.setPrefWidth(400);
	        card.setMaxWidth(400);

	        return card;
	    }
}
