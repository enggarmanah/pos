package com.app.posweb.server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.posweb.server.dao.ProductDao;
import com.app.posweb.server.model.Product;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
import com.app.posweb.server.model.SyncStatus;
 
@SuppressWarnings("serial")
public class ProductUpdateJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
    	List<SyncStatus> syncStatusList = new ArrayList<SyncStatus>();
        
    	ProductDao productDao = new ProductDao();
        
        for (Product product : request.getProducts()) {
        	
        	product.setSync_date(request.getSync_date());
        	
        	String status = SyncStatus.FAIL;
        	
        	try {
				productDao.syncProduct(product);
				status = SyncStatus.SUCCESS;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	SyncStatus syncStatus = new SyncStatus();
        	syncStatus.setRemoteId(product.getRemote_id());
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
