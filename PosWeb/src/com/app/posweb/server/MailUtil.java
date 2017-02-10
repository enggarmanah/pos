package com.app.posweb.server;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import com.app.posweb.server.model.Merchant;
import com.app.posweb.shared.Config;

public class MailUtil {
	
	public static void main(String[] args) {
		
		Merchant merchant = new Merchant();
		merchant.setName("Lancar Rejeki");
		merchant.setRef_id("1234567890");
		merchant.setContact_email("radixe@yahoo.com");
		merchant.setLocale("in,ID");
		merchant.setAddress("Jl. Merak 4 Gonilan Solo 57162, Indonesia");
		merchant.setTelephone("+62 856-4745-3111");
		merchant.setLogin_id("login");
		merchant.setPassword("password");
		
		sendRegistrationEmail(merchant);
	}
 
	public static void sendRegistrationEmail(Merchant merchant) {

		try {

			String subject = null;

			if ("in,ID".equals(merchant.getLocale())) {
				subject = "Registrasi POS Tokoku [" + merchant.getName() + "]";
			} else {
				subject = "POS Tokoku Registration [" + merchant.getName() + "]";
			}

			Sendgrid mail = new Sendgrid("radixe", "enggar123");

			mail.setTo(merchant.getContact_email())
				.setFrom(Config.ADMIN_EMAIL).setSubject(subject)
				.setText("Hello World!")
				.setHtml(TemplateUtil.getRegistrationMessage(merchant));

			mail.send();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendNotificationEmail(Merchant merchant) {

		try {

			String subject = null;

			if ("in,ID".equals(merchant.getLocale())) {
				subject = "Registrasi POS Tokoku [" + merchant.getName() + "]";
			} else {
				subject = "POS Tokoku Registration [" + merchant.getName() + "]";
			}

			Sendgrid mail = new Sendgrid("radixe", "enggar123");

			mail.setTo(Config.ADMIN_EMAIL)
				.setFrom(Config.ADMIN_EMAIL).setSubject(subject)
				.setText("Hello World!")
				.setHtml(TemplateUtil.getNotificationMessage(merchant));

			mail.send();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendFeedbackEmail(String name, String email,
			String subject, String message, String lang) {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(Config.ADMIN_EMAIL,
					Config.ADMIN_EMAIL_DESC));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					email));
			msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
					Config.ADMIN_EMAIL, Config.ADMIN_EMAIL_DESC));

			if ("id".equals(lang)) {
				msg.setSubject("POS TokoKu - Konfirmasi Pesan");
			} else {
				msg.setSubject("POS TokoKu - Feedback Acknowledgement");
			}

			Multipart mp = new MimeMultipart();

			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(TemplateUtil.getAcknowledgementMessage(name,
					subject, message, lang), "text/html; charset=utf-8");
			mp.addBodyPart(htmlPart);

			msg.setContent(mp);

			Transport.send(msg);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
