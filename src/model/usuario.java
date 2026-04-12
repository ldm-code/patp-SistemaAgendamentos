package model;
import java.security.MessageDigest;

import List.Usuario;
import dao.UsuarioDAO;
import utils.enviarEmail;
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
	 public static boolean validarMatricula(String matricula) {
		    return matricula != null && matricula.matches("\\d+");
		}

	 public static void cadastrarUsuario(
			    String matricula,
			    String nome,
			    String email,
			    String senha,
			    String cpf
			) {

			    try {

			        // 🔹 Validação geral
			        if (matricula == null || matricula.isEmpty() ||
			            nome == null || nome.isEmpty() ||
			            email == null || email.isEmpty() ||
			            senha == null || senha.isEmpty() ||
			            cpf == null || cpf.isEmpty()) {

			            System.out.println("Todos os campos são obrigatórios");
			            return;
			        }

			        // 🔹 Email básico
			        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
			            System.out.println("Email inválido");
			            return;
			        }
                 // validacao de matricula
			        if (!usuario.validarMatricula(matricula)) {
			        	System.out.println("matricula invalida");
			        	return;
			        }
			        // 🔹 CPF (estrutura básica)
			        // limpa máscara
			        cpf = cpf.replaceAll("[^\\d]", "");

			        // valida
			        if (cpf.length() != 11) {
			            System.out.println("CPF inválido");
			            return;
			        }

			        // 🔹 Senha mínima
			        if (senha.length() < 6) {
			            System.out.println("Senha muito curta");
			            return;
			        }

			        // 🔹 Criptografia
			        String senhaCripto = usuario.gerarHash(senha);

			        // 🔹 Inserção de dados em banco
			        UsuarioDAO.inserir(matricula, nome, email, senhaCripto, cpf);

			        // 🔹 Envio de Email 
		        enviarEmail.enviar(
			            email,
			            "Bem-vindo",
			            "Seja bem-vindo ao sistema de cadastro de consultas da Cotriel"
		        );

			        System.out.println("Usuário cadastrado com sucesso!");

			    } catch (Exception e) {
			        System.out.println("Erro no cadastro:");
			        e.printStackTrace();
			    }
			}
	 // Validacao de login
	 public static Usuario validarLogin(String email, String senha) {
		    try {
		        String senhaHash = gerarHash(senha); // Criptografa o valor digitado para comparacao

		       Usuario u= UsuarioDAO.loginSelect(email, senhaHash);// faz o login
		       return u;

		    } catch (Exception e) {
		        e.printStackTrace();
		        return null;
		    }
		}
}