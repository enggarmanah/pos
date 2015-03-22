package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.OrderItemDao;
import com.app.posweb.server.model.SyncRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@SuppressWarnings("serial")
public class OrderItemGetJsonServlet extends BaseJsonServlet {
 
    protected Object processJsonRequest(String jsonStr) throws IOException {
    	
        ObjectMapper mapper = new ObjectMapper();
        
        SyncRequest request = mapper.readValue(jsonStr, SyncRequest.class);         
        
        OrderItemDao orderItemDao = new OrderItemDao();
        
        return orderItemDao.getOrderItems(request);
    }
}
