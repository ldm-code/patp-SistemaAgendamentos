package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    public static void main(String[] args) {

        Properties props = new Properties();

        try {
            FileInputStream file = new FileInputStream("config.properties");
            props.load(file);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            System.out.println("URL: " + url);
            System.out.println("User: " + user);
            System.out.println("Password: " + password);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
