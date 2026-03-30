package utils;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class enviarEmail  {
	 public static void enviar(String email){
		    final String remetente = "demoraesleonardo327@gmail.com";
	        final String senha = "gpkx skpj ftln efmb"; 

	        String destinatario = email;

	        
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587"); 
	        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

	        try {
	           
	            Session session = Session.getInstance(props,
	                new Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(remetente, senha);
	                    }
	                }
	            );

	          
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(remetente));
	            message.setRecipients(
	                Message.RecipientType.TO,
	                InternetAddress.parse(destinatario)
	            );
	            message.setSubject("Teste");
	            message.setText("Oi,fiz funcionar a parte do email mano :)");

	            Transport.send(message);

	            System.out.println("Email enviado com sucesso!");

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

		 
	 }
		
	

}
