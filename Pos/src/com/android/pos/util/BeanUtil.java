package com.android.pos.util;

import com.android.pos.Constant;
import com.android.pos.dao.Customer;
import com.android.pos.dao.Discount;
import com.android.pos.dao.Employee;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.Product;
import com.android.pos.dao.ProductGroup;
import com.android.pos.dao.TransactionItem;
import com.android.pos.dao.Transactions;
import com.android.pos.dao.User;
import com.android.pos.model.CustomerBean;
import com.android.pos.model.DiscountBean;
import com.android.pos.model.EmployeeBean;
import com.android.pos.model.MerchantBean;
import com.android.pos.model.ProductBean;
import com.android.pos.model.ProductGroupBean;
import com.android.pos.model.TransactionItemBean;
import com.android.pos.model.TransactionsBean;
import com.android.pos.model.UserBean;

public class BeanUtil {
	
	public static ProductGroupBean getBean(ProductGroup productGroup) {
		
		ProductGroupBean bean = new ProductGroupBean();
		
		bean.setMerchant_id(productGroup.getMerchantId());
		bean.setRemote_id(productGroup.getId());
		bean.setName(productGroup.getName());
		bean.setStatus(productGroup.getStatus());
		bean.setCreate_by(productGroup.getCreateBy());
		bean.setCreate_date(productGroup.getCreateDate());
		bean.setUpdate_by(productGroup.getUpdateBy());
		bean.setUpdate_date(productGroup.getUpdateDate());
		
		return bean;
	}
	
	public static void updateBean(ProductGroup productGroup, ProductGroupBean bean) {
		
		productGroup.setMerchantId(bean.getMerchant_id());
		productGroup.setId(bean.getRemote_id());
		productGroup.setName(bean.getName());
		productGroup.setStatus(bean.getStatus());
		productGroup.setUploadStatus(Constant.STATUS_NO);
		productGroup.setCreateBy(bean.getCreate_by());
		productGroup.setCreateDate(bean.getCreate_date());
		productGroup.setUpdateBy(bean.getUpdate_by());
		productGroup.setUpdateDate(bean.getUpdate_date());
	}
	
	public static DiscountBean getBean(Discount discount) {
		
		DiscountBean bean = new DiscountBean();
		
		bean.setMerchant_id(discount.getMerchantId());
		bean.setRemote_id(discount.getId());
		bean.setName(discount.getName());
		bean.setPercentage(discount.getPercentage());
		bean.setStatus(discount.getStatus());
		bean.setCreate_by(discount.getCreateBy());
		bean.setCreate_date(discount.getCreateDate());
		bean.setUpdate_by(discount.getUpdateBy());
		bean.setUpdate_date(discount.getUpdateDate());
		
		return bean;
	}
	
	public static void updateBean(Discount discount, DiscountBean bean) {
		
		discount.setMerchantId(bean.getMerchant_id());
		discount.setId(bean.getRemote_id());
		discount.setName(bean.getName());
		discount.setPercentage(bean.getPercentage());
		discount.setStatus(bean.getStatus());
		discount.setUploadStatus(Constant.STATUS_NO);
		discount.setCreateBy(bean.getCreate_by());
		discount.setCreateDate(bean.getCreate_date());
		discount.setUpdateBy(bean.getUpdate_by());
		discount.setUpdateDate(bean.getUpdate_date());
	}
	
	public static MerchantBean getBean(Merchant merchant) {
		
		MerchantBean bean = new MerchantBean();
		
		bean.setRemote_id(merchant.getId());
		bean.setName(merchant.getName());
		bean.setType(merchant.getType());
		bean.setAddress(merchant.getAddress());
		bean.setTelephone(merchant.getTelephone());
		bean.setContact_name(merchant.getContactName());
		bean.setContact_telephone(merchant.getContactTelephone());
		bean.setLogin_id(merchant.getLoginId());
		bean.setPassword(merchant.getPassword());
		bean.setPeriod_start(merchant.getPeriodStart());
		bean.setPeriod_end(merchant.getPeriodEnd());
		bean.setTax_percentage(merchant.getTaxPercentage());
		bean.setService_charge_percentage(merchant.getServiceChargePercentage());
		bean.setCapacity(merchant.getCapacity());
		bean.setStatus(merchant.getStatus());
		bean.setCreate_by(merchant.getCreateBy());
		bean.setCreate_date(merchant.getCreateDate());
		bean.setUpdate_by(merchant.getUpdateBy());
		bean.setUpdate_date(merchant.getUpdateDate());
		
		return bean;
	}
	
