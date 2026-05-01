package utils;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import config.EmailSessionConfig;

public class enviarEmail {

    public static void enviar(String email, String titulo, String conteudo) {

        try {
            Session session = EmailSessionConfig.getSession();

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress("demoraesleonardo327@gmail.com"));

            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(email)
            );

            message.setSubject(titulo);

            message.setText(conteudo);

            new Thread(() -> {
                try {
					Transport.send(message);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }).start();

            System.out.println("Email enviado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}