package com.android.pos.service;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.pos.Constant;
import com.android.pos.dao.Employee;
import com.android.pos.dao.EmployeeDao;
import com.android.pos.model.EmployeeBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class EmployeeDaoService {
	
	private EmployeeDao employeeDao = DbUtil.getSession().getEmployeeDao();
	
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
	
	public List<Employee> getEmployees(String query, int lastIndex) {

		SQLiteDatabase db = DbUtil.getDb();
		
		String queryStr = "%" + CommonUtil.getNvlString(query) + "%";
		String status = Constant.STATUS_DELETED;
		String limit = Constant.QUERY_LIMIT;
		String lastIdx = String.valueOf(lastIndex);
		
		Cursor cursor = db.rawQuery("SELECT _id "
				+ " FROM employee "
				+ " WHERE name like ? AND status <> ? "
				+ " ORDER BY name LIMIT ? OFFSET ? ",
				new String[] { queryStr, status, limit, lastIdx});
		
		List<Employee> list = new ArrayList<Employee>();
		
		while(cursor.moveToNext()) {
			
			Long id = cursor.getLong(0);
			Employee item = getEmployee(id);
			list.add(item);
		}

		return list;
	}
	
	public List<EmployeeBean> getEmployeesForUpload() {

		QueryBuilder<Employee> qb = employeeDao.queryBuilder();
		qb.where(EmployeeDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(EmployeeDao.Properties.Name);
		
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
