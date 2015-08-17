package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.MailUtil;
import com.app.posweb.server.dao.MerchantDao;
import com.app.posweb.server.model.Merchant;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
import com.app.posweb.shared.Constant;
 
@SuppressWarnings("serial")
public class MerchantRegisterJsonServlet extends BaseJsonServlet {
	
	protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        MerchantDao merchantDao = new MerchantDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        
        try {
        	
        	Merchant merchant = merchantDao.registerMerchant(request.getMerchant());
			response.setMerchant(merchant);
			
			MailUtil.sendConfirmationEmail(merchant);
			
		} catch (Exception e) {
			
			response.setRespCode(SyncResponse.ERROR);
			response.setRespDescription(Constant.ERROR_REGISTER_MERCHANT_CONFLICT);
			
			e.printStackTrace();
		}
        
        return response;
    }
}