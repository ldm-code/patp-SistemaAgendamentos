package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class conexaoBanco {
	private static final String URL="jdbc:mysql://localhost:3306/agendamentoPATP";
	private static final String USER="root";
	private static final String  PASSWORD="root";
	public static Connection conectar() {
		try {
			Connection conn=DriverManager.getConnection(URL,USER ,PASSWORD);
			
		
			   System.out.println("conectou");
	            return conn;
		 } catch (SQLException e) {
	            System.out.println("Erro ao conectar ao banco de dados");
	            e.printStackTrace();
	            return null;
	        }
	    }
		
	}
	
	


