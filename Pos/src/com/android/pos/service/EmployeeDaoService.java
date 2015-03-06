package com.android.pos.service;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.dao.Employee;
import com.android.pos.dao.EmployeeDao;
import com.android.pos.model.EmployeeBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.DbUtil;
import com.android.pos.util.MerchantUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class EmployeeDaoService {
	
	private static EmployeeDao employeeDao = DbUtil.getSession().getEmployeeDao();
	
	public void addEmployee(Employee employee) {
		
		employeeDao.insert(employee);
	}
	
	public void updateEmployee(Employee employee) {
		
		employeeDao.update(employee);
	}
	
	public void deleteEmployee(Employee employee) {

		Employee entity = employeeDao.load(employee.getId());
		entity.setStatus(Constant.STATUS_DELETED);
		entity.setUploadStatus(Constant.STATUS_YES);
		employeeDao.update(entity);
	}
	
	public Employee getEmployee(Long id) {
		
		return employeeDao.load(id);
	}
	
	public List<Employee> getEmployees(String query) {

		QueryBuilder<Employee> qb = employeeDao.queryBuilder();
		qb.where(EmployeeDao.Properties.MerchantId.eq(MerchantUtil.getMerchantId()),
				EmployeeDao.Properties.Name.like("%" + query + "%"), 
				EmployeeDao.Properties.Status.notEq(Constant.STATUS_DELETED)).orderAsc(EmployeeDao.Properties.Name);

		Query<Employee> q = qb.build();
		List<Employee> list = q.list();

		return list;
	}
	
	public List<EmployeeBean> getEmployeesForUpload() {

		QueryBuilder<Employee> qb = employeeDao.queryBuilder();
		qb.where(EmployeeDao.Properties.MerchantId.eq(MerchantUtil.getMerchantId()),
				EmployeeDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(EmployeeDao.Properties.Name);
		
		Query<Employee> q = qb.build();
		
		ArrayList<EmployeeBean> employeeBeans = new ArrayList<EmployeeBean>();
		
		for (Employee prdGroup : q.list()) {
			
			employeeBeans.add(BeanUtil.getBean(prdGroup));
		}
		
		return employeeBeans;
	}
	
	public void updateEmployees(List<EmployeeBean> employees) {
		
		for (EmployeeBean bean : employees) {
			
			boolean isAdd = false;
			
			Employee employee = employeeDao.load(bean.getRemote_id());
			
			if (employee == null) {
				employee = new Employee();
				isAdd = true;
			}
			
			BeanUtil.updateBean(employee, bean);
			
			if (isAdd) {
				employeeDao.insert(employee);
			} else {
				employeeDao.update(employee);
			}
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
