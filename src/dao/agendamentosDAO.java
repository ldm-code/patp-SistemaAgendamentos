package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import List.Agendamentos;
public class agendamentosDAO  {
	public static void inserirAgendamento(int idConsulta) throws Exception {
		Connection conn = conexaoBanco.conectar();
		
		String sql = "INSERT INTO agendamentos(fk_consulta) VALUES(?)";
		
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    stmt.setInt(1,idConsulta);
	    
	    stmt.executeUpdate();

	    stmt.close();
	    conn.close();
	}
	
		  public static List<Agendamentos> listarAgendamentosConcluidos() throws Exception {

		        List<Agendamentos> lista = new ArrayList<>();

		        String sql = """
		            SELECT 
    a.id AS id_agendamento,
    c.id AS id_consulta,
    u.nome AS nome_usuario,
    m.nome AS nome_medico,
    m.tipo AS tipo_medico,
    c.data_consulta,
    c.status
FROM agendamentos a
INNER JOIN consultas c ON a.fk_consulta = c.id
INNER JOIN usuarios u ON c.fk_usuario = u.id
INNER JOIN medicos m ON c.fk_medico = m.id
WHERE  c.status="concluida";
		        """;

		        Connection conn = conexaoBanco.conectar();
		        PreparedStatement stmt = conn.prepareStatement(sql);
		        ResultSet rs = stmt.executeQuery();

		        while (rs.next()) {

		            Agendamentos c = new Agendamentos();

		            c.setIdConsulta(rs.getInt("id_consulta"));
		            c.setNomeUsuario(rs.getString("nome_usuario"));
		            c.setNomeMedico(rs.getString("nome_medico"));
		            c.setTipoMedico(rs.getString("tipo_medico"));
		            c.setDataConsulta(rs.getTimestamp("data_consulta").toLocalDateTime());
		            c.setStatus(rs.getString("status"));
		            c.setIdAgendamento(rs.getInt("id_agendamento"));

		            lista.add(c);
		        }

		        rs.close();
		        stmt.close();
		        conn.close();

		        return lista;
	}

}
