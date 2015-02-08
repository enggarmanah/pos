package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.ProductDao;
import com.app.posweb.server.model.SyncRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@SuppressWarnings("serial")
public class ProductGetJsonServlet extends BaseJsonServlet {
 
    protected Object processJsonRequest(String jsonStr) throws IOException {
    	
        ObjectMapper mapper = new ObjectMapper();
        
        SyncRequest request = mapper.readValue(jsonStr, SyncRequest.class);         
        
        ProductDao productDao = new ProductDao();
        
        return productDao.getProducts(request);
    }
}
