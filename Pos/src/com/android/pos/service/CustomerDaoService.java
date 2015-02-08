package com.android.pos.service;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.dao.Customer;
import com.android.pos.dao.CustomerDao;
import com.android.pos.model.CustomerBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class CustomerDaoService {
	
	private CustomerDao customerDao = DbUtil.getSession().getCustomerDao();
	
	public void addCustomer(Customer customer) {
		
		customerDao.insert(customer);
	}
	
	public void updateCustomer(Customer customer) {
		
		customerDao.update(customer);
	}
	
	public void deleteCustomer(Customer customer) {

		Customer entity = customerDao.load(customer.getId());
		entity.setStatus(Constant.STATUS_DELETED);
		entity.setUploadStatus(Constant.STATUS_YES);
		customerDao.update(entity);
	}
	
	public Customer getCustomer(Long id) {
		
		return customerDao.load(id);
	}
	
	public List<Customer> getCustomers(String query) {

		QueryBuilder<Customer> qb = customerDao.queryBuilder();
		qb.where(CustomerDao.Properties.Name.like("%" + query + "%"), 
				CustomerDao.Properties.Status.notEq(Constant.STATUS_DELETED)).orderAsc(CustomerDao.Properties.Name);

		Query<Customer> q = qb.build();
		List<Customer> list = q.list();

		return list;
	}
	
	public List<CustomerBean> getCustomersForUpload() {

		QueryBuilder<Customer> qb = customerDao.queryBuilder();
		qb.where(CustomerDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(CustomerDao.Properties.Name);
		
		Query<Customer> q = qb.build();
		
		ArrayList<CustomerBean> customerBeans = new ArrayList<CustomerBean>();
		
		for (Customer prdGroup : q.list()) {
			
			customerBeans.add(BeanUtil.getBean(prdGroup));
		}
		
		return customerBeans;
	}
	
	public void updateCustomers(List<CustomerBean> customers) {
		
		for (CustomerBean bean : customers) {
			
			boolean isAdd = false;
			
			Customer customer = customerDao.load(bean.getRemote_id());
			
			if (customer == null) {
				customer = new Customer();
				isAdd = true;
			}
			
			BeanUtil.updateBean(customer, bean);
			
			if (isAdd) {
				customerDao.insert(customer);
			} else {
				customerDao.update(customer);
			}
		} 
	}
	
	public void updateCustomerStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			Customer customer = customerDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				customer.setUploadStatus(Constant.STATUS_NO);
				customerDao.update(customer);
			}
		} 
	}
}
