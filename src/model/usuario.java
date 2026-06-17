package model;
import java.security.MessageDigest;

import java.util.ArrayList;

import List.Usuario;
import dao.UsuarioDAO;
import utils.SessaoUsuario;
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

	 public static String cadastrarUsuario(
		        String matricula,
		        String nome,
		        String email,
		        String senha,
		        String cpf
		) {

		    try {

		        if (matricula == null || matricula.isEmpty() ||
		            nome == null || nome.isEmpty() ||
		            email == null || email.isEmpty() ||
		            senha == null || senha.isEmpty() ||
		            cpf == null || cpf.isEmpty()) {

		            return "Todos os campos são obrigatórios";
		        }

		        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
		            return "Email inválido";
		        }

		        if (!validarMatricula(matricula)) {
		            return "Matrícula inválida";
		        }

		        cpf = cpf.replaceAll("[^\\d]", "");

		        if (cpf.length() != 11) {
		            return "CPF inválido";
		        }

		        if (senha.length() < 6) {
		            return "Senha muito curta";
		        }
		        	
		        Usuario cpfExiste=UsuarioDAO.buscarPorCpf(cpf);
		        if (cpfExiste!=null) {
		        	return "Ja existe um usuario com esse cpf";
		        }
		        Usuario usuariosCadastrados = UsuarioDAO.buscarPorEmail(email);

		        if (usuariosCadastrados != null) {
		            return "Já existe um usuário com esse email";
		        }
		        String senhaCripto = gerarHash(senha);

		        UsuarioDAO.inserir(matricula, nome, email, senhaCripto, cpf);

		        enviarEmail.enviar(
		                email,
		                "Bem-vindo",
		                "Seja bem-vindo ao sistema de cadastro de consultas da Cotriel"
		        );

		        return null; // sucesso

		    } catch (Exception e) {
		        e.printStackTrace();
		        return "Erro ao cadastrar usuário";
		    }
		}
	 // Validacao de login
	 public static String validarLogin(String email, String senha) {
		    try {

		        if (email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
		            return "Preencha todos os campos!";
		        }

		        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
		            return "Email inválido!";
		        }

		        String senhaHash = gerarHash(senha);

		        Usuario u = UsuarioDAO.loginSelect(email, senhaHash);

		        if (u == null) {
		            return "Email ou senha incorretos!";
		        }

		        // ✔ NÃO mexe com sessão aqui
		        return null;

		    } catch (Exception e) {
		        e.printStackTrace();
		        return "Erro interno no sistema!";
		    }
		}
	 public static Usuario buscarUsuario(String email, String senha) throws Exception {
		    String senhaHash = gerarHash(senha);
		    return UsuarioDAO.loginSelect(email, senhaHash);
		}
}