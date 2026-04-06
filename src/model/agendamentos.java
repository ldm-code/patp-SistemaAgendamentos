package model;
import dao.agendamentosDAO;
import List.Agendamentos;
import java.util.List;
public class agendamentos {


	    public static void exibirConsultasTerminal() {
	        try {


	            List<Agendamentos> lista = agendamentosDAO.listarAgendamentosConcluidos();

	            // 2. Verifica se veio vazio
	            if (lista == null || lista.isEmpty()) {
	                System.out.println("Nenhuma consulta encontrada.");
	                return;
	            }

	            for (Agendamentos c : lista) {

	                System.out.println("=================================");
	                System.out.println("Agendamento: " + c.getIdAgendamento());
	                System.out.println("Consulta: " + c.getIdConsulta());
	                System.out.println("Usuário: " + c.getNomeUsuario());
	                System.out.println("Médico: " + c.getNomeMedico());
	                System.out.println("Tipo: " + c.getTipoMedico());
	                System.out.println("Data: " + c.getDataConsulta());
	                System.out.println("Status: " + c.getStatus());
	            }

	        } catch (Exception e) {
	            System.out.println("Erro ao exibir consultas:");
	            e.printStackTrace();
	        }
	    }
	}

