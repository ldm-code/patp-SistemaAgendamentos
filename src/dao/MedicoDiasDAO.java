package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MedicoDiasDAO {

    public static void inserirDia(int medicoId, String diaSemana) throws Exception {
        Connection conn = conexaoBanco.conectar();

        String sql = "INSERT INTO medico_dias_semana (medico_id, dia_semana) VALUES (?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, medicoId);
        ps.setString(2, diaSemana);

        ps.executeUpdate();
        ps.close();
    }

    public static List<String> buscarDias(int medicoId) throws Exception {
        List<String> dias = new ArrayList<>();
        Connection conn = conexaoBanco.conectar();

        String sql = "SELECT dia_semana FROM medico_dias_semana WHERE medico_id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, medicoId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            dias.add(rs.getString("dia_semana"));
        }

        rs.close();
        ps.close();

        return dias;
    }
}
