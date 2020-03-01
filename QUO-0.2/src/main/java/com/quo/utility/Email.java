package com.quo.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

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

import org.apache.log4j.Logger;

public class Email {

	ResourceBundle resourceBundle = ResourceBundle.getBundle("Config");
	Logger logger = Logger.getLogger("devpinoyLogger");

	Date dateNow = new Date();
	SimpleDateFormat dateFormatter = new SimpleDateFormat(resourceBundle.getString("dateFormatForEmailSubject"));
	String currentdate = dateFormatter.format(dateNow);

	//String to = resourceBundle.getString("toEmail");
	//String cc = resourceBundle.getString("ccEmail");
	//String bcc = resourceBundle.getString("bccEmail");
	//String from = resourceBundle.getString("fromEmail");
	//String password = resourceBundle.getString("emailPassword");

	// String subject = "Sprint Report: " + currentdate;
	// String htmlMessage = "<p>Hello,</p><p>Sprint Report is generated on date: <b>" + currentdate
	//		+ "</b>.</p><p>Please find the attached report.</p>";
	
	public boolean sendSprintReport(String attachmentFile, String recipientTypeTO, final String from, final String fromPassword, String sprintNumber) 
	{
		boolean emailSentStatus = false;
		Properties props = new Properties();
		props.put("mail.smtp.auth", resourceBundle.getString("authentication"));
		props.put("mail.smtp.starttls.enable", resourceBundle.getString("startTlsEnable"));
		props.put("mail.smtp.host", resourceBundle.getString("host"));
		props.put("mail.smtp.port", resourceBundle.getString("port"));

		Session session = Session.getInstance(props, new javax.mail.Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication() 
			{
				return new PasswordAuthentication(from, fromPassword);
			}
		});
		try 
		{
			String subject = "Sprint Report - " +sprintNumber;
			String htmlMessage = "<p>Hello,</p><p>Sprint Report is generated for <b>" +sprintNumber+ "</b> on <b>" + currentdate
					+ "</b>.</p><p>Please find the attached report.</p>";
			String attachmentFileName = attachmentFile.substring(attachmentFile.lastIndexOf("\\") + 1).trim();
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientTypeTO));
			// message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
			// message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
			message.setSubject(subject);
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(htmlMessage, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(attachmentFile);
			messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(attachmentFileName);
	        multipart.addBodyPart(messageBodyPart);
	        message.setContent(multipart);
			logger.debug("Sending Sprint Report to: " + recipientTypeTO);
			// logger.debug("Sending email bcc: " + bcc);
			// logger.debug("Sending email cc: " + cc);
			Transport.send(message);
			logger.debug("Sprint Report is sent to email: "+recipientTypeTO);
			emailSentStatus = true;
			return emailSentStatus;
		} 
		catch (MessagingException e) 
		{
			logger.debug("Exception Occurred: "+e);
			return emailSentStatus;
		}
	}
}
