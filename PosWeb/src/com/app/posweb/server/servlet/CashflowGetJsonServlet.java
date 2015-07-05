package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.CashflowDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class CashflowGetJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        CashflowDao cashflowDao = new CashflowDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setCashflows(cashflowDao.getCashflows(request));
        
        return response;
    }
}