	public static void updateBean(Merchant merchant, MerchantBean bean) {
		
		merchant.setId(bean.getRemote_id());
		merchant.setName(bean.getName());
		merchant.setType(bean.getType());
		merchant.setAddress(bean.getAddress());
		merchant.setTelephone(bean.getTelephone());
		merchant.setContactName(bean.getContact_name());
		merchant.setContactTelephone(bean.getContact_telephone());
		merchant.setLoginId(bean.getLogin_id());
		merchant.setPassword(bean.getPassword());
		merchant.setPeriodStart(bean.getPeriod_start());
		merchant.setPeriodEnd(bean.getPeriod_end());
		merchant.setTaxPercentage(bean.getTax_percentage());
		merchant.setServiceChargePercentage(bean.getService_charge_percentage());
		merchant.setCapacity(bean.getCapacity());
		merchant.setStatus(bean.getStatus());
		merchant.setUploadStatus(Constant.STATUS_NO);
		merchant.setCreateBy(bean.getCreate_by());
		merchant.setCreateDate(bean.getCreate_date());
		merchant.setUpdateBy(bean.getUpdate_by());
		merchant.setUpdateDate(bean.getUpdate_date());
	}
	
	public static EmployeeBean getBean(Employee employee) {
		
		EmployeeBean bean = new EmployeeBean();
		
		bean.setMerchant_id(employee.getMerchantId());
		bean.setRemote_id(employee.getId());
		bean.setName(employee.getName());
		bean.setTelephone(employee.getTelephone());
		bean.setAddress(employee.getAddress());
		bean.setStatus(employee.getStatus());
		bean.setCreate_by(employee.getCreateBy());
		bean.setCreate_date(employee.getCreateDate());
		bean.setUpdate_by(employee.getUpdateBy());
		bean.setUpdate_date(employee.getUpdateDate());
		
		return bean;
	}
	
	public static void updateBean(Employee employee, EmployeeBean bean) {
		
		employee.setMerchantId(bean.getMerchant_id());
		employee.setId(bean.getRemote_id());
		employee.setName(bean.getName());
		employee.setTelephone(bean.getTelephone());
		employee.setAddress(bean.getAddress());
		employee.setStatus(bean.getStatus());
		employee.setUploadStatus(Constant.STATUS_NO);
		employee.setCreateBy(bean.getCreate_by());
		employee.setCreateDate(bean.getCreate_date());
		employee.setUpdateBy(bean.getUpdate_by());
		employee.setUpdateDate(bean.getUpdate_date());
	}
	
	public static CustomerBean getBean(Customer customer) {
		
		CustomerBean bean = new CustomerBean();
		
		bean.setMerchant_id(customer.getMerchantId());
		bean.setRemote_id(customer.getId());
		bean.setName(customer.getName());
		bean.setTelephone(customer.getTelephone());
		bean.setEmail(customer.getEmail());
		bean.setEmail_status(customer.getEmailStatus());
		bean.setAddress(customer.getAddress());
		bean.setStatus(customer.getStatus());
		bean.setCreate_by(customer.getCreateBy());
		bean.setCreate_date(customer.getCreateDate());
		bean.setUpdate_by(customer.getUpdateBy());
		bean.setUpdate_date(customer.getUpdateDate());
		
		return bean;
	}
	
	public static void updateBean(Customer customer, CustomerBean bean) {
		
		customer.setMerchantId(bean.getMerchant_id());
		customer.setId(bean.getRemote_id());
		customer.setName(bean.getName());
		customer.setTelephone(bean.getTelephone());
		customer.setEmail(bean.getEmail());
		customer.setEmailStatus(bean.getEmail_status());
		customer.setAddress(bean.getAddress());
		customer.setStatus(bean.getStatus());
		customer.setUploadStatus(Constant.STATUS_NO);
		customer.setCreateBy(bean.getCreate_by());
		customer.setCreateDate(bean.getCreate_date());
		customer.setUpdateBy(bean.getUpdate_by());
		customer.setUpdateDate(bean.getUpdate_date());
	}
	
