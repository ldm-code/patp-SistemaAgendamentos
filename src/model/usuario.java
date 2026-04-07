package model;
import java.security.MessageDigest;
import dao.UsuarioDAO;
import javafx.scene.text.Text;

//UsuarioService.java
public class usuario {
	 public static String gerarHash(String texto) throws Exception {
		 MessageDigest md=MessageDigest.getInstance("SHA-256");
		 byte[] hash=md.digest(texto.getBytes());
		 StringBuilder hex=new StringBuilder();
		 for(byte b:hash) {
			 String hexString=Integer.toHexString(0xff & b);
			 if(hexString.length()==1) {
				 hex.append('0');
			 }
			 hex.append(hexString);
			 
		 }
		 return hex.toString();
	 }

 public static void cadastrarUsuario(
     String matricula,
     String nome,
     String email,
     String senha

 ) {

     try {

         if (nome == null || nome.isEmpty()) {
             System.out.println("Nome obrigatório");
             return;
         }

         if (!email.contains("@")) {
             System.out.println("Email inválido");
             return;
         }
         String senhaCripto=usuario.gerarHash(senha);


         UsuarioDAO.inserir(matricula, nome, email,senhaCripto);

         System.out.println("Usuário cadastrado com sucesso!");

     } catch (Exception e) {
         System.out.println("Erro no cadastro:");
         e.printStackTrace();
     }
 }
}
