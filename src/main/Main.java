package main;

import java.util.List;
import dao.medicosDAO;
import utils.enviarEmail;
import model.MedicosSelect;
import model.consultas;

public class Main {

	   public static void main(String[] args) throws Exception {
            enviarEmail.enviar("nicolascole543@gmail.com","eai","se apagar e gay xD");
	        List<MedicosSelect> medicos = medicosDAO.select();

	        for (MedicosSelect m : medicos) {
	            System.out.println(
	                m.getId() + " - " +
	                m.getNome() + " - " +
	                m.getTipo()
	            );
	         
      
	}
	        consultas.cadastrarConsultas(4, 1, "11/07/2026");   

}
}