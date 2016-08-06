package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.MerchantDao;
import com.app.posweb.server.model.Merchant;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
import com.app.posweb.shared.Constant;
 
@SuppressWarnings("serial")
public class MerchantActivateJsonServlet extends BaseJsonServlet {
	
	protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        MerchantDao merchantDao = new MerchantDao();
        
        Merchant merchant = request.getMerchant();
        
        merchant = merchantDao.getMerchant(merchant.getLogin_id());
        merchant.setStatus(Constant.STATUS_ACTIVE);
        
        merchantDao.updateMerchant(merchant);
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setMerchant(merchant);
        
        return response;
    }
}
