package com.app.posweb.server.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
 
@SuppressWarnings("serial")
public abstract class BaseJsonServlet extends HttpServlet {
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    	
    	doPost(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    	
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        
        StringBuffer json = new StringBuffer();
        
        String line = null;
        
        while ((line = br.readLine()) != null){
        	json.append(line);
        }
 
        Object output = processJsonRequest(json.toString());
        
        response.setContentType("application/json");
        
        ObjectMapper mapper = new ObjectMapper();
        
        mapper.writeValue(response.getOutputStream(), output);
    }
    
    protected abstract Object processJsonRequest(String jsonStr) throws IOException;
}
