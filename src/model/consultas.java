package model;


import dao.ConsultaDAO;

public class consultas {
	public static void cadastrarConsultas(
			int idUsuario, 
			int idMed,
			String dataConsultas
		
			)  {
		 try {
			
			 
		if (dataConsultas == null || dataConsultas.isEmpty()) {
			System.out.println("Data final inválida ou inexistente.");
		}


		ConsultaDAO.inserir(idUsuario, idMed, dataConsultas);
		System.out.println("Consulta marcada com sucesso!");
		 }catch (Exception e){
			  System.out.println("Erro no cadastro:");
              e.printStackTrace();
		 }
	}

}
