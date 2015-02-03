package com.app.posweb.server.servlet;

import java.io.IOException;
import java.util.Date;

import com.app.posweb.server.dao.DeviceDao;
import com.app.posweb.server.model.Device;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@SuppressWarnings("serial")
public class UpdateLastSyncJsonServlet extends BaseJsonServlet {
 
    protected Object processJsonRequest(String jsonStr) throws IOException {
    	
        ObjectMapper mapper = new ObjectMapper();
        
        Device device = mapper.readValue(jsonStr, Device.class);
        		
        DeviceDao deviceDao = new DeviceDao();
        
        Device bean = deviceDao.getDevice(device);
        
        bean.setLast_sync_date(new Date());
        device = deviceDao.updateDevice(bean);
        
        return device;
    }
}
