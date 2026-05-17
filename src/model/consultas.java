package model;
import java.time.Duration;
import java.time.LocalDate;
import dao.ConsultaDAO;
import java.time.LocalDateTime;
import java.time.LocalTime;

import dao.agendamentosDAO;
import dao.medicosDAO;
import utils.DateUtil;
import utils.enviarEmail;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
		enviarEmail.enviar(email, " Consulta Cancelada","Sua consulta foi cancelada por um administrador do sistema.\n"
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
	public static List<String> buscarHorariosDisponiveis(
	        int idMedico,
	        LocalDate data
	) throws Exception {

	    LocalDateTime dataTeste =
	            data.atTime(8,0);

	    // 🔥 Médico não atende nesse dia
	    if(!medicoAtendeNoDia(
	            idMedico,
	            dataTeste
	    )) {

	        return new ArrayList<>();
	    }

	    List<String> todosHorarios = List.of(
	        "08:00","08:30",
	        "09:00","09:30",
	        "10:00","10:30",
	        "11:00","11:30",
	        "13:00","13:30",
	        "14:00","14:30",
	        "15:00","15:30",
	        "16:00","16:30",
	        "17:00","17:30"
	    );

	    List<LocalTime> ocupados =
	        ConsultaDAO.buscarHorariosOcupados(
	            idMedico,
	            data
	        );

	    List<String> disponiveis =
	        new ArrayList<>();

	    for(String horario : todosHorarios) {

	        LocalTime hora =
	            LocalTime.parse(horario);

	        if(!ocupados.contains(hora)) {

	            disponiveis.add(horario);
	        }
	    }

	    return disponiveis;
	}
	public static void enviarLembretesConsultas()
			throws Exception {

			    // 🔹 Busca todas as consultas
			    List<Consulta> lista =
			        ConsultaDAO.listarConsultas();

			    // 🔹 Percorre todas
			    for(Consulta consulta : lista) {

			        // =====================================
			        // 1. Apenas consultas AGENDADAS
			        // =====================================

			        if(!consulta.getStatus()
			                .equalsIgnoreCase("agendada")) {

			            continue;
			        }

			        // =====================================
			        // 2. Data da consulta
			        // =====================================

			        LocalDateTime dataConsulta =
			            consulta.getDataConsulta();

			        // =====================================
			        // 3. Data atual
			        // =====================================

			        LocalDateTime agora =
			            LocalDateTime.now();

			        // =====================================
			        // 4. Diferença entre agora e consulta
			        // =====================================

			        Duration diferenca =
			            Duration.between(
			                agora,
			                dataConsulta
			            );

			        // =====================================
			        // 5. Converte para horas
			        // =====================================

			        long horas =
			            diferenca.toHours();

			        // =====================================
			        // 6. Verifica se falta entre
			        //    0h e 24h
			        // =====================================
			        boolean enviado =
			        	    ConsultaDAO
			        	        .lembreteJaEnviado(
			        	            consulta.getId()
			        	        );

			        if(enviado) {
			        	    continue;
			        	}
			        if(horas <= 24 && horas >=0) {

			            // 🔹 Busca email do usuário
			            String email =
			                ConsultaDAO
			                    .buscarEmailPorConsulta(
			                        consulta.getId()
			                    );
			            String nomeMedico =medicosDAO.buscarNomePorId(consulta.getIdMedico());

			            // 🔹 Formata data
			            String dataFormatada =
			                DateUtil.format(
			                    dataConsulta
			                );

			            // 🔹 Envia email
			            enviarEmail.enviar(
			                email,
			                "Lembrete de Consulta",
			                "Olá!\n\n" +

			                "Este é um lembrete da sua consulta agendada com o medico "+ nomeMedico +" marcada para:\n\n"

			                + dataFormatada +

			                "\n\nAtenciosamente,\n" +
			                "Equipe de Atendimento.\n\n"
			            );

			            ConsultaDAO.marcarLembreteEnviado(
			            		consulta.getId()
			            		);
			            System.out.println(
			                "Lembrete enviado para consulta ID: "
			                + consulta.getId()
			            );
			        }
			    }
			}
}
