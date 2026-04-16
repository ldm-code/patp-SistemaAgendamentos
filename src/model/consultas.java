package model;
import java.time.LocalDate;
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

	

	public static String cadastrarConsultas(
	        int idUsuario, 
	        int idMed,
	        LocalDateTime dataConsultas
	) {
	    try {

	        if (dataConsultas == null) {
	            return "Data inválida ou inexistente.";
	        }

	        if (dataConsultas.isBefore(LocalDateTime.now())) {
	            return "Data no passado não permitida.";
	        }

	        Consulta c = new Consulta();
	        c.setDataConsulta(dataConsultas);

	        if (!c.isHorarioValido()) {
	            return "Horário inválido!";
	        }

	        if (medicoTemConsultaNoHorario(idMed, dataConsultas)) {
	            return "Horário já ocupado!";
	        }

	        ConsultaDAO.inserir(idUsuario, idMed, dataConsultas);

	        return "Consulta marcada com sucesso!";

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Erro no cadastro!";
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
	public static void marcarAgendamento(int id) throws Exception{
		ConsultaDAO.atualizarConsulta(id,"agendada");
		
	}

}
