package dao;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import List.Consulta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConsultaDAO {
	public static void inserir(
	        int idUser,
	        int idMedico,
	        LocalDateTime dataConsulta
	) throws Exception {

	    Connection conn = conexaoBanco.conectar();

	    String sql = "INSERT INTO consultas (fk_usuario,fk_medico,data_consulta) VALUES (?, ?, ?)";

	    PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	    ps.setInt(1, idUser);
	    ps.setInt(2, idMedico);

	    // 🔥 Aqui está o ponto importante
	    ps.setObject(3, dataConsulta);

	    ps.executeUpdate();

	    ps.close();
	    conn.close();
	}
	public static void atualizarConsulta(int id,
			String status)throws Exception {
		Connection conn=conexaoBanco.conectar();
		String sql="UPDATE consultas SET status = ? WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1,status);
		stmt.setInt(2, id);
		int linhasAfetadas = stmt.executeUpdate();
		if (linhasAfetadas > 0) {
              System.out.println("Atualizado com sucesso!");
        } else {
              System.out.println("Nenhum registro encontrado.");
        }

	}
	public static List<Consulta> listarConsultas() throws Exception {

	    List<Consulta> lista = new ArrayList<>();

	    Connection conn = conexaoBanco.conectar();

	    String sql = """
	        SELECT 
	            c.id,
	            u.nome AS nome_usuario,
	            m.nome AS nome_medico,
	            m.tipo AS especialidade,
	            c.data_consulta,
	            c.status,
	            c.fk_medico
	        FROM consultas c
	        JOIN usuarios u ON c.fk_usuario = u.id
	        JOIN medicos m ON c.fk_medico = m.id
	    """;

	    PreparedStatement ps = conn.prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {

	        int id = rs.getInt("id");
	        String usuario = rs.getString("nome_usuario");
	        String medico = rs.getString("nome_medico");
	        String especialidade = rs.getString("especialidade");

	        LocalDateTime data = rs.getTimestamp("data_consulta").toLocalDateTime();

	        String status = rs.getString("status");
	        int idMedico = rs.getInt("fk_medico");

	      
	        Consulta consulta = new Consulta(
	            id, usuario, medico, especialidade, data, status,idMedico
	        );

	        lista.add(consulta);
	    }

	    rs.close();
	    ps.close();
	    conn.close();

	    return lista;
	}
	public static List<Consulta> listarConsultasUser(int idUsuario) throws Exception {

	    List<Consulta> lista = new ArrayList<>();

	    Connection conn = conexaoBanco.conectar();

	    String sql = """
	        SELECT 
	            c.id,
	            u.nome AS nome_usuario,
	            m.nome AS nome_medico,
	            m.tipo AS especialidade,
	            c.data_consulta,
	            c.status,
	            c.fk_medico
	        FROM consultas c
	        JOIN usuarios u ON c.fk_usuario = u.id
	        JOIN medicos m ON c.fk_medico = m.id
	        WHERE c.fk_usuario = ?
	    """;

	    PreparedStatement ps = conn.prepareStatement(sql);

	
	    ps.setInt(1, idUsuario);

	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {

	        int id = rs.getInt("id");
	        String usuario = rs.getString("nome_usuario");
	        String medico = rs.getString("nome_medico");
	        String especialidade = rs.getString("especialidade");

	        LocalDateTime data = rs.getTimestamp("data_consulta").toLocalDateTime();

	        String status = rs.getString("status");
	        int idMedico = rs.getInt("fk_medico");

	        Consulta consulta = new Consulta(
	            id, usuario, medico, especialidade, data, status, idMedico
	        );

	        lista.add(consulta);
	    }

	    rs.close();
	    ps.close();
	    conn.close();

	    return lista;
	}


}
