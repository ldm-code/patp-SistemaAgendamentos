package model;
import java.time.LocalDateTime;
import dao.agendamentosDAO;
import java.time.format.DateTimeFormatter;
import java.util.List;

import List.Consulta;
import dao.ConsultaDAO;


public class consultas {
	public static boolean medicoTemConsultaNoHorario(int idMedico, LocalDateTime data) throws Exception {
	    List<Consulta> lista = ConsultaDAO.listarConsultas();
	    for (Consulta c : lista) {
	     
	        if (c.getIdMedico() == idMedico && c.getDataConsulta().equals(data)) {
	            return true; 
	        }
	    }
	    return false; 
	}

	

	public static void cadastrarConsultas(
			int idUsuario, 
			int idMed,
			String dataConsultas
		
			)  {
		 try {
			
			 
		if (dataConsultas == null || dataConsultas.isEmpty()) {
			System.out.println("Data final inválida ou inexistente.");
		}
		LocalDateTime agora = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime data = LocalDateTime.parse(dataConsultas, formatter);

		Consulta c = new Consulta();
		c.setDataConsulta(data);


		if (!c.isHorarioValido()|| medicoTemConsultaNoHorario(idMed, data) || data.isBefore(agora)) {
		    System.out.println("Horário inválido!,escolha outro");
		}
		else {
			
		ConsultaDAO.inserir(idUsuario, idMed, dataConsultas);
		System.out.println("Consulta marcada com sucesso!");
		}
		 }catch (Exception e){
			  System.out.println("Erro no cadastro:");
              e.printStackTrace();
		 }
	}
	public static void cancelarConsulta(int id)throws Exception {
		ConsultaDAO.atualizarConsulta(id,"cancelada");
		System.out.println("consulta cancelada com sucesso");
	}
	public static void concluirConsulta(int id)throws Exception {
		agendamentosDAO.inserirAgendamento(id);
		ConsultaDAO.atualizarConsulta(id,"concluida");
	}

}
