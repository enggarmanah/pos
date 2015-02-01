package com.app.posweb.server.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 



import com.app.posweb.server.dao.ProductGroupDao;
import com.app.posweb.server.model.ProductGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
 
public class ProductGroupServlet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    	
    	doPost(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
 
        // 1. get received JSON data from request
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        
        StringBuffer json = new StringBuffer();
        
        String line = null;
        
        while ((line = br.readLine()) != null){
        	json.append(line);
        }
 
        // 2. initiate jackson mapper
        ObjectMapper mapper = new ObjectMapper();
        
        // 3. Convert received JSON to Article
        //ProductGroup productGroup = mapper.readValue(json, ProductGroup.class);
        
        List<ProductGroup> list = mapper.readValue(json.toString(),
        							TypeFactory.defaultInstance().constructCollectionType(List.class,  
        							ProductGroup.class));
 
        // 4. Set response type to JSON
        response.setContentType("application/json");           
        
        ProductGroupDao productGroupDao = new ProductGroupDao();
        
        // 5. Add article to List<Article>
        
        for (ProductGroup productGroup : list) {
        	productGroupDao.syncProductGroup(productGroup);
        }
        
        //List<ProductGroup> productGroups = productGroupDao.getProductGroups();
        List<ProductGroup> productGroups = list;
        
        // 6. Send List<Article> as JSON to client
        mapper.writeValue(response.getOutputStream(), productGroups);
    }
}