	public static ProductBean getBean(Product product) {
		
		ProductBean bean = new ProductBean();
		
		bean.setMerchant_id(product.getMerchantId());
		bean.setRemote_id(product.getId());
		bean.setProduct_group_id(product.getProductGroupId());
		bean.setName(product.getName());
		bean.setType(product.getType());
		bean.setPrice(product.getPrice());
		bean.setCost_price(product.getCostPrice());
		bean.setPic_required(product.getPicRequired());
		bean.setCommision(product.getCommision());
		bean.setPromo_price(product.getPromoPrice());
		bean.setPromo_start(product.getPromoStart());
		bean.setPromo_end(product.getPromoEnd());
		bean.setStock(product.getStock());
		bean.setMin_stock(product.getMinStock());
		bean.setStatus(product.getStatus());
		bean.setCreate_by(product.getCreateBy());
		bean.setCreate_date(product.getCreateDate());
		bean.setUpdate_by(product.getUpdateBy());
		bean.setUpdate_date(product.getUpdateDate());
		
		return bean;
	}
	
	public static void updateBean(Product product, ProductBean bean) {
		
		product.setMerchantId(bean.getMerchant_id());
		product.setId(bean.getRemote_id());
		product.setProductGroupId(bean.getProduct_group_id());
		product.setName(bean.getName());
		product.setType(bean.getType());
		product.setPrice(bean.getPrice());
		product.setCostPrice(bean.getCost_price());
		product.setPicRequired(bean.getPic_required());
		product.setCommision(bean.getCommision());
		product.setPromoPrice(bean.getPromo_price());
		product.setPromoStart(bean.getPromo_start());
		product.setPromoEnd(bean.getPromo_end());
		product.setStock(bean.getStock());
		product.setMinStock(bean.getMin_stock());
		product.setStatus(bean.getStatus());
		product.setUploadStatus(Constant.STATUS_NO);
		product.setCreateBy(bean.getCreate_by());
		product.setCreateDate(bean.getCreate_date());
		product.setUpdateBy(bean.getUpdate_by());
		product.setUpdateDate(bean.getUpdate_date());
	}
	
	public static UserBean getBean(User user) {
		
		UserBean bean = new UserBean();
		
		bean.setMerchant_id(user.getMerchantId());
		bean.setRemote_id(user.getId());
		bean.setName(user.getName());
		bean.setUser_id(user.getUserId());
		bean.setPassword(user.getPassword());
		bean.setRole(user.getRole());
		bean.setStatus(user.getStatus());
		bean.setCreate_by(user.getCreateBy());
		bean.setCreate_date(user.getCreateDate());
		bean.setUpdate_by(user.getUpdateBy());
		bean.setUpdate_date(user.getUpdateDate());
		
		return bean;
	}
	
    public static void updateBean(User user, UserBean bean) {
		
		user.setMerchantId(bean.getMerchant_id());
		user.setId(bean.getRemote_id());
		user.setName(bean.getName());
		user.setUserId(bean.getUser_id());
		user.setPassword(bean.getPassword());
		user.setRole(bean.getRole());
		user.setStatus(bean.getStatus());
		user.setUploadStatus(Constant.STATUS_NO);
		user.setCreateBy(bean.getCreate_by());
		user.setCreateDate(bean.getCreate_date());
		user.setUpdateBy(bean.getUpdate_by());
		user.setUpdateDate(bean.getUpdate_date());
	}
    
