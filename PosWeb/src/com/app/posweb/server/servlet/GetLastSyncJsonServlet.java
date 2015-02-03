package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.DeviceDao;
import com.app.posweb.server.model.Device;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@SuppressWarnings("serial")
public class GetLastSyncJsonServlet extends BaseJsonServlet {
 
    protected Object processJsonRequest(String jsonStr) throws IOException {
    	
        ObjectMapper mapper = new ObjectMapper();
        
        Device device = mapper.readValue(jsonStr, Device.class);
        		
        DeviceDao deviceDao = new DeviceDao();
        
        Device bean = deviceDao.getDevice(device);
        
        if (bean == null) {
        	device = deviceDao.addDevice(device);
        } else {
        	device = bean;
        }
        
        return device;
    }
}
