package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.EmployeeDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class EmployeeGetJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        EmployeeDao employeeDao = new EmployeeDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setEmployees(employeeDao.getEmployees(request));
        
        response.setResultCount(employeeDao.getEmployeesCount(request));
        response.setNextIndex(getNextIndex(request.getIndex(), response.getResultCount()));
        
        return response;
    }
}
