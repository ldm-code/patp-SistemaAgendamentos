package dao;

//UsuarioDAO.java
import java.sql.Connection;
import java.sql.PreparedStatement;

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
 
}