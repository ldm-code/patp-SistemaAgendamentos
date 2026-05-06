package utils;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import config.Config;
import config.EmailSessionConfig;

public class enviarEmail {

	public static void enviar(String email, String titulo, String conteudo) {

	    new Thread(() -> {
	        try {
	            Session session = EmailSessionConfig.getSession();

	            Message message = new MimeMessage(session);

	            message.setFrom(new InternetAddress(Config.get("mail.user")));

	            message.setRecipients(
	                Message.RecipientType.TO,
	                InternetAddress.parse(email)
	            );

	            message.setSubject(titulo);

	            message.setText(conteudo);

	            Transport.send(message);

	            System.out.println("Email enviado com sucesso!");

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }).start();
	}
}