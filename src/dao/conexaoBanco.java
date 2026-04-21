package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class conexaoBanco {

    public static Connection conectar() {

        Properties props = new Properties();

        try {
            // Carrega o arquivo de configuração
            InputStream file = conexaoBanco.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");

            props.load(file);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            Connection conn = DriverManager.getConnection(url, user, password);

            System.out.println("Conectou com sucesso");
            return conn;

        } catch (Exception e) {
            System.out.println("Erro ao conectar ao banco de dados");
            e.printStackTrace();
            return null;
        }
    }
}