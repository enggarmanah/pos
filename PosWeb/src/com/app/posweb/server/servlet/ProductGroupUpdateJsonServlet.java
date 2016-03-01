package com.app.posweb.server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.posweb.server.dao.ProductGroupDao;
import com.app.posweb.server.model.ProductGroup;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
import com.app.posweb.server.model.SyncStatus;
 
@SuppressWarnings("serial")
public class ProductGroupUpdateJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
    	List<SyncStatus> syncStatusList = new ArrayList<SyncStatus>();
        
    	ProductGroupDao productGroupDao = new ProductGroupDao();
        
        for (ProductGroup productGroup : request.getProductGroups()) {
        	
        	productGroup.setSync_date(request.getSync_date());
        	
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
        
        SyncResponse response = new SyncResponse();
        
        response.setResultCount(request.getResultCount());
        response.setNextIndex(getNextIndex(request.getIndex(), request.getResultCount()));
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setStatus(syncStatusList);
        
        return response;
    }
}
