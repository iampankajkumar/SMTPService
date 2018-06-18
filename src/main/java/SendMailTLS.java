
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMailTLS {

	public static void main(String[] args) {

		final String username = "";//your mail id
		final String password = "";//your password

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		String [] to={"abc@gmail.com","xyz@gmail.com"};//sending to multiple user
		String imagePath="/home/stpl/Downloads/11.png";
		SendMailTLS.sendAttachmentEmail(session, to,"SSLEmail Testing Subject with Attachment", "SSLEmail Testing Body with Attachment",imagePath);

		
	}
	public static void sendAttachmentEmail(Session session, String[] toEmail, String subject, String body, String imagePath){
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			InternetAddress[] sendTo = new InternetAddress[toEmail.length];
			for (int i = 0; i <toEmail.length; i++) {
				System.out.println("Send to:" + toEmail[i]);
				sendTo[i] = new InternetAddress(toEmail[i]);
			}
			message.addRecipients(Message.RecipientType.TO, sendTo);
			message.setSubject(subject);
			if(imagePath==null){
				
				//for sending simple body mail
				message.setText(body);
			    message.setSentDate(new Date());
			}
			else{
			 // This mail has 2 part, the BODY and the embedded image
	         MimeMultipart multipart = new MimeMultipart("related");

	         // first part (the html)
	         BodyPart messageBodyPart = new MimeBodyPart();
	         String htmlText = "<H1>"+body+"</H1><img src=\"cid:image\">";
	         messageBodyPart.setContent(htmlText, "text/html");
	         // add it
	         multipart.addBodyPart(messageBodyPart);

	         // second part (the image)
	         messageBodyPart = new MimeBodyPart();
	         DataSource fds = new FileDataSource(
	        		 imagePath);

	         messageBodyPart.setDataHandler(new DataHandler(fds));
	         messageBodyPart.setHeader("Content-ID", "<image>");

	         // add image to the multipart
	         multipart.addBodyPart(messageBodyPart);

	         // put everything together
	         message.setContent(multipart);
			}
	         // Send message
	         Transport.send(message);

	         System.out.println("Sent message successfully....");

	      } catch (MessagingException e) {
	         throw new RuntimeException(e);
	      }
	
	}
}
