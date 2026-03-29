package dao;
import java.sql.Connection;
import model.MedicosSelect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class medicosDAO {
	public static void inserir(

		     String nome,
		     String tipo
		 ) throws Exception {
	    Connection conn = conexaoBanco.conectar();
	    String sql="INSERT INTO medicos (nome, tipo) VALUES (?, ?);";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, nome);
        ps.setString(2, tipo);

        int linhasAfetadas = ps.executeUpdate();
        if (linhasAfetadas > 0) {

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                long idGerado = generatedKeys.getLong(1);
                System.out.println("ID gerado: " + idGerado);
            }
        }

        ps.close(); 
	}
	public static List<MedicosSelect> select()  throws Exception{
		    List<MedicosSelect> lista = new ArrayList<>();
		    Connection conn = conexaoBanco.conectar();
		    
			    
		    	
		   String sql = "SELECT id, nome, tipo FROM medicos";
           PreparedStatement ps = conn.prepareStatement(sql);
           ResultSet rs=ps.executeQuery();
           while (rs.next()) {
        	   MedicosSelect m=new MedicosSelect();
        	   m.setId(rs.getInt("id"));
               m.setNome(rs.getString("nome"));
               m.setTipo(rs.getString("tipo"));

        	   
        	   lista.add(m);
        	   
           }
           rs.close();
           ps.close();
           return lista;

		    
		
	}


}

