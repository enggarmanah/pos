package com.app.posweb.server.servlet;

import java.io.IOException;
import java.util.List;

import com.app.posweb.server.dao.ProductGroupDao;
import com.app.posweb.server.model.ProductGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
 
@SuppressWarnings("serial")
public class ProductGroupUpdateJsonServlet extends BaseJsonServlet {
 
    protected Object processJsonRequest(String jsonStr) throws IOException {
    	
        ObjectMapper mapper = new ObjectMapper();
        
        List<ProductGroup> productGroups = mapper.readValue(jsonStr.toString(),
        							TypeFactory.defaultInstance().constructCollectionType(List.class,  
        							ProductGroup.class));         
        
        ProductGroupDao productGroupDao = new ProductGroupDao();
        
        for (ProductGroup productGroup : productGroups) {
        	productGroupDao.syncProductGroup(productGroup);
        }
        
        return productGroups;
    }
}
