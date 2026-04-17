package model;
import java.time.LocalDate;
import dao.ConsultaDAO;
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
	     
	        if (c.getIdMedico() == idMedico && c.getDataConsulta().equals(data) && c.getStatus().equalsIgnoreCase("agendada")) {
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
	        if (dataConsultas.isBefore(LocalDateTime.now().plusHours(1))) {
	            return "Agendamento deve ser feito com 1h de antecedência.";
	        }
	        if (dataConsultas.getDayOfWeek().getValue() == 7) {
	            return "Não há atendimento aos domingos!";
	        }
	        if (dataConsultas.getDayOfWeek().getValue() == 6) {
	            return "Não há atendimento aos sábados!";
	        }
	        boolean existe = ConsultaDAO.usuarioJaTemConsulta(
                    idUsuario,
                    idMed, 
                    dataConsultas
                    
                   
            );
	        boolean marcada=ConsultaDAO.usuarioJaTemConsultaMesmoHorario(idUsuario, dataConsultas);
	        if (existe) { 
	            return "Você já possui uma consulta com esse médico em um horário proximo!";
	        }
	        if(marcada) {
	        	  return "Você já possui uma consulta com outro médico nesse horário !";
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
