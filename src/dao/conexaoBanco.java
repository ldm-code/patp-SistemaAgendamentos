package dao;

import java.sql.Connection;
import java.sql.DriverManager;

import config.Config;

public class conexaoBanco {

    public static Connection conectar() {

        try {
            String url = Config.get("db.url");
            String user = Config.get("db.user");
            String password = Config.get("db.password");

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