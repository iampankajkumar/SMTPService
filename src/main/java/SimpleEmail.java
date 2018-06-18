import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SimpleEmail {
	
	public static void main(String[] args) {
		
	    System.out.println("SimpleEmail Start");
		
	    String smtpHostServer = "mail.ithubalottery.co.za";
	    String[] emailID = {"tarun.sharma@skilrock.com"};
	    
	    Properties props = System.getProperties();

	    props.put("mail.smtp.host", smtpHostServer);

	    Session session = Session.getInstance(props, null);
	    
	    sendEmail(session, emailID,"SimpleEmail Testing Subject", "SimpleEmail Testing Body");
	}
	public static void sendEmail(Session session, String[] toEmail, String subject, String body){
		try
	    {
	      MimeMessage msg = new MimeMessage(session);
	      //set message headers
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));

	      msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

	      msg.setSubject(subject, "UTF-8");

	      msg.setText(body, "UTF-8");

	      msg.setSentDate(new Date());

	     /* msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));*/
	      InternetAddress[] sendTo = new InternetAddress[toEmail.length];
			for (int i = 0; i <toEmail.length; i++) {
				
				sendTo[i] = new InternetAddress(toEmail[i]);
			}
			msg.addRecipients(Message.RecipientType.TO, sendTo);
	      System.out.println("Message is ready");
    	  Transport.send(msg);  

	      System.out.println("EMail Sent Successfully!!");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}

}

