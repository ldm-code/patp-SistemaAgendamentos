package main;

import java.util.List;

import List.Consulta;
import List.MedicosSelect;
import dao.ConsultaDAO;
import dao.medicosDAO;
import utils.enviarEmail;
import model.consultas;
import model.agendamentos;


import javafx.application.Application;
import view.CadastroUsuario;
import view.CadastroUsuario;
public class Main {
    public static void main(String[] args) {
    	 try {
    		 
           Application.launch(CadastroUsuario.class, args);

            
	        List<MedicosSelect> medicos = medicosDAO.select();

	        for (MedicosSelect m : medicos) {
	            System.out.println(
	                m.getId() + " - " +
	                m.getNome() + " - " +
	                m.getTipo()
	            );
	        
	         
      
	}
	     
	       
	     
	       List<Consulta> lista = ConsultaDAO.listarConsultas();
	       for (Consulta c : lista) {

	    	    System.out.println(
	    	        c.getId() + " - " +
	    	        c.getNomeUsuario() + " - " +
	    	        c.getNomeMedico() + " - " +
	    	        c.getEspecialidade() + " - " +
	    	        c.getDataConsulta() + " - " +
	    	        c.getStatus()
	    	    );
	    	}
	
           System.out.println("consultas finalizadas");
	       agendamentos.exibirConsultasTerminal();
    	 }catch (Exception e) {
    	        e.printStackTrace();
    	    }
}
}
