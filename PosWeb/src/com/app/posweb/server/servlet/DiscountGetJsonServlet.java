package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.DiscountDao;
import com.app.posweb.server.model.SyncRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@SuppressWarnings("serial")
public class DiscountGetJsonServlet extends BaseJsonServlet {
 
    protected Object processJsonRequest(String jsonStr) throws IOException {
    	
        ObjectMapper mapper = new ObjectMapper();
        
        SyncRequest request = mapper.readValue(jsonStr, SyncRequest.class);         
        
        DiscountDao discountDao = new DiscountDao();
        
        return discountDao.getDiscounts(request.getLastSyncDate());
    }
}
