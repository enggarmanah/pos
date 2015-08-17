package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.MerchantDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class MerchantGetByLoginIdJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        MerchantDao merchantDao = new MerchantDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setMerchant(merchantDao.getMerchant(request.getMerchant().getLogin_id()));
        
        return response;
    }
}