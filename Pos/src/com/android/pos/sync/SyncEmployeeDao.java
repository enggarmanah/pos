package com.android.pos.sync;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.dao.Employee;
import com.android.pos.dao.EmployeeDao;
import com.android.pos.model.EmployeeBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class SyncEmployeeDao {
	
	private EmployeeDao employeeDao = DbUtil.getSession().getEmployeeDao();
	
	public List<EmployeeBean> getEmployeesForUpload() {

		QueryBuilder<Employee> qb = employeeDao.queryBuilder();
		qb.where(EmployeeDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(EmployeeDao.Properties.Name);
		
		Query<Employee> q = qb.build();
		
		ArrayList<EmployeeBean> prodGroupBeans = new ArrayList<EmployeeBean>();
		
		for (Employee prdGroup : q.list()) {
			
			prodGroupBeans.add(BeanUtil.getBean(prdGroup));
		}
		
		return prodGroupBeans;
	}
	
	public void updateEmployees(List<EmployeeBean> employees) {
		
		for (EmployeeBean bean : employees) {
			
			Employee employee = employeeDao.load(bean.getRemote_id());
			
			if (employee == null) {
				employee = new Employee();
			}
			
			BeanUtil.updateBean(employee, bean);
		} 
	}
	
	public void updateEmployeeStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			Employee employee = employeeDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				employee.setUploadStatus(Constant.STATUS_NO);
				employeeDao.update(employee);
			}
		} 
	}
}
