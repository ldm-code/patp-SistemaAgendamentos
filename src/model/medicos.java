package model;


import dao.medicosDAO;
public class medicos {
	public static void cadastrarMedico(String nome,String tipo) {
		 try {

	         if (nome == null || nome.isEmpty()) {
	             System.out.println("Nome obrigatório");
	             return;
	             }
	         else if(tipo==null) {
	            	 System.out.println("tipo obrigatorio");
	             }
	         medicosDAO.inserir(nome, tipo);
	         System.out.println("medico cadastrado com sucesso");
	         }catch  (Exception e){
	        	  System.out.println("Erro no cadastro:");
	              e.printStackTrace();
	         }
		
	}
	


}

