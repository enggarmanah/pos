package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.MerchantAccessDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class MerchantAccessGetJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        MerchantAccessDao merchantAccessDao = new MerchantAccessDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setMerchantAccesses(merchantAccessDao.getMerchantAccesses(request));
        
        response.setResultCount(merchantAccessDao.getMerchantAccessesCount(request));
        response.setNextIndex(getNextIndex(request.getIndex(), response.getResultCount()));
        
        return response;
    }
}
