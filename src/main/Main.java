package main;

import java.util.List;
import dao.medicosDAO;
import utils.enviarEmail;
import model.MedicosSelect;
import model.consultas;

public class Main {

	   public static void main(String[] args) throws Exception {
            enviarEmail.enviar("demoraesleonardo327@gmail.com","eai","xD");
	        List<MedicosSelect> medicos = medicosDAO.select();

	        for (MedicosSelect m : medicos) {
	            System.out.println(
	                m.getId() + " - " +
	                m.getNome() + " - " +
	                m.getTipo()
	            );
	         
      
	}
	        consultas.cancelarConsulta(1);  
	        consultas.concluirConsulta(2);

}
}