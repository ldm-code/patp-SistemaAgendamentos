package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import List.MedicosSelect;
public class medicosDAO {
	public static int inserir(String nome, String tipo) throws Exception {

	    Connection conn = conexaoBanco.conectar();

	    String sql = "INSERT INTO medicos (nome, tipo) VALUES (?, ?)";

	    PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

	    ps.setString(1, nome);
	    ps.setString(2, tipo);

	    ps.executeUpdate();

	    ResultSet rs = ps.getGeneratedKeys();

	    int idGerado = 0;

	    if (rs.next()) {
	        idGerado = rs.getInt(1);
	    }

	    rs.close();
	    ps.close();

	    return idGerado;
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

