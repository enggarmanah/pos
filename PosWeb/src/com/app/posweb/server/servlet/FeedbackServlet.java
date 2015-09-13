package com.app.posweb.server.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.posweb.server.MailUtil;

@SuppressWarnings("serial")
public class FeedbackServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String subject = req.getParameter("subject");
		String message = req.getParameter("message");
		String lang = req.getParameter("lang");
		
		MailUtil.sendFeedbackEmail(name, email, subject, message, lang);
		
		RequestDispatcher view = req.getRequestDispatcher("/feedback.jsp");
        
        try {
			view.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		}    
    }
}
