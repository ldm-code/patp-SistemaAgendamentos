package main;

import java.util.List;

import dao.ConsultaDAO;
import dao.medicosDAO;
import utils.enviarEmail;
import model.Consulta;
import model.MedicosSelect;
import model.consultas;

public class Main {

	   public static void main(String[] args) throws Exception {
            
	        List<MedicosSelect> medicos = medicosDAO.select();

	        for (MedicosSelect m : medicos) {
	            System.out.println(
	                m.getId() + " - " +
	                m.getNome() + " - " +
	                m.getTipo()
	            );
	        
	         
      
	}
	       consultas.cadastrarConsultas(3, 1, "11/09/2026 17:30");
	       consultas.cadastrarConsultas(3, 1, "11/09/2026 14:30");
	       
	       
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

}
}