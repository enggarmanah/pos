package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.ProductGroupDao;
import com.app.posweb.server.model.Request;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@SuppressWarnings("serial")
public class ProductGroupGetJsonServlet extends BaseJsonServlet {
 
    protected Object processJsonRequest(String jsonStr) throws IOException {
    	
        ObjectMapper mapper = new ObjectMapper();
        
        Request request = mapper.readValue(jsonStr, Request.class);         
        
        ProductGroupDao productGroupDao = new ProductGroupDao();
        
        return productGroupDao.getProductGroups(request.getLastSyncDate());
    }
}