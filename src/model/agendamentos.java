package model;
import dao.agendamentosDAO;
import List.Agendamentos;
import java.util.List;
public class agendamentos {



	    public static List<Agendamentos> listarConcluidos() {
	        try {
	            return agendamentosDAO.listarAgendamentosConcluidos();
	        } catch (Exception e) {
	            System.out.println("Erro ao buscar agendamentos");
	            e.printStackTrace();
	            return List.of();
	        }
	    }
	}

