package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import List.MedicosSelect;

public class medicosDAO {

    public static int inserir(String nome, String tipo) throws Exception {

        String sql = "INSERT INTO medicos (nome, tipo) VALUES (?, ?)";

        try (
                Connection conn = conexaoBanco.conectar();
                PreparedStatement ps = conn.prepareStatement(
                        sql,
                        PreparedStatement.RETURN_GENERATED_KEYS
                )
        ) {

            ps.setString(1, nome);
            ps.setString(2, tipo);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {

                int idGerado = 0;

                if (rs.next()) {
                    idGerado = rs.getInt(1);
                }

                return idGerado;
            }
        }
    }

    public static String buscarNomePorId(int id) throws Exception {

        String sql = "SELECT nome FROM medicos WHERE id = ?";

        try (
                Connection conn = conexaoBanco.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                String nome = null;

                if (rs.next()) {
                    nome = rs.getString("nome");
                }

                return nome;
            }
        }
    }

    public static List<MedicosSelect> select() throws Exception {

        List<MedicosSelect> lista = new ArrayList<>();

        String sql = "SELECT id, nome, tipo FROM medicos";

        try (
                Connection conn = conexaoBanco.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                MedicosSelect m = new MedicosSelect();

                m.setId(rs.getInt("id"));
                m.setNome(rs.getString("nome"));
                m.setTipo(rs.getString("tipo"));

                lista.add(m);
            }

            return lista;
        }
    }
}