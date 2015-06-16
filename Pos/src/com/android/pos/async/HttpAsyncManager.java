package com.android.pos.async;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.android.pos.Config;
import com.android.pos.Constant;
import com.android.pos.Installation;
import com.android.pos.R;
import com.android.pos.auth.LoginListener;
import com.android.pos.dao.BillsDaoService;
import com.android.pos.dao.CustomerDaoService;
import com.android.pos.dao.DiscountDaoService;
import com.android.pos.dao.EmployeeDaoService;
import com.android.pos.dao.InventoryDaoService;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantAccessDaoService;
import com.android.pos.dao.MerchantDaoService;
import com.android.pos.dao.OrderItem;
import com.android.pos.dao.OrderItemDaoService;
import com.android.pos.dao.Orders;
import com.android.pos.dao.OrdersDaoService;
import com.android.pos.dao.ProductDaoService;
import com.android.pos.dao.ProductGroupDaoService;
import com.android.pos.dao.SupplierDaoService;
import com.android.pos.dao.TransactionItemDaoService;
import com.android.pos.dao.TransactionsDaoService;
import com.android.pos.dao.User;
import com.android.pos.dao.UserAccessDaoService;
import com.android.pos.dao.UserDaoService;
import com.android.pos.model.DeviceBean;
import com.android.pos.model.MerchantBean;
import com.android.pos.model.OrderItemBean;
import com.android.pos.model.OrdersBean;
import com.android.pos.model.SyncRequestBean;
import com.android.pos.model.SyncResponseBean;
import com.android.pos.model.UserBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.UserUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class HttpAsyncManager {
	
	private static HttpAsyncTask task;
	private static HttpAsyncListener mAsyncListener;
	private static HttpPost httpPost;
	
	private Context mContext;
	private DeviceBean mDevice;
	private String mLoginId;
	private String mPassword;

	private ProductGroupDaoService mProductGroupDaoService;
	private DiscountDaoService mDiscountDaoService;
	private MerchantDaoService mMerchantDaoService;
	private EmployeeDaoService mEmployeeDaoService;
	private CustomerDaoService mCustomerDaoService;
	private ProductDaoService mProductDaoService;
	private UserDaoService mUserDaoService;
	private UserAccessDaoService mUserAccessDaoService;
	private TransactionsDaoService mTransactionsDaoService;
	private TransactionItemDaoService mTransactionItemDaoService;
	private SupplierDaoService mSupplierDaoService;
	private BillsDaoService mBillDaoService;
	private InventoryDaoService mInventoryDaoService;
	private MerchantAccessDaoService mMerchantAccessDaoService;
	private OrdersDaoService mOrdersDaoService;
	private OrderItemDaoService mOrderItemDaoService;

	private int mTaskIndex = 0;
	
	private List<String> mTasks;
	private List<String> mGetTasks;
	private List<String> mUpdateTasks;
	
	private Map<String, String> taskMessage;
	
	private List<OrdersBean> mOrders;
	private List<OrderItemBean> mOrderItems;
	
	private static ObjectMapper mapper;
	
	private Long startTime;
	private Date mSyncDate;
	
	static {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public HttpAsyncManager(Context context) {

		this.mContext = context;
		
		mDevice = new DeviceBean();
		
		mAsyncListener = (HttpAsyncListener) context;

		mProductGroupDaoService = new ProductGroupDaoService();
		mDiscountDaoService = new DiscountDaoService();
		mMerchantDaoService = new MerchantDaoService();
		mEmployeeDaoService = new EmployeeDaoService();
		mCustomerDaoService = new CustomerDaoService();
		mProductDaoService = new ProductDaoService();
		mUserDaoService = new UserDaoService();
		mUserAccessDaoService = new UserAccessDaoService();
		mTransactionsDaoService = new TransactionsDaoService();
		mTransactionItemDaoService = new TransactionItemDaoService();
		mSupplierDaoService = new SupplierDaoService();
		mBillDaoService = new BillsDaoService();
		mInventoryDaoService = new InventoryDaoService();
		mMerchantAccessDaoService = new MerchantAccessDaoService();
		mOrdersDaoService = new OrdersDaoService();
		mOrderItemDaoService = new OrderItemDaoService();
		
		mTasks = new ArrayList<String>();
		mGetTasks = new ArrayList<String>();
		mUpdateTasks = new ArrayList<String>();
		
		taskMessage = new HashMap<String, String>();
		
		taskMessage.put(Constant.TASK_VALIDATE_MERCHANT, context.getString(R.string.task_validate_merchant));
		taskMessage.put(Constant.TASK_VALIDATE_USER, context.getString(R.string.task_validate_user));
		taskMessage.put(Constant.TASK_ROOT_GET_MERCHANT, context.getString(R.string.task_root_get_merchant));
		taskMessage.put(Constant.TASK_ROOT_GET_MERCHANT_ACCESS, context.getString(R.string.task_root_get_merchant_access));
		taskMessage.put(Constant.TASK_SUBMIT_ORDERS, context.getString(R.string.task_submit_orders));
		taskMessage.put(Constant.TASK_SUBMIT_ORDER_ITEMS, context.getString(R.string.task_submit_order_items));
		taskMessage.put(Constant.TASK_GET_ORDERS, context.getString(R.string.task_get_orders));
		taskMessage.put(Constant.TASK_GET_ORDER_ITEMS, context.getString(R.string.task_get_order_item));
		taskMessage.put(Constant.TASK_DELETE_ORDERS, context.getString(R.string.task_delete_orders));
		taskMessage.put(Constant.TASK_GET_PRODUCT_GROUP, context.getString(R.string.task_get_product_group));
		taskMessage.put(Constant.TASK_UPDATE_PRODUCT_GROUP, context.getString(R.string.task_update_product_group));
		taskMessage.put(Constant.TASK_GET_DISCOUNT, context.getString(R.string.task_get_discount));
		taskMessage.put(Constant.TASK_UPDATE_DISCOUNT, context.getString(R.string.task_update_discount));
		taskMessage.put(Constant.TASK_GET_EMPLOYEE, context.getString(R.string.task_get_employee));
		taskMessage.put(Constant.TASK_UPDATE_EMPLOYEE, context.getString(R.string.task_update_employee));
		taskMessage.put(Constant.TASK_GET_CUSTOMER, context.getString(R.string.task_get_customer));
		taskMessage.put(Constant.TASK_UPDATE_CUSTOMER, context.getString(R.string.task_update_customer));
		taskMessage.put(Constant.TASK_GET_PRODUCT, context.getString(R.string.task_get_product));
		taskMessage.put(Constant.TASK_UPDATE_PRODUCT, context.getString(R.string.task_update_product));
		taskMessage.put(Constant.TASK_GET_USER, context.getString(R.string.task_get_user));
		taskMessage.put(Constant.TASK_UPDATE_USER, context.getString(R.string.task_update_user));
		taskMessage.put(Constant.TASK_GET_USER_ACCESS, context.getString(R.string.task_get_user_access));
		taskMessage.put(Constant.TASK_UPDATE_USER_ACCESS, context.getString(R.string.task_update_user_access));
		taskMessage.put(Constant.TASK_GET_TRANSACTIONS, context.getString(R.string.task_get_transactions));
		taskMessage.put(Constant.TASK_UPDATE_TRANSACTIONS, context.getString(R.string.task_update_transactions));
		taskMessage.put(Constant.TASK_GET_TRANSACTION_ITEM, context.getString(R.string.task_get_transaction_item));
		taskMessage.put(Constant.TASK_UPDATE_TRANSACTION_ITEM, context.getString(R.string.task_update_transaction_item));
		taskMessage.put(Constant.TASK_GET_SUPPLIER, context.getString(R.string.task_get_supplier));
		taskMessage.put(Constant.TASK_UPDATE_SUPPLIER, context.getString(R.string.task_update_supplier));
		taskMessage.put(Constant.TASK_GET_BILL, context.getString(R.string.task_get_bill));
		taskMessage.put(Constant.TASK_UPDATE_BILL, context.getString(R.string.task_update_bill));
		taskMessage.put(Constant.TASK_GET_INVENTORY, context.getString(R.string.task_get_inventory));
		taskMessage.put(Constant.TASK_UPDATE_INVENTORY, context.getString(R.string.task_update_inventory));
		taskMessage.put(Constant.TASK_GET_MERCHANT, context.getString(R.string.task_get_merchant));
		taskMessage.put(Constant.TASK_UPDATE_MERCHANT, context.getString(R.string.task_update_merchant));
		taskMessage.put(Constant.TASK_GET_MERCHANT_ACCESS, context.getString(R.string.task_get_merchant_access));
		taskMessage.put(Constant.TASK_UPDATE_MERCHANT_ACCESS, context.getString(R.string.task_update_merchant_access));
		taskMessage.put(Constant.TASK_GET_LAST_SYNC, context.getString(R.string.task_get_last_sync));
		taskMessage.put(Constant.TASK_UPDATE_LAST_SYNC, context.getString(R.string.task_update_last_sync));
	}
	
	private String getMessage(String task) {
		
		return taskMessage.get(task);
	} 
	
	private void executeNextTask() {
		
		if (mTaskIndex >= mTasks.size()) {
			return;
		}
		
		String taskName = mTasks.get(mTaskIndex);
		String message = getMessage(mTasks.get(mTaskIndex));
		
		if (Constant.TASK_COMPLETED.equals(taskName)) {
			syncCompleted();
			return;
		}
		
		int progress = mTaskIndex * 100 / mTasks.size();
		
		mAsyncListener.setSyncProgress(progress);
		mAsyncListener.setSyncMessage(message);
		
		task = new HttpAsyncTask();
		task.execute(taskName);
		
		if (mTaskIndex == 0) {
				
			Handler handler = new Handler();
			handler.postDelayed(getTimeOutHandler(task), Constant.TIMEOUT);
		}
		
		mTaskIndex++;    	
	}
	
	public Runnable getTimeOutHandler(final HttpAsyncTask task) {
		
		return new Runnable() {
			
			@Override
			public void run() {
				
				if (task.getStatus() == AsyncTask.Status.RUNNING) {
					task.cancel(true);
					abortHttpRequest();
					mAsyncListener.onTimeOut();
				}
			}
		};
	}
	
	public static boolean stopSyncTask() {
		
		boolean isCancelled = false;
		
		if (task.getStatus() == AsyncTask.Status.RUNNING) {
			task.cancel(true);
			abortHttpRequest();
			isCancelled = true;
		}
		
		return isCancelled;
	}
	
	public void validateMerchant(String loginId, String password) {
		
		startTime = new Date().getTime();
		
		mLoginId = loginId;
		mPassword = password;
		
		mTaskIndex = 0;
		
		mTasks.clear();
		mTasks.add(Constant.TASK_VALIDATE_MERCHANT);
		
		executeNextTask();
	}
	
	public void validateUser(String userId, String password) {
		
		startTime = new Date().getTime();
		
		mLoginId = userId;
		mPassword = password;
		
		mTaskIndex = 0;
		
		mTasks.clear();
		mTasks.add(Constant.TASK_VALIDATE_USER);
		
		executeNextTask();
	}
	
	public void syncMerchants() {
		
		startTime = new Date().getTime();
		
		mTaskIndex = 0;
		
		mTasks.clear();
		mTasks.add(Constant.TASK_GET_LAST_SYNC);
		
		mGetTasks.clear();
		mGetTasks.add(Constant.TASK_ROOT_GET_MERCHANT);
		mGetTasks.add(Constant.TASK_ROOT_GET_MERCHANT_ACCESS);
		
		mUpdateTasks.clear();
		mUpdateTasks.add(Constant.TASK_UPDATE_MERCHANT);
		mUpdateTasks.add(Constant.TASK_UPDATE_MERCHANT_ACCESS);
		
		mTasks.addAll(getTaskWithUpdate(mUpdateTasks));
		
		mTasks.add(Constant.TASK_UPDATE_LAST_SYNC);
		mTasks.add(Constant.TASK_COMPLETED);
		
		executeNextTask();
	}
	
	public void syncMerchant() {
		
		startTime = new Date().getTime();
		
		mTaskIndex = 0;
		
		mTasks.clear();
		mTasks.add(Constant.TASK_GET_LAST_SYNC);
		
		mGetTasks.clear();
		mGetTasks.add(Constant.TASK_GET_USER);
		mGetTasks.add(Constant.TASK_GET_USER_ACCESS);
		mGetTasks.add(Constant.TASK_GET_MERCHANT_ACCESS);
		mGetTasks.add(Constant.TASK_GET_MERCHANT);
		
		mUpdateTasks.clear();
		mUpdateTasks.add(Constant.TASK_UPDATE_USER);
		mUpdateTasks.add(Constant.TASK_UPDATE_USER_ACCESS);
		mUpdateTasks.add(Constant.TASK_UPDATE_MERCHANT);
		mUpdateTasks.add(Constant.TASK_UPDATE_MERCHANT_ACCESS);
		
		mTasks.addAll(getTaskWithUpdate(mUpdateTasks));
		
		mTasks.add(Constant.TASK_COMPLETED);
		
		executeNextTask();
	}
	
	public void syncUsers() {
		
		startTime = new Date().getTime();
		
		mTaskIndex = 0;
		
		mTasks.clear();
		mTasks.add(Constant.TASK_GET_LAST_SYNC);
		
		mGetTasks.clear();
		mGetTasks.add(Constant.TASK_GET_USER);
		mGetTasks.add(Constant.TASK_GET_USER_ACCESS);
		mGetTasks.add(Constant.TASK_GET_MERCHANT_ACCESS);
		
		mUpdateTasks.clear();
		mUpdateTasks.add(Constant.TASK_UPDATE_USER);
		mUpdateTasks.add(Constant.TASK_UPDATE_USER_ACCESS);
		
		mTasks.addAll(getTaskWithUpdate(mUpdateTasks));
		
		mTasks.add(Constant.TASK_COMPLETED);
		
		executeNextTask();
	}
	
	public void sync() {
		
		startTime = new Date().getTime();
		
		mTaskIndex = 0;
		
		mTasks.clear();
		
		mTasks.add(Constant.TASK_GET_LAST_SYNC);
		
		mGetTasks.clear();
		mGetTasks.add(Constant.TASK_GET_PRODUCT_GROUP);
		mGetTasks.add(Constant.TASK_GET_DISCOUNT);
		mGetTasks.add(Constant.TASK_GET_EMPLOYEE);
		mGetTasks.add(Constant.TASK_GET_CUSTOMER);
		mGetTasks.add(Constant.TASK_GET_PRODUCT);
		mGetTasks.add(Constant.TASK_GET_USER);
		mGetTasks.add(Constant.TASK_GET_USER_ACCESS);
		mGetTasks.add(Constant.TASK_GET_TRANSACTIONS);
		mGetTasks.add(Constant.TASK_GET_TRANSACTION_ITEM);
		mGetTasks.add(Constant.TASK_GET_SUPPLIER);
		mGetTasks.add(Constant.TASK_GET_BILL);
		mGetTasks.add(Constant.TASK_GET_INVENTORY);
		mGetTasks.add(Constant.TASK_GET_MERCHANT);
		mGetTasks.add(Constant.TASK_GET_MERCHANT_ACCESS);
		
		mUpdateTasks.clear();
		mUpdateTasks.add(Constant.TASK_UPDATE_PRODUCT_GROUP);
		mUpdateTasks.add(Constant.TASK_UPDATE_DISCOUNT);
		mUpdateTasks.add(Constant.TASK_UPDATE_EMPLOYEE);
		mUpdateTasks.add(Constant.TASK_UPDATE_CUSTOMER);
		mUpdateTasks.add(Constant.TASK_UPDATE_PRODUCT);
		mUpdateTasks.add(Constant.TASK_UPDATE_USER);
		mUpdateTasks.add(Constant.TASK_UPDATE_USER_ACCESS);
		mUpdateTasks.add(Constant.TASK_UPDATE_TRANSACTIONS);
		mUpdateTasks.add(Constant.TASK_UPDATE_TRANSACTION_ITEM);
		mUpdateTasks.add(Constant.TASK_UPDATE_SUPPLIER);
		mUpdateTasks.add(Constant.TASK_UPDATE_BILL);
		mUpdateTasks.add(Constant.TASK_UPDATE_INVENTORY);
		mUpdateTasks.add(Constant.TASK_UPDATE_MERCHANT);
		mUpdateTasks.add(Constant.TASK_UPDATE_MERCHANT_ACCESS);
		
		mTasks.addAll(getTaskWithUpdate(mUpdateTasks));
		
		mTasks.add(Constant.TASK_UPDATE_LAST_SYNC);
		mTasks.add(Constant.TASK_COMPLETED);
		
		executeNextTask();
	}
	
	public void submitOrders(Orders orders, List<OrderItem> orderItems) {
		
		startTime = new Date().getTime();
		
		mOrders = new ArrayList<OrdersBean>();
		
		mOrders.add(BeanUtil.getBean(orders));
		
		mOrderItems = new ArrayList<OrderItemBean>();
		
		for (OrderItem orderItem : orderItems) {
			
			mOrderItems.add(BeanUtil.getBean(orderItem));
		}
		
		mTaskIndex = 0;
		
		mTasks.clear();
		mTasks.add(Constant.TASK_SUBMIT_ORDERS);
		mTasks.add(Constant.TASK_SUBMIT_ORDER_ITEMS);
		mTasks.add(Constant.TASK_COMPLETED);
		
		executeNextTask();
	}
	
	public void getOrders() {
		
		startTime = new Date().getTime();
		
		mTaskIndex = 0;
		
		mTasks.clear();
		mTasks.add(Constant.TASK_GET_ORDERS);
		mTasks.add(Constant.TASK_GET_ORDER_ITEMS);
		mTasks.add(Constant.TASK_DELETE_ORDERS);
		mTasks.add(Constant.TASK_COMPLETED);
		
		executeNextTask();
	}
	
	public void syncProducts() {
		
		startTime = new Date().getTime();
		
		mTaskIndex = 0;
		
		mTasks.clear();
		mTasks.add(Constant.TASK_GET_LAST_SYNC);
		
		mGetTasks.clear();
		mGetTasks.add(Constant.TASK_GET_PRODUCT_GROUP);
		mGetTasks.add(Constant.TASK_GET_PRODUCT);
		
		mTasks.add(Constant.TASK_UPDATE_LAST_SYNC);
		mTasks.add(Constant.TASK_COMPLETED);
		
		executeNextTask();
	}

	private void syncCompleted() {
		
		System.out.println("Processing Time : " + (new Date().getTime() - startTime));
		
		mAsyncListener.setSyncProgress(100);
	}
	
	private List<String> getTaskWithUpdate(List<String> tasks) {
		
		List<String> taskWithUpdates = new ArrayList<String>();
		
		for (String task : tasks) {
			
			if (Constant.TASK_UPDATE_PRODUCT_GROUP.equals(task) &&  mProductGroupDaoService.hasUpdate()) {
				taskWithUpdates.add(Constant.TASK_UPDATE_PRODUCT_GROUP);
			
			} else if (Constant.TASK_UPDATE_PRODUCT.equals(task) &&  mProductDaoService.hasUpdate()) {
				taskWithUpdates.add(Constant.TASK_UPDATE_PRODUCT);
			
			} else if (Constant.TASK_UPDATE_DISCOUNT.equals(task) &&  mDiscountDaoService.hasUpdate()) {
				taskWithUpdates.add(Constant.TASK_UPDATE_DISCOUNT);
			
			} else if (Constant.TASK_UPDATE_EMPLOYEE.equals(task) &&  mEmployeeDaoService.hasUpdate()) {
				taskWithUpdates.add(Constant.TASK_UPDATE_EMPLOYEE);
			
			} else if (Constant.TASK_UPDATE_CUSTOMER.equals(task) &&  mCustomerDaoService.hasUpdate()) {
				taskWithUpdates.add(Constant.TASK_UPDATE_CUSTOMER);
			
			} else if (Constant.TASK_UPDATE_USER.equals(task) &&  mUserDaoService.hasUpdate()) {
				taskWithUpdates.add(Constant.TASK_UPDATE_USER);
			
			} else if (Constant.TASK_UPDATE_USER_ACCESS.equals(task) &&  mUserAccessDaoService.hasUpdate()) {
				taskWithUpdates.add(Constant.TASK_UPDATE_USER_ACCESS);
			
			} else if (Constant.TASK_UPDATE_TRANSACTIONS.equals(task) &&  mTransactionsDaoService.hasUpdate()) {
				taskWithUpdates.add(Constant.TASK_UPDATE_TRANSACTIONS);
			
			} else if (Constant.TASK_UPDATE_TRANSACTION_ITEM.equals(task) &&  mTransactionItemDaoService.hasUpdate()) {
				taskWithUpdates.add(Constant.TASK_UPDATE_TRANSACTION_ITEM);
			
			} else if (Constant.TASK_UPDATE_SUPPLIER.equals(task) &&  mSupplierDaoService.hasUpdate()) {
				taskWithUpdates.add(Constant.TASK_UPDATE_SUPPLIER);
			
			} else if (Constant.TASK_UPDATE_BILL.equals(task) &&  mBillDaoService.hasUpdate()) {
				taskWithUpdates.add(Constant.TASK_UPDATE_BILL);
			
			} else if (Constant.TASK_UPDATE_INVENTORY.equals(task) &&  mInventoryDaoService.hasUpdate()) {
				taskWithUpdates.add(Constant.TASK_UPDATE_INVENTORY);
			
			} else if (Constant.TASK_UPDATE_MERCHANT.equals(task) &&  mMerchantDaoService.hasUpdate()) {
				taskWithUpdates.add(Constant.TASK_UPDATE_MERCHANT);
			
			} else if (Constant.TASK_UPDATE_MERCHANT_ACCESS.equals(task) &&  mMerchantAccessDaoService.hasUpdate()) {
				taskWithUpdates.add(Constant.TASK_UPDATE_MERCHANT_ACCESS);
			
			} 
		}
		
		return taskWithUpdates;
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {

		String task;

		@Override
		protected String doInBackground(String... tasks) {

			task = tasks[0];
			
			Long merchantId = null;
			
			if (UserUtil.isRoot()) {
				merchantId = Long.valueOf(-1);
			} else {
				merchantId = MerchantUtil.getMerchantId();
			}
			
			String url = Constant.EMPTY_STRING;
			
			SyncRequestBean request = new SyncRequestBean();
			
			request.setMerchant_id(merchantId);
			request.setUuid(Installation.getInstallationId(mContext));
			request.setLast_sync_date(mDevice.getLast_sync_date());
			request.setSync_date(mSyncDate);
			
			if (Constant.TASK_VALIDATE_MERCHANT.equals(tasks[0])) {
				
				url = Config.SERVER_URL + "/merchantValidateJsonServlet";

				MerchantBean merchant = new MerchantBean();
				
				merchant.setLogin_id(mLoginId);
				merchant.setPassword(mPassword);
				
				request.setMerchant(merchant);

			} else if (Constant.TASK_VALIDATE_USER.equals(tasks[0])) {
				
				url = Config.SERVER_URL + "/userValidateJsonServlet";

				UserBean user = new UserBean();
				
				user.setUser_id(mLoginId);
				user.setPassword(mPassword);
				
				request.setUser(user);

			} else if (Constant.TASK_ROOT_GET_MERCHANT.equals(tasks[0])) {

				url = Config.SERVER_URL + "/merchantGetAllJsonServlet";

			} else if (Constant.TASK_GET_LAST_SYNC.equals(tasks[0])) {

				url = Config.SERVER_URL + "/getLastSyncJsonServlet";

				DeviceBean device = new DeviceBean();
				device.setMerchant_id(merchantId);
				device.setUuid(Installation.getInstallationId(mContext));
				
				request.setDevice(device);
				request.setGetRequests(mGetTasks);

			} else if (Constant.TASK_SUBMIT_ORDERS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/ordersUpdateJsonServlet";
				
				request.setOrders(mOrders);

			} else if (Constant.TASK_SUBMIT_ORDER_ITEMS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/orderItemUpdateJsonServlet";
				
				request.setOrderItems(mOrderItems);

			} else if (Constant.TASK_GET_ORDERS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/ordersGetJsonServlet";
				
			} else if (Constant.TASK_GET_ORDER_ITEMS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/orderItemGetJsonServlet";
				
			} else if (Constant.TASK_DELETE_ORDERS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/ordersDeleteJsonServlet";
				
				request.setOrders(mOrders);

			} else if (Constant.TASK_GET_PRODUCT_GROUP.equals(tasks[0])) {

				url = Config.SERVER_URL + "/productGroupGetJsonServlet";
				
			} else if (Constant.TASK_UPDATE_PRODUCT_GROUP.equals(tasks[0])) {

				url = Config.SERVER_URL + "/productGroupUpdateJsonServlet";
				
				request.setProductGroups(mProductGroupDaoService.getProductGroupsForUpload());
				
			} else if (Constant.TASK_GET_DISCOUNT.equals(tasks[0])) {

				url = Config.SERVER_URL + "/discountGetJsonServlet";

			} else if (Constant.TASK_UPDATE_DISCOUNT.equals(tasks[0])) {

				url = Config.SERVER_URL + "/discountUpdateJsonServlet";

				request.setDiscounts(mDiscountDaoService.getDiscountsForUpload());

			} else if (Constant.TASK_GET_EMPLOYEE.equals(tasks[0])) {

				url = Config.SERVER_URL + "/employeeGetJsonServlet";

			} else if (Constant.TASK_UPDATE_EMPLOYEE.equals(tasks[0])) {

				url = Config.SERVER_URL + "/employeeUpdateJsonServlet";

				request.setEmployees(mEmployeeDaoService.getEmployeesForUpload());

			} else if (Constant.TASK_GET_CUSTOMER.equals(tasks[0])) {

				url = Config.SERVER_URL + "/customerGetJsonServlet";

			} else if (Constant.TASK_UPDATE_CUSTOMER.equals(tasks[0])) {

				url = Config.SERVER_URL + "/customerUpdateJsonServlet";

				request.setCustomers(mCustomerDaoService.getCustomersForUpload());

			} else if (Constant.TASK_GET_PRODUCT.equals(tasks[0])) {

				url = Config.SERVER_URL + "/productGetJsonServlet";

			} else if (Constant.TASK_UPDATE_PRODUCT.equals(tasks[0])) {

				url = Config.SERVER_URL + "/productUpdateJsonServlet";

				request.setProducts(mProductDaoService.getProductsForUpload());

			} else if (Constant.TASK_GET_USER.equals(tasks[0])) {

				url = Config.SERVER_URL + "/userGetJsonServlet";
				
			} else if (Constant.TASK_UPDATE_USER.equals(tasks[0])) {

				url = Config.SERVER_URL + "/userUpdateJsonServlet";

				request.setUsers(mUserDaoService.getUsersForUpload());

			} else if (Constant.TASK_GET_USER_ACCESS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/userAccessGetJsonServlet";

			} else if (Constant.TASK_UPDATE_USER_ACCESS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/userAccessUpdateJsonServlet";

				request.setUserAccesses(mUserAccessDaoService.getUserAccessesForUpload());

			} else if (Constant.TASK_GET_TRANSACTIONS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/transactionsGetJsonServlet";
				
			} else if (Constant.TASK_UPDATE_TRANSACTIONS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/transactionsUpdateJsonServlet";

				request.setTransactions(mTransactionsDaoService.getTransactionsForUpload());

			} else if (Constant.TASK_GET_TRANSACTION_ITEM.equals(tasks[0])) {

				url = Config.SERVER_URL + "/transactionItemGetJsonServlet";
				
			} else if (Constant.TASK_UPDATE_TRANSACTION_ITEM.equals(tasks[0])) {

				url = Config.SERVER_URL + "/transactionItemUpdateJsonServlet";

				request.setTransactionItems(mTransactionItemDaoService.getTransactionItemsForUpload());

			} else if (Constant.TASK_GET_SUPPLIER.equals(tasks[0])) {

				url = Config.SERVER_URL + "/supplierGetJsonServlet";

			} else if (Constant.TASK_UPDATE_SUPPLIER.equals(tasks[0])) {

				url = Config.SERVER_URL + "/supplierUpdateJsonServlet";

				request.setSuppliers(mSupplierDaoService.getSuppliersForUpload());

			} else if (Constant.TASK_GET_BILL.equals(tasks[0])) {

				url = Config.SERVER_URL + "/billGetJsonServlet";
				
			} else if (Constant.TASK_UPDATE_BILL.equals(tasks[0])) {

				url = Config.SERVER_URL + "/billUpdateJsonServlet";

				request.setBills(mBillDaoService.getBillsForUpload());

			} else if (Constant.TASK_GET_INVENTORY.equals(tasks[0])) {

				url = Config.SERVER_URL + "/inventoryGetJsonServlet";
				
			} else if (Constant.TASK_UPDATE_INVENTORY.equals(tasks[0])) {

				url = Config.SERVER_URL + "/inventoryUpdateJsonServlet";

				request.setInventories(mInventoryDaoService.getInventoriesForUpload());

			} else if (Constant.TASK_GET_MERCHANT.equals(tasks[0])) {

				url = Config.SERVER_URL + "/merchantGetJsonServlet";
				
			} else if (Constant.TASK_UPDATE_MERCHANT.equals(tasks[0])) {

				url = Config.SERVER_URL + "/merchantUpdateJsonServlet";

				request.setMerchants(mMerchantDaoService.getMerchantsForUpload());

			} else if (Constant.TASK_ROOT_GET_MERCHANT_ACCESS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/merchantAccessGetAllJsonServlet";
				
			} else if (Constant.TASK_GET_MERCHANT_ACCESS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/merchantAccessGetJsonServlet";
				
			} else if (Constant.TASK_UPDATE_MERCHANT_ACCESS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/merchantAccessUpdateJsonServlet";

				request.setMerchantAccesses(mMerchantAccessDaoService.getMerchantAccessesForUpload());

			} else if (Constant.TASK_UPDATE_LAST_SYNC.equals(tasks[0])) {

				url = Config.SERVER_URL + "/updateLastSyncJsonServlet";
				
				//mDevice.setLast_sync_date(mSyncDate);
				request.setDevice(mDevice);
			}
			
			return POST(url, request);
		}

		@Override
		protected void onPostExecute(String result) {
			
			try {
				
				SyncResponseBean resp = mapper.readValue(result, SyncResponseBean.class);
				
				if (SyncResponseBean.ERROR.equals(resp.getRespCode())) {
					
					mAsyncListener.onSyncError(resp.getRespDescription());
					
				} else if (SyncResponseBean.SUCCESS.equals(resp.getRespCode())) {
					
					if (Constant.TASK_VALIDATE_MERCHANT.equals(task)) {
	
						MerchantBean bean =  resp.getMerchant();
						
						Merchant merchant = null;
						
						if (bean != null) {
							
							merchant = new Merchant();
							BeanUtil.updateBean(merchant, bean);
						}
						
						LoginListener mLoginListener = (LoginListener) mContext;
						mLoginListener.onMerchantValidated(merchant);
						
					} else if (Constant.TASK_VALIDATE_USER.equals(task)) {
	
						UserBean bean =  resp.getUser();
						
						User user = null;
						
						if (bean != null) {
							
							user = new User();
							user.setUserId(bean.getUser_id());
							user.setName(bean.getName());
						}
						
						LoginListener mLoginListener = (LoginListener) mContext;
						mLoginListener.onUserValidated(user);
						
					} else if (Constant.TASK_GET_LAST_SYNC.equals(task)) {
	
						mDevice = resp.getDevice();
						mSyncDate = resp.getRespDate();
						
						mTasks.remove(0);
						mTaskIndex--;
						
						List<String> taskHasUpdates = resp.getTaskHasUpdates();
						taskHasUpdates.addAll(mTasks);
						
						mTasks.clear();
						mTasks.addAll(taskHasUpdates);
	
					} else if (Constant.TASK_SUBMIT_ORDERS.equals(task)) {
	
					} else if (Constant.TASK_SUBMIT_ORDER_ITEMS.equals(task)) {
	
					} else if (Constant.TASK_GET_ORDERS.equals(task)) {
	
						mOrders = resp.getOrders();
						
						mOrdersDaoService.addOrders(mOrders);
	
					} else if (Constant.TASK_GET_ORDER_ITEMS.equals(task)) {
	
						mOrderItemDaoService.addOrderItems(resp.getOrderItems());
	
					} else if (Constant.TASK_DELETE_ORDERS.equals(task)) {
						
					} else if (Constant.TASK_GET_PRODUCT_GROUP.equals(task)) {
						
						mProductGroupDaoService.updateProductGroups(resp.getProductGroups());
	
					} else if (Constant.TASK_UPDATE_PRODUCT_GROUP.equals(task)) {
	
						mProductGroupDaoService.updateProductGroupStatus(resp.getStatus());
						
					} else if (Constant.TASK_GET_DISCOUNT.equals(task)) {
	
						mDiscountDaoService.updateDiscounts(resp.getDiscounts());
	
					} else if (Constant.TASK_UPDATE_DISCOUNT.equals(task)) {
	
						mDiscountDaoService.updateDiscountStatus(resp.getStatus());
	
					} else if (Constant.TASK_GET_EMPLOYEE.equals(task)) {
	
						mEmployeeDaoService.updateEmployees(resp.getEmployees());
	
					} else if (Constant.TASK_UPDATE_EMPLOYEE.equals(task)) {
	
						mEmployeeDaoService.updateEmployeeStatus(resp.getStatus());
	
					} else if (Constant.TASK_GET_CUSTOMER.equals(task)) {
	
						mCustomerDaoService.updateCustomers(resp.getCustomers());
	
					} else if (Constant.TASK_UPDATE_CUSTOMER.equals(task)) {
	
						mCustomerDaoService.updateCustomerStatus(resp.getStatus());
	
					} else if (Constant.TASK_GET_PRODUCT.equals(task)) {
	
						mProductDaoService.updateProducts(resp.getProducts());
	
					} else if (Constant.TASK_UPDATE_PRODUCT.equals(task)) {
	
						mProductDaoService.updateProductStatus(resp.getStatus());
	
					} else if (Constant.TASK_GET_USER.equals(task)) {
	
						mUserDaoService.updateUsers(resp.getUsers());
	
					} else if (Constant.TASK_UPDATE_USER.equals(task)) {
	
						mUserDaoService.updateUserStatus(resp.getStatus());
	
					} else if (Constant.TASK_GET_USER_ACCESS.equals(task)) {
	
						mUserAccessDaoService.updateUserAccesses(resp.getUserAccesses());
	
					} else if (Constant.TASK_UPDATE_USER_ACCESS.equals(task)) {
	
						mUserAccessDaoService.updateUserAccessStatus(resp.getStatus());
	
					} else if (Constant.TASK_GET_TRANSACTIONS.equals(task)) {
	
						mTransactionsDaoService.updateTransactions(resp.getTransactions());
	
					} else if (Constant.TASK_UPDATE_TRANSACTIONS.equals(task)) {
	
						mTransactionsDaoService.updateTransactionsStatus(resp.getStatus());
	
					} else if (Constant.TASK_GET_TRANSACTION_ITEM.equals(task)) {
	
						mTransactionItemDaoService.updateTransactionItems(resp.getTransactionItems());
	
					} else if (Constant.TASK_UPDATE_TRANSACTION_ITEM.equals(task)) {
	
						mTransactionItemDaoService.updateTransactionItemStatus(resp.getStatus());
	
					} else if (Constant.TASK_GET_SUPPLIER.equals(task)) {
	
						mSupplierDaoService.updateSuppliers(resp.getSuppliers());
	
					} else if (Constant.TASK_UPDATE_SUPPLIER.equals(task)) {
	
						mSupplierDaoService.updateSupplierStatus(resp.getStatus());
	
					} else if (Constant.TASK_GET_BILL.equals(task)) {
	
						mBillDaoService.updateBills(resp.getBills());
	
					} else if (Constant.TASK_UPDATE_BILL.equals(task)) {
	
						mBillDaoService.updateBillsStatus(resp.getStatus());
	
					} else if (Constant.TASK_GET_INVENTORY.equals(task)) {
	
						mInventoryDaoService.updateInventories(resp.getInventories());
	
					} else if (Constant.TASK_UPDATE_INVENTORY.equals(task)) {
	
						mInventoryDaoService.updateInventoryStatus(resp.getStatus());
	
					} else if (Constant.TASK_GET_MERCHANT.equals(task) ||
							   Constant.TASK_ROOT_GET_MERCHANT.equals(task)) {
	
						mMerchantDaoService.updateMerchants(resp.getMerchants());
	
					} else if (Constant.TASK_UPDATE_MERCHANT.equals(task)) {
	
						mMerchantDaoService.updateMerchantStatus(resp.getStatus());
	
					} else if (Constant.TASK_GET_MERCHANT_ACCESS.equals(task) ||
							   Constant.TASK_ROOT_GET_MERCHANT_ACCESS.equals(task)) {
	
						mMerchantAccessDaoService.updateMerchantAccesses(resp.getMerchantAccesses());
	
					} else if (Constant.TASK_UPDATE_MERCHANT_ACCESS.equals(task)) {
	
						mMerchantAccessDaoService.updateMerchantAccessStatus(resp.getStatus());
	
					} else if (Constant.TASK_UPDATE_LAST_SYNC.equals(task)) {
	
						mDevice = resp.getDevice();
						
						if (mContext instanceof LoginListener) {
							
							LoginListener mLoginListener = (LoginListener) mContext;
							mLoginListener.onMerchantsUpdated();
						}
					}
					
					executeNextTask();
				}
					
			} catch (Exception e) {
				
				e.printStackTrace();
				mAsyncListener.onSyncError();
			}
		}
	}

	public static String POST(String url, Object object) {

		InputStream inputStream = null;
		String result = "";

		try {

			HttpClient httpclient = new DefaultHttpClient();

			httpPost = new HttpPost(url);

			final OutputStream out = new ByteArrayOutputStream();
			final ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

			writer.writeValue(out, object);
			
			String json = out.toString();
			
			byte[] bytes = compress(json.getBytes());
			
			ByteArrayEntity be = new ByteArrayEntity(bytes);

			httpPost.setEntity(be);

			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			
			HttpResponse httpResponse = httpclient.execute(httpPost);
			
			inputStream = httpResponse.getEntity().getContent();
			
			if (inputStream != null)
				result = uncompress(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {

			Log.d("InputStream", e.getLocalizedMessage());
		}

		return result;
	}
	
	private static void abortHttpRequest() {
		
		if (httpPost != null) {
			httpPost.abort();
		}
	}
	
	public static byte[] compress(byte[] bytes) throws IOException {

		ByteArrayOutputStream rstBao = new ByteArrayOutputStream();
		GZIPOutputStream zos = new GZIPOutputStream(rstBao);
		
		zos.write(bytes);
		zos.close();

		byte[] output = rstBao.toByteArray();
		
		return output;
	}
	
	public static String uncompress(InputStream inputStream)
			throws IOException {
		
		String result = null;
		
		GZIPInputStream zi = null;
		
		try {
			
			zi = new GZIPInputStream(inputStream);
			result = IOUtils.toString(zi);
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			if (zi != null) {
				zi.close();
			}
		}
		
		return result;
	}
}
