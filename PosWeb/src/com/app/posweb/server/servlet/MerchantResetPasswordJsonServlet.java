package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.MerchantDao;
import com.app.posweb.server.model.Merchant;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class MerchantResetPasswordJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        MerchantDao merchantDao = new MerchantDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        
        Merchant bean = request.getMerchant();
        
        Merchant merchant = merchantDao.getMerchant(bean.getLogin_id());
        merchant.setPassword(bean.getPassword());
        
        merchant = merchantDao.updateMerchant(merchant);
        
        response.setMerchant(merchant);
        
        return response;
    }
}