package main;
import java.util.List;
import model.usuario;
import dao.medicosDAO;
import model.MedicosSelect;

public class Main {

	   public static void main(String[] args) throws Exception {
		    usuario.cadastrarUsuario("312233","victor", "vic@gmail.com", "123456", "adm");

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