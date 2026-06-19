package dao;

//UsuarioDAO.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import List.Usuario;

public class UsuarioDAO {

 public static void inserir(
     String matricula,
     String nome,
     String email,
     String senha,
     String cpf
   
 ) throws Exception {

     Connection conn = conexaoBanco.conectar();

     String sql = "INSERT INTO usuarios (matricula, nome, email, senha,cpf) VALUES (?, ?, ?, ?,?)";

     PreparedStatement stmt = conn.prepareStatement(sql);

     stmt.setString(1, matricula);
     stmt.setString(2, nome);
     stmt.setString(3, email);
     stmt.setString(4, senha);
     stmt.setString(5,cpf);
   
     stmt.executeUpdate();

     stmt.close();
     conn.close();
 }
 public static Usuario loginSelect(String email, String senha) throws Exception{
	 Connection conn = conexaoBanco.conectar();
	 String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
	 PreparedStatement stmt = conn.prepareStatement(sql);

	    stmt.setString(1, email);
	    stmt.setString(2, senha);
	    ResultSet rs = stmt.executeQuery();
	
	    if (rs.next()) {
	    	Usuario u= new Usuario();
	    	u.setId(rs.getInt("id"));
	    	u.setMatricula(rs.getString("matricula"));
	    	u.setNome(rs.getString("nome"));
	    	u.setEmail(rs.getString("email"));
	    	u.setCpf(rs.getString("cpf"));
	    	u.setTipo(rs.getString("tipo"));
	    	 rs.close();
	         stmt.close();
	         conn.close();

	         return u;
	     

	    }
	     rs.close();
	     stmt.close();
	     conn.close();

	     return null;
	    	

	 
 }
 
 public static Usuario buscarPorEmail(String email) throws Exception{
	 String sql = "SELECT * FROM usuarios WHERE email = ?";
	try( Connection conn = conexaoBanco.conectar();
	 PreparedStatement stmt = conn.prepareStatement(sql);){
		

	    stmt.setString(1, email);
	    ResultSet rs = stmt.executeQuery();
	
	    if (rs.next()) {
	    	Usuario u= new Usuario();
	    	u.setId(rs.getInt("id"));
	    	u.setMatricula(rs.getString("matricula"));
	    	u.setNome(rs.getString("nome"));
	    	u.setEmail(rs.getString("email"));
	    	u.setCpf(rs.getString("cpf"));
	    	u.setTipo(rs.getString("tipo"));
	    	 rs.close();
	         stmt.close();
	         conn.close();

	         return u;
	     

	    }
	     rs.close();
	     stmt.close();
	     conn.close();

	}
	     return null;
	    	

	 
 }

 public static Usuario buscarPorCpf(String cpf) throws Exception{
	 String sql = "SELECT * FROM usuarios WHERE cpf = ?";
try(	 Connection conn = conexaoBanco.conectar();
	 PreparedStatement stmt = conn.prepareStatement(sql);
){
	
	    stmt.setString(1, cpf);
	    ResultSet rs = stmt.executeQuery();
	
	    if (rs.next()) {
	    	Usuario u= new Usuario();
	    	u.setId(rs.getInt("id"));
	    	u.setMatricula(rs.getString("matricula"));
	    	u.setNome(rs.getString("nome"));
	    	u.setEmail(rs.getString("email"));
	    	u.setCpf(rs.getString("cpf"));
	    	u.setTipo(rs.getString("tipo"));
	    	 rs.close();
	         stmt.close();
	         conn.close();

	         return u;
	     

	    }
	     rs.close();
	     stmt.close();
	     conn.close();

}
	     return null;
	    	

	 
 }
 public static ArrayList<Usuario> buscarEmails(String email) throws Exception {

	    ArrayList<Usuario> lista = new ArrayList<>();

	    String sql = "SELECT * FROM usuarios WHERE email LIKE ? LIMIT 10";

	    try (
	        Connection conn = conexaoBanco.conectar();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {

	    	stmt.setString(1, "%" + email.trim() + "%");

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {

	            Usuario u = new Usuario();

	            u.setId(rs.getInt("id"));
	            u.setMatricula(rs.getString("matricula"));
	            u.setNome(rs.getString("nome"));
	            u.setEmail(rs.getString("email"));
	            u.setCpf(rs.getString("cpf"));
	            u.setTipo(rs.getString("tipo"));

	            lista.add(u);
	        }

	        rs.close();
	    }

	    return lista;
	}
 
 
 
}