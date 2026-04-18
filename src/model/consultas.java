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

	        if (dataConsultas.isBefore(LocalDateTime.now().plusMinutes(30))) {
	        	return "Agendamento deve ser feito com 30 minutos de antecedência.";
	        }
	        if (!c.isHorarioValido()) {
	            return "Horário inválido!";
	        }

	        if (medicoTemConsultaNoHorario(idMed, dataConsultas)) {
	            return "Horário já ocupado!";
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
	public static String editarConsulta(
	        Consulta consulta,
	        LocalDateTime novaData

	) {
	    try {

	        // 🔴 1. valida nulo
	        if (novaData == null) {
	            return "Data inválida.";
	        }

	        // 🔴 2. não pode ser no passado
	        if (novaData.isBefore(LocalDateTime.now())) {
	            return "Data no passado não permitida.";
	        }

	        // 🔴 3. valida regra de negócio (reutilizando teu model)
	        Consulta temp = new Consulta();
	        temp.setDataConsulta(novaData);

	        if (!temp.isHorarioValido()) {
	            return "Horário inválido!";
	        }

	        // 🔴 4. sábado/domingo
	        if (novaData.getDayOfWeek().getValue() >= 6) {
	            return "Não há atendimento aos finais de semana!";
	        }

	        // 🔴 5. conflito com médico
	        if (medicoTemConsultaNoHorario(consulta.getIdMedico(), novaData)) {
	            return "Horário já ocupado para esse médico!";
	        }


	        // 🔴 7. conflito mesmo horário (outro médico)
	        boolean mesmoHorario = ConsultaDAO.usuarioJaTemConsultaMesmoHorario(
	                consulta.getIdUsuario(),
	                novaData
	        );

	        if (mesmoHorario) {
	            return "Você já possui outra consulta nesse horário!";
	        }

	        // 🔹 8. atualiza no banco
	        ConsultaDAO.atualizarHorario(
	                consulta.getId(),
	                novaData.toLocalDate(),
	                novaData.toLocalTime()
	        );

	        return "Consulta atualizada com sucesso!";

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Erro ao editar consulta!";
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
