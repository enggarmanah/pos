package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.MerchantDao;
import com.app.posweb.server.model.Merchant;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@SuppressWarnings("serial")
public class MerchantValidateJsonServlet extends BaseJsonServlet {
 
    protected Object processJsonRequest(String jsonStr) throws IOException {
    	
        ObjectMapper mapper = new ObjectMapper();
        
        Merchant merchant = mapper.readValue(jsonStr, Merchant.class);         
        
        MerchantDao merchantDao = new MerchantDao();
        
        return merchantDao.validateMerchant(merchant);
    }
}
