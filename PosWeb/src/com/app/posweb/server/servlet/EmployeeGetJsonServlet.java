package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.EmployeeDao;
import com.app.posweb.server.model.SyncRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@SuppressWarnings("serial")
public class EmployeeGetJsonServlet extends BaseJsonServlet {
 
    protected Object processJsonRequest(String jsonStr) throws IOException {
    	
        ObjectMapper mapper = new ObjectMapper();
        
        SyncRequest request = mapper.readValue(jsonStr, SyncRequest.class);         
        
        EmployeeDao employeeDao = new EmployeeDao();
        
        return employeeDao.getEmployees(request);
    }
}
