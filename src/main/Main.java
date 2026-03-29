package main;
import java.util.Properties;
import java.util.List;
import dao.medicosDAO;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import model.MedicosSelect;

public class Main {

	   public static void main(String[] args) throws Exception {
		    final String remetente = "demoraesleonardo327@gmail.com";
	        final String senha = "gpkx skpj ftln efmb"; 

	        String destinatario = "demoraesleonardo327@gmail.com";

	        
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
	            message.setText("Oi");

	            // ENVIO (isso que realmente manda)
	            Transport.send(message);

	            System.out.println("Email enviado com sucesso!");

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        List<MedicosSelect> medicos = medicosDAO.select();

	        for (MedicosSelect m : medicos) {
	            System.out.println(
	                m.getId() + " - " +
	                m.getNome() + " - " +
	                m.getTipo()
	            );
	            
      
	}

}
}