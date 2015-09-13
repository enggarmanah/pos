package com.app.posweb.server;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.app.posweb.server.model.Merchant;


public class TemplateUtil {
	
	private static final Logger log = Logger.getLogger(TemplateUtil.class.getName());
	
	private static VelocityEngine ve = new VelocityEngine();
	
	static {
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
    	ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
    	ve.init();
	}
	
	public static String getNotificationMessage(Merchant merchant) {
		
		StringWriter w = new StringWriter();
		
		try
        {
        	VelocityContext context = new VelocityContext();
        	
        	String refId = merchant.getRef_id();
        	String activationCode = refId.substring(refId.length()-6);

            context.put("merchant", merchant.getName());
            context.put("address", merchant.getAddress());
            context.put("telephone", merchant.getTelephone());
            context.put("loginId", merchant.getLogin_id());
            context.put("code", activationCode);

            Template t = ve.getTemplate( "com/app/posweb/server/template/registration.vm" );
        	t.merge(context, w);
        	
        	System.out.println(" template : " + w );
        }
        catch(Exception e)
        {
        	log.log(Level.SEVERE, e.getMessage(), e);
        }
		
		return w.toString();
	}
	
	public static String getAcknowledgementMessage(String name, String subject, String message, String lang) {
		
		StringWriter w = new StringWriter();
		
		try
        {
        	VelocityContext context = new VelocityContext();
        	
        	context.put("name", name);
            context.put("subject", subject);
            context.put("message", message);

            Template t = ve.getTemplate( "com/app/posweb/server/template/" + ("id".equals(lang) ? "feedback-id.vm" : "feedback.vm"));
        	t.merge(context, w);
        	
        	System.out.println(" template : " + w );
        }
        catch(Exception e)
        {
        	log.log(Level.SEVERE, e.getMessage(), e);
        }
		
		return w.toString();
	}
}
