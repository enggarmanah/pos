package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.ProductDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class ProductGetJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        ProductDao productDao = new ProductDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setProducts(productDao.getProducts(request));
        
        return response;
    }
}