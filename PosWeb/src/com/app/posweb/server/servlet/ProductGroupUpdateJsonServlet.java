package com.app.posweb.server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.posweb.server.dao.ProductGroupDao;
import com.app.posweb.server.model.ProductGroup;
import com.app.posweb.server.model.SyncStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
 
@SuppressWarnings("serial")
public class ProductGroupUpdateJsonServlet extends BaseJsonServlet {
 
    protected Object processJsonRequest(String jsonStr) throws IOException {
    	
    	List<SyncStatus> syncStatusList = new ArrayList<SyncStatus>();
        ObjectMapper mapper = new ObjectMapper();
        
        List<ProductGroup> productGroups = mapper.readValue(jsonStr.toString(),
        							TypeFactory.defaultInstance().constructCollectionType(List.class,  
        							ProductGroup.class));         
        
        ProductGroupDao productGroupDao = new ProductGroupDao();
        
        for (ProductGroup productGroup : productGroups) {
        	
        	String status = SyncStatus.FAIL;
        	
        	try {
				productGroupDao.syncProductGroup(productGroup);
				status = SyncStatus.SUCCESS;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	SyncStatus syncStatus = new SyncStatus();
        	syncStatus.setRemoteId(productGroup.getRemote_id());
        	syncStatus.setStatus(status);
        	
        	syncStatusList.add(syncStatus);
        }
        
        return syncStatusList;
    }
}
