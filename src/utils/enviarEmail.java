package utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import config.Config;
import config.EmailSessionConfig;

public class enviarEmail {

    // 🔥 Pool com 2 threads (controla concorrência)
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void enviar(String email, String titulo, String conteudo) {

        executor.submit(() -> { // envia tarefa para a fila
            try {
                Session session = EmailSessionConfig.getSession();

                MimeMessage message = new MimeMessage(session);

                message.setFrom(new InternetAddress(Config.get("mail.user")));

                message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
                );

                message.setSubject(titulo, "UTF-8");

                message.setText(conteudo, "UTF-8");

                Transport.send(message);

                System.out.println("Email enviado para: " + email);

                // 🔥 pequeno delay para não sobrecarregar o servidor
                Thread.sleep(500);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}