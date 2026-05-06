package model;
import java.time.LocalDate;
import dao.ConsultaDAO;
import java.time.LocalDateTime;
import dao.agendamentosDAO;
import utils.DateUtil;
import utils.enviarEmail;

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
	public static boolean medicoAtendeNoDia(int idMedico, LocalDateTime data) throws Exception {

	    int diaConsulta = data.getDayOfWeek().getValue(); // 1-7

	    List<String> diasDoMedico = dao.MedicoDiasDAO.buscarDias(idMedico);

	    for (String dia : diasDoMedico) {
	        int diaBanco = utils.ValidacaoMedicoUtils.converterDia(dia);

	        if (diaBanco == diaConsulta) {
	            return true;
	        }
	    }

	    return false;
	}

	

	public static String cadastrarConsultas(
	        int idUsuario, 
	        int idMed,
	        LocalDateTime dataConsultas,
	        String email
	) {
	    try {
	    	if (!medicoAtendeNoDia(idMed, dataConsultas)) {
	    	    return "Médico não atende neste dia!";
	    	}

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
	        String dataFormatada = DateUtil.format(dataConsultas);

            enviarEmail.enviar(email, "Consulta Agendada",
            	    "Sua consulta foi agendada para: " + dataFormatada+ "\n\n" +
            	    	    "Atenciosamente,\n" +
            	    	    "Equipe de Atendimento.\n"+"\n");
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
	    	if (!medicoAtendeNoDia(consulta.getIdMedico(), novaData)) {
	    	    return "Médico não atende neste dia!";
	    	}

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
	        boolean ValidarHora= ConsultaDAO.medicoJaTemConsultaEdicao(consulta.getIdMedico(), novaData, consulta.getId());

	        if (ValidarHora) {
	            return "Você já possui outra consulta nesse horário!";
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
	        String dataFormatada = DateUtil.format(novaData);
	        String email=ConsultaDAO.buscarEmailPorConsulta(consulta.getId());
	        enviarEmail.enviar(email, "Consulta Reagendada", " Sua consulta acaba de ser reagendada para "+
	        dataFormatada +"\n" +"Para mais informacoes, Contate o seguinte ramal:\n"+"\n"+
	        		"Para agendar com dentista:4455.\n"+
	        		"Para agendar com outro medico:3347.\n"
	        		+"\n \n"+
	        		"Atenciosamente,\n" +
	        		"Equipe de Atendimento.\n"+"\n"
	        
	        );
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
	public static void cancelarConsulta(int id,String motivo)throws Exception {
		ConsultaDAO.atualizarConsulta(id,"cancelada");
		agendamentosDAO.inserirAgendamentoCancelado(id,motivo);
		String email=ConsultaDAO.buscarEmailPorConsulta(id);
		enviarEmail.enviar(email, " Consulta Cancelada","Sua consulta foi canecalda por um adiministrador do sistema.\n"
				+ "Para mais informacoes, Contate o seguinte ramal:"+"\n"
				+"Para agendar com dentista:4455.\n"+
        		"Para agendar com outro medico:3347.\n"+"\n\n"
				+ "Atenciosamente\n"
				+ "Equipe de atendimento.\n"+"\n");
		System.out.println("consulta cancelada com sucesso");
	}
	public static void concluirConsulta(int id)throws Exception {
		agendamentosDAO.inserirAgendamento(id);
		ConsultaDAO.atualizarConsulta(id,"concluida");
	}
	public static String marcarAgendamento(int id, int idMedico, LocalDateTime data, int idUsuario) throws Exception {

	    // 🔴 1. valida conflito do médico
	    boolean medicoOcupado = ConsultaDAO.medicoJaTemConsultaEdicao(idMedico, data, id);

	    if (medicoOcupado) {
	        return "Este médico já possui consulta nesse horário!";
	    }

	    // 🔴 2. valida conflito do usuário com mesmo horário
	    boolean usuarioOcupado = ConsultaDAO.usuarioJaTemConsultaMesmoHorario(idUsuario, data);

	    if (usuarioOcupado) {
	        return "Você já possui uma consulta nesse horário!";
	    }

	    // 🔴 3. atualiza status
	    ConsultaDAO.atualizarConsulta(id, "agendada");

	    return "Consulta marcada como agendada!";
	}

}
