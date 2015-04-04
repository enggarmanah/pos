package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.CustomerDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class CustomerGetJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        CustomerDao customerDao = new CustomerDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setCustomers(customerDao.getCustomers(request));
        
        return response;
    }
}
