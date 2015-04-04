package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.DiscountDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class DiscountGetJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        DiscountDao discountDao = new DiscountDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setDiscounts(discountDao.getDiscounts(request));
        
        return response;
    }
}