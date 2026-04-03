package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class ConsultaDAO {
	public static void inserir(
			int idUser,
			int idMedico,
			String dataConsulta
			)throws Exception {
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
          LocalDate data = LocalDate.parse(dataConsulta, formatter);
		Connection conn=conexaoBanco.conectar();
		String sql="INSERT INTO consultas (fk_usuario,fk_medico,data_consulta) VALUES (?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1,idUser);
		
		ps.setInt(2, idMedico);
		ps.setDate(3, java.sql.Date.valueOf(data));
		
		ps.executeUpdate();
		
		ps.close();
		conn.close();
		
		
	} 

}
