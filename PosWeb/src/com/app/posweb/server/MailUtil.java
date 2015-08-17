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
	
	public static void sendConfirmationEmail(Merchant merchant) {
		
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(Config.ADMIN_EMAIL, Config.ADMIN_EMAIL_DESC));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(merchant.getContact_email(), merchant.getName()));
            msg.setSubject("POS Tokoku Registration Confirmation");
                        
            Multipart mp = new MimeMultipart();            
            
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(TemplateUtil.getNotificationMessage(merchant), "text/html; charset=utf-8");
            mp.addBodyPart(htmlPart);

            msg.setContent(mp);
            
            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
