package com.app.posweb.server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.posweb.server.dao.EmployeeDao;
import com.app.posweb.server.model.Employee;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
import com.app.posweb.server.model.SyncStatus;
 
@SuppressWarnings("serial")
public class EmployeeUpdateJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
    	List<SyncStatus> syncStatusList = new ArrayList<SyncStatus>();
        
        EmployeeDao employeeDao = new EmployeeDao();
        
        for (Employee employee : request.getEmployees()) {
        	
        	employee.setSync_date(request.getSync_date());
        	
        	String status = SyncStatus.FAIL;
        	
        	try {
				employeeDao.syncEmployee(employee);
				status = SyncStatus.SUCCESS;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	SyncStatus syncStatus = new SyncStatus();
        	syncStatus.setRemoteId(employee.getRemote_id());
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
