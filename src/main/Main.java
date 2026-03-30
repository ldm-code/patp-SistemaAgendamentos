package main;

import java.util.List;
import dao.medicosDAO;
import utils.enviarEmail;
import model.MedicosSelect;

public class Main {

	   public static void main(String[] args) throws Exception {
            enviarEmail.enviar("demoraesleonardo327@gmail.com");
	        List<MedicosSelect> medicos = medicosDAO.select();

	        for (MedicosSelect m : medicos) {
	            System.out.println(
	                m.getId() + " - " +
	                m.getNome() + " - " +
	                m.getTipo()
	            );
	            
      
	}

}
}