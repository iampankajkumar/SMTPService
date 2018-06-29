
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

	private static SendMailTLS instance;
	private static final String SMTP_HOST = "smtp.gmail.com";

	private SendMailTLS() {
	}

	public static SendMailTLS getInstance() {
		if (instance == null)
			instance = new SendMailTLS();
		return instance;
	}

	public void sendMail(String fromEmail, String toEmail, String subject, String body) {
		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST);

			Session session = SendMailTLS.getInstance().getSMTPSession(props, true);

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(subject);

			// for sending simple body mail
			message.setText(body);
			message.setSentDate(new Date());
			// Send mail
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void sendMail(String fromEmail, String toEmail, String subject, String body, String filePath) {
		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST);

			Session session = SendMailTLS.getInstance().getSMTPSession(props, true);

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(subject);

			// for sending simple body mail

			// This mail has 2 part, the BODY and the embedded image
			MimeMultipart multipart = new MimeMultipart("related");

			// first part (the html)
			BodyPart messageBodyPart = new MimeBodyPart();
			String htmlText = body;
			messageBodyPart.setContent(htmlText, "text/html");
			// add it
			multipart.addBodyPart(messageBodyPart);

			// second part (the image)
			messageBodyPart = new MimeBodyPart();
			DataSource fds = new FileDataSource(filePath);

			messageBodyPart.setDataHandler(new DataHandler(fds));
			messageBodyPart.setHeader("Content-ID", "<image>");

			// add image to the multipart
			multipart.addBodyPart(messageBodyPart);

			// put everything together
			message.setContent(multipart);

			// Send mail
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void sendBulkMail(String fromEmail, String[] toEmail, String subject, String body) {
		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST);

			Session session = SendMailTLS.getInstance().getSMTPSession(props, true);

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			InternetAddress[] sendTo = new InternetAddress[toEmail.length];
			for (int i = 0; i < toEmail.length; i++) {
				System.out.println("Send to:" + toEmail[i]);
				sendTo[i] = new InternetAddress(toEmail[i]);
			}
			message.addRecipients(Message.RecipientType.TO, sendTo);
			message.setSubject(subject);

			// for sending simple body mail
			message.setText(body);
			message.setSentDate(new Date());
			// Send mail
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void sendBulkMail(String fromEmail, String[] toEmail, String subject, String body, String filePath) {
		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST);

			Session session = SendMailTLS.getInstance().getSMTPSession(props, true);

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			InternetAddress[] sendTo = new InternetAddress[toEmail.length];
			for (int i = 0; i < toEmail.length; i++) {
				System.out.println("Send to:" + toEmail[i]);
				sendTo[i] = new InternetAddress(toEmail[i]);
			}
			message.setRecipients(Message.RecipientType.TO, sendTo);
			message.setSubject(subject);

			// for sending simple body mail

			// This mail has 2 part, the BODY and the embedded image
			MimeMultipart multipart = new MimeMultipart("related");

			// first part (the html)
			BodyPart messageBodyPart = new MimeBodyPart();
			String htmlText = body;
			messageBodyPart.setContent(htmlText, "text/html");
			// add it
			multipart.addBodyPart(messageBodyPart);

			// second part (the image)
			messageBodyPart = new MimeBodyPart();
			DataSource fds = new FileDataSource(filePath);

			messageBodyPart.setDataHandler(new DataHandler(fds));
			messageBodyPart.setHeader("Content-ID", "<image>");

			// add image to the multipart
			multipart.addBodyPart(messageBodyPart);

			// put everything together
			message.setContent(multipart);

			// Send mail
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public Session getSMTPSession(Properties props, boolean senderAuth) {
		if (senderAuth) {
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");
			return Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("pankajkumar.01869@gmail.com", "email.com");
				}
			});
		} else {
			return Session.getInstance(props, null);
		}
	}

	public static void main(String[] args) {

		String toEmail = "pankaj.kumar@skilrock.com";
		String fromEmail = "pankaj.kumar@skilrock.com";
		String subject = "Test";
		String message = "test message";
		SendMailTLS.getInstance().sendMail(fromEmail, toEmail, subject, message);

	}
}
