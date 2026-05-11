package dao;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import List.Consulta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

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
	    ps.setObject(3, dataConsulta);

	    ps.executeUpdate();

	    ps.close();
	    conn.close();
	}

	public static void atualizarConsulta(
			int id,
			String status)throws Exception {

		Connection conn=conexaoBanco.conectar();

		String sql="UPDATE consultas SET status = ? WHERE id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1,status);
		stmt.setInt(2,id);

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
	            c.data_agendamento,
	            c.status,
	            c.fk_medico,
	            c.fk_usuario
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

	        LocalDateTime data =
	            rs.getTimestamp(
	                "data_consulta"
	            ).toLocalDateTime();

	        LocalDateTime dataAgendada =
	            rs.getTimestamp(
	                "data_agendamento"
	            ).toLocalDateTime();

	        String status = rs.getString("status");
	        int idMedico = rs.getInt("fk_medico");
	        int idUser=rs.getInt("fk_usuario");

	        Consulta consulta = new Consulta(
	            id,
	            usuario,
	            medico,
	            especialidade,
	            data,
	            dataAgendada,
	            status,
	            idMedico,
	            idUser
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
	            u.id AS id_usuario,
	            u.nome AS nome_usuario,
	            m.nome AS nome_medico,
	            m.tipo AS especialidade,
	            c.data_consulta,
	            c.status,
	            c.fk_medico,
	            c.data_agendamento
	        FROM consultas c
	        JOIN usuarios u ON c.fk_usuario = u.id
	        JOIN medicos m ON c.fk_medico = m.id
	        WHERE c.fk_usuario = ? AND c.status = "agendada"
	    """;

	    PreparedStatement ps = conn.prepareStatement(sql);

	    ps.setInt(1,idUsuario);

	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {

	        int id = rs.getInt("id");
	        String usuario = rs.getString("nome_usuario");
	        String medico = rs.getString("nome_medico");
	        String especialidade = rs.getString("especialidade");

	        LocalDateTime data =
	            rs.getTimestamp(
	                "data_consulta"
	            ).toLocalDateTime();

	        String status = rs.getString("status");

	        int idMedico = rs.getInt("fk_medico");
	        int idUser = rs.getInt("id_usuario");

	        LocalDateTime dataAgendada =
	            rs.getTimestamp(
	                "data_agendamento"
	            ).toLocalDateTime();

	        Consulta consulta = new Consulta(
	            id,
	            usuario,
	            medico,
	            especialidade,
	            data,
	            dataAgendada,
	            status,
	            idMedico,
	            idUser
	        );

	        lista.add(consulta);
	    }

	    rs.close();
	    ps.close();
	    conn.close();

	    return lista;
	}



	public static List<Consulta> buscarPorData(LocalDate data) throws Exception {

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
	            c.fk_medico,
                c.fk_usuario,
                c.data_agendamento
	        FROM consultas c
	        JOIN usuarios u ON c.fk_usuario = u.id
	        JOIN medicos m ON c.fk_medico = m.id
	        WHERE c.data_consulta BETWEEN ? AND ?
	    """;

	    PreparedStatement ps = conn.prepareStatement(sql);

	    LocalDateTime inicio = data.atStartOfDay();
	    LocalDateTime fim = data.atTime(23,59,59);

	    ps.setTimestamp(
	        1,
	        Timestamp.valueOf(inicio)
	    );

	    ps.setTimestamp(
	        2,
	        Timestamp.valueOf(fim)
	    );

	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {

	        int id = rs.getInt("id");
	        String usuario = rs.getString("nome_usuario");
	        String medico = rs.getString("nome_medico");
	        String especialidade = rs.getString("especialidade");

	        LocalDateTime dataConsulta =
	            rs.getTimestamp(
	                "data_consulta"
	            ).toLocalDateTime();

	        LocalDateTime dataAgendada =
	            rs.getTimestamp(
	                "data_agendamento"
	            ).toLocalDateTime();

	        String status = rs.getString("status");

	        int idMedico = rs.getInt("fk_medico");
	        int idUser=rs.getInt("fk_usuario");

	        Consulta consulta = new Consulta(
	            id,
	            usuario,
	            medico,
	            especialidade,
	            dataConsulta,
	            dataAgendada,
	            status,
	            idMedico,
	            idUser
	        );

	        lista.add(consulta);
	    }

	    rs.close();
	    ps.close();
	    conn.close();

	    return lista;
	}



	public static boolean usuarioJaTemConsulta(
	        int idUsuario,
	        int idMedico,
	        LocalDateTime data) throws Exception {

	    String sql = """
	        SELECT COUNT(*)
	        FROM consultas
	        WHERE fk_usuario = ?
	        AND fk_medico = ?
	        AND status = 'agendada'
	        AND data_consulta BETWEEN ? AND ?
	    """;

	    try(Connection conn=conexaoBanco.conectar();
	        PreparedStatement ps=
	        conn.prepareStatement(sql)){

	        ps.setInt(1,idUsuario);
	        ps.setInt(2,idMedico);

	        LocalDateTime inicio =
	            data.minusHours(1);

	        LocalDateTime fim =
	            data.plusHours(1);

	        ps.setTimestamp(
	            3,
	            Timestamp.valueOf(inicio)
	        );

	        ps.setTimestamp(
	            4,
	            Timestamp.valueOf(fim)
	        );

	        try(ResultSet rs=ps.executeQuery()){
	            return rs.next() &&
	                   rs.getInt(1)>0;
	        }
	    }
	}



	public static boolean usuarioJaTemConsultaMesmoHorario(
	        int idUsuario,
	        LocalDateTime data
	)throws Exception{

	    String sql="""
	        SELECT COUNT(*)
	        FROM consultas
	        WHERE fk_usuario = ?
		    AND status='agendada'
	        AND data_consulta = ?
	    """;

	    try(Connection conn=
	            conexaoBanco.conectar();
	        PreparedStatement ps=
	            conn.prepareStatement(sql)){

	        ps.setInt(1,idUsuario);

	        ps.setTimestamp(
	            2,
	            Timestamp.valueOf(data)
	        );

	        try(ResultSet rs=
	                ps.executeQuery()){

	            return rs.next() &&
	                   rs.getInt(1)>0;
	        }
	    }
	}



	public static void atualizarHorario(
	        int id,
	        LocalDate novaData,
	        LocalTime novaHora
	)throws Exception{

	    Connection conn=
	        conexaoBanco.conectar();

	    String sql=
	        "UPDATE consultas SET data_consulta=? WHERE id=?";

	    PreparedStatement ps=
	        conn.prepareStatement(sql);

	    LocalDateTime dataHora=
	        LocalDateTime.of(
	            novaData,
	            novaHora
	        );

	    ps.setTimestamp(
	        1,
	        Timestamp.valueOf(
	            dataHora
	        )
	    );

	    ps.setInt(2,id);

	    ps.executeUpdate();

	    ps.close();
	    conn.close();
	}



	public static boolean medicoJaTemConsultaEdicao(
	        int idMedico,
	        LocalDateTime data,
	        int idAtual
	)throws Exception{

	    String sql="""
	        SELECT COUNT(*)
	        FROM consultas
	        WHERE fk_medico=?
	        AND status='agendada'
	        AND data_consulta=?
	        AND id!=?
	    """;

	    try(Connection conn=
	            conexaoBanco.conectar();
	        PreparedStatement ps=
	            conn.prepareStatement(sql)){

	        ps.setInt(1,idMedico);

	        ps.setTimestamp(
	            2,
	            Timestamp.valueOf(data)
	        );

	        ps.setInt(3,idAtual);

	        try(ResultSet rs=
	                ps.executeQuery()){

	            return rs.next() &&
	                   rs.getInt(1)>0;
	        }
	    }
	}
	public static List<Consulta> buscarFiltradas(
	        LocalDate data,
	        String status,
	        String nomeUsuario
	) throws Exception {

	    List<Consulta> lista = new ArrayList<>();

	    Connection conn = conexaoBanco.conectar();

	    // =========================
	    // SQL BASE
	    // =========================

	    StringBuilder sql = new StringBuilder("""
	        SELECT
	            c.id,
	            u.nome AS nome_usuario,
	            m.nome AS nome_medico,
	            m.tipo AS especialidade,
	            c.data_consulta,
	            c.data_agendamento,
	            c.status,
	            c.fk_medico,
	            c.fk_usuario
	        FROM consultas c
	        JOIN usuarios u ON c.fk_usuario = u.id
	        JOIN medicos m ON c.fk_medico = m.id
	        WHERE 1=1
	    """);

	    // =========================
	    // FILTRO STATUS
	    // =========================

	    if(status != null &&
	       !status.equalsIgnoreCase("Todos")) {

	        sql.append(" AND c.status = ?");
	    }

	    // =========================
	    // FILTRO NOME USUÁRIO
	    // =========================

	    if(nomeUsuario != null &&
	       !nomeUsuario.isBlank()) {

	        sql.append("""
	            AND LOWER(u.nome) LIKE LOWER(?)
	        """);
	    }

	    // =========================
	    // FILTRO DATA
	    // =========================

	    if(data != null) {

	        sql.append("""
	            AND c.data_consulta BETWEEN ? AND ?
	        """);
	    }

	    // =========================
	    // ORDER BY
	    // =========================

	    sql.append("""
	        ORDER BY c.data_consulta ASC
	    """);

	    PreparedStatement ps =
	            conn.prepareStatement(sql.toString());

	    // =========================
	    // CONTROLE DOS PARÂMETROS
	    // =========================

	    int index = 1;

	    // =========================
	    // SET STATUS
	    // =========================

	    if(status != null &&
	       !status.equalsIgnoreCase("Todos")) {

	        ps.setString(index++, status);
	    }

	    // =========================
	    // SET NOME
	    // =========================

	    if(nomeUsuario != null &&
	       !nomeUsuario.isBlank()) {

	        ps.setString(
	                index++,
	                "%" + nomeUsuario + "%"
	        );
	    }

	    // =========================
	    // SET DATA
	    // =========================

	    if(data != null) {

	        LocalDateTime inicio =
	                data.atStartOfDay();

	        LocalDateTime fim =
	                data.atTime(23,59,59);

	        ps.setTimestamp(
	                index++,
	                Timestamp.valueOf(inicio)
	        );

	        ps.setTimestamp(
	                index++,
	                Timestamp.valueOf(fim)
	        );
	    }

	    ResultSet rs = ps.executeQuery();

	    // =========================
	    // MONTA LISTA
	    // =========================

	    while(rs.next()) {

	        int id = rs.getInt("id");

	        String usuario =
	                rs.getString("nome_usuario");

	        String medico =
	                rs.getString("nome_medico");

	        String especialidade =
	                rs.getString("especialidade");

	        LocalDateTime dataConsulta =
	                rs.getTimestamp(
	                        "data_consulta"
	                ).toLocalDateTime();

	        LocalDateTime dataAgendada =
	                rs.getTimestamp(
	                        "data_agendamento"
	                ).toLocalDateTime();

	        String statusConsulta =
	                rs.getString("status");

	        int idMedico =
	                rs.getInt("fk_medico");

	        int idUser =
	                rs.getInt("fk_usuario");

	        Consulta consulta = new Consulta(
	                id,
	                usuario,
	                medico,
	                especialidade,
	                dataConsulta,
	                dataAgendada,
	                statusConsulta,
	                idMedico,
	                idUser
	        );

	        lista.add(consulta);
	    }

	    rs.close();
	    ps.close();
	    conn.close();

	    return lista;
	}

	public static boolean usuarioJaTemConsultaEdicao(
	        int idUsuario,
	        int idMedico,
	        LocalDateTime data,
	        int idAtual
	)throws Exception{

	    String sql="""
	        SELECT COUNT(*)
	        FROM consultas
	        WHERE fk_usuario=?
	        AND fk_medico=?
	        AND status='agendada'
	        AND data_consulta BETWEEN ? AND ?
	        AND id!=?
	    """;

	    try(Connection conn=
	            conexaoBanco.conectar();
	        PreparedStatement ps=
	            conn.prepareStatement(sql)){

	        ps.setInt(1,idUsuario);
	        ps.setInt(2,idMedico);

	        LocalDateTime inicio=
	            data.minusHours(1);

	        LocalDateTime fim=
	            data.plusHours(1);

	        ps.setTimestamp(
	            3,
	            Timestamp.valueOf(inicio)
	        );

	        ps.setTimestamp(
	            4,
	            Timestamp.valueOf(fim)
	        );

	        ps.setInt(5,idAtual);

	        try(ResultSet rs=
	                ps.executeQuery()){

	            return rs.next() &&
	                   rs.getInt(1)>0;
	        }
	    }
	}
	public static String buscarEmailPorConsulta(int idConsulta) throws Exception {

	    String email = null;

	    String sql = """
	        SELECT u.email
	        FROM consultas c
	        JOIN usuarios u ON c.fk_usuario = u.id
	        WHERE c.id = ?
	    """;

	    try (Connection conn = conexaoBanco.conectar();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, idConsulta);

	        try (ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {
	                email = rs.getString("email");
	            }
	        }
	    }

	    return email;
	}

}