    public static TransactionsBean getBean(Transactions transactions) {
		
		TransactionsBean bean = new TransactionsBean();
		
		bean.setMerchant_id(transactions.getMerchantId());
		bean.setRemote_id(transactions.getId());
		bean.setTransaction_no(transactions.getTransactionNo());
		bean.setTransaction_date(transactions.getTransactionDate());
		bean.setOrder_type(transactions.getOrderType());
		bean.setOrder_reference(transactions.getOrderReference());
		bean.setBill_amount(transactions.getBillAmount());
		bean.setDiscount_name(transactions.getDiscountName());
		bean.setDiscount_percentage(transactions.getDiscountPercentage());
		bean.setDiscount_amount(transactions.getDiscountAmount());
		bean.setTax_percentage(transactions.getTaxPercentage());
		bean.setTax_amount(transactions.getTaxAmount());
		bean.setService_charge_percentage(transactions.getServiceChargePercentage());
		bean.setService_charge_amount(transactions.getServiceChargeAmount());
		bean.setTotal_amount(transactions.getTotalAmount());
		bean.setPayment_amount(transactions.getPaymentAmount());
		bean.setReturn_amount(transactions.getReturnAmount());
		bean.setPayment_type(transactions.getPaymentType());
		bean.setCashier_id(transactions.getCashierId());
		bean.setCashier_name(transactions.getCashierName());
		bean.setCustomer_id(transactions.getCustomerId());
		bean.setCustomer_name(transactions.getCustomerName());
		bean.setStatus(transactions.getStatus());
		
		return bean;
	}
    
	public static void updateBean(Transactions transactions, TransactionsBean bean) {
		
		transactions.setMerchantId(bean.getMerchant_id());
		transactions.setId(bean.getRemote_id());
		transactions.setTransactionNo(bean.getTransaction_no());
		transactions.setTransactionDate(bean.getTransaction_date());
		transactions.setOrderType(bean.getOrder_type());
		transactions.setOrderReference(bean.getOrder_reference());
		transactions.setBillAmount(bean.getBill_amount());
		transactions.setDiscountName(bean.getDiscount_name());
		transactions.setDiscountPercentage(bean.getDiscount_percentage());
		transactions.setDiscountAmount(bean.getDiscount_amount());
		transactions.setTaxPercentage(bean.getTax_percentage());
		transactions.setTaxAmount(bean.getTax_amount());
		transactions.setServiceChargePercentage(bean.getService_charge_percentage());
		transactions.setServiceChargeAmount(bean.getService_charge_amount());
		transactions.setTotalAmount(bean.getTotal_amount());
		transactions.setPaymentAmount(bean.getPayment_amount());
		transactions.setReturnAmount(bean.getReturn_amount());
		transactions.setPaymentType(bean.getPayment_type());
		transactions.setCashierId(bean.getCashier_id());
		transactions.setCashierName(bean.getCashier_name());
		transactions.setCustomerId(bean.getCustomer_id());
		transactions.setCustomerName(bean.getCustomer_name());
		transactions.setUploadStatus(Constant.STATUS_NO);
		transactions.setStatus(bean.getStatus());
	}
	
    public static TransactionItemBean getBean(TransactionItem transactionItem) {
		
		TransactionItemBean bean = new TransactionItemBean();
		
		bean.setMerchant_id(transactionItem.getMerchantId());
		bean.setRemote_id(transactionItem.getId());
		bean.setTransaction_id(transactionItem.getTransactionId());
		bean.setProduct_id(transactionItem.getProductId());
		bean.setProduct_name(transactionItem.getProductName());
		bean.setProduct_type(transactionItem.getProductType());
		bean.setPrice(transactionItem.getPrice());
		bean.setCost_price(transactionItem.getCostPrice());
		bean.setDiscount(transactionItem.getDiscount());
		bean.setQuantity(transactionItem.getQuantity());
		bean.setCommision(transactionItem.getCommision());
		bean.setEmployee_id(transactionItem.getEmployeeId());
		
		return bean;
	}
    
	public static void updateBean(TransactionItem transactionItem, TransactionItemBean bean) {
		
		transactionItem.setMerchantId(bean.getMerchant_id());
		transactionItem.setId(bean.getRemote_id());
		transactionItem.setTransactionId(bean.getTransaction_id());
		transactionItem.setProductId(bean.getProduct_id());
		transactionItem.setProductName(bean.getProduct_name());
		transactionItem.setProductType(bean.getProduct_type());
		transactionItem.setPrice(bean.getPrice());
		transactionItem.setCostPrice(bean.getCost_price());
		transactionItem.setDiscount(bean.getDiscount());
		transactionItem.setQuantity(bean.getQuantity());
		transactionItem.setCommision(bean.getCommision());
		transactionItem.setEmployeeId(bean.getEmployee_id());
		transactionItem.setUploadStatus(Constant.STATUS_NO);
	}
}
