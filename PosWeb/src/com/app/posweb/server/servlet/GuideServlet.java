package com.app.posweb.server.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class GuideServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        // we do not set content type, headers, cookies etc.
        // resp.setContentType("text/html"); // while redirecting as
        // it would most likely result in an IllegalStateException

        // "/" is relative to the context root (your web-app name)
        RequestDispatcher view = req.getRequestDispatcher("/guide.htm");
        // don't add your web-app name to the path

        try {
			view.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		}    
    }
}
