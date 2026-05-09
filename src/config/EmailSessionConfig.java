package config;
import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
public class EmailSessionConfig {
	private static Session session ;
	public static Session getSession() {
		if (session==null) {


			            final String remetente = Config.get("mail.user");
			            final String senha = Config.get("mail.password"); // depois vamos melhorar isso

			            Properties props = new Properties();
			            props.put("mail.smtp.auth", "true");
			          
			            props.put("mail.smtp.host", Config.get("mail.smtp.host"));
			            props.put("mail.smtp.port", "587");
			          

			            session = Session.getInstance(props, new Authenticator() {
			                protected PasswordAuthentication getPasswordAuthentication() {
			                    return new PasswordAuthentication(remetente, senha);
			                
			           
			                }
			            });
			        }

			        return session;
			    }
			
		
		
			            }


