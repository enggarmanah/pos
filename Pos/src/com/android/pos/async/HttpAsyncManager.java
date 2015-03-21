package com.android.pos.async;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
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
import com.android.pos.auth.LoginListener;
import com.android.pos.dao.BillsDaoService;
import com.android.pos.dao.CustomerDaoService;
import com.android.pos.dao.DiscountDaoService;
import com.android.pos.dao.EmployeeDaoService;
import com.android.pos.dao.InventoryDaoService;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantAccessDaoService;
import com.android.pos.dao.MerchantDaoService;
import com.android.pos.dao.ProductDaoService;
import com.android.pos.dao.ProductGroupDaoService;
import com.android.pos.dao.SupplierDaoService;
import com.android.pos.dao.TransactionItemDaoService;
import com.android.pos.dao.TransactionsDaoService;
import com.android.pos.dao.UserAccessDaoService;
import com.android.pos.dao.UserDaoService;
import com.android.pos.model.BillsBean;
import com.android.pos.model.CustomerBean;
import com.android.pos.model.DeviceBean;
import com.android.pos.model.DiscountBean;
import com.android.pos.model.EmployeeBean;
import com.android.pos.model.InventoryBean;
import com.android.pos.model.MerchantAccessBean;
import com.android.pos.model.MerchantBean;
import com.android.pos.model.ProductBean;
import com.android.pos.model.ProductGroupBean;
import com.android.pos.model.SupplierBean;
import com.android.pos.model.SyncRequestBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.model.TransactionItemBean;
import com.android.pos.model.TransactionsBean;
import com.android.pos.model.UserAccessBean;
import com.android.pos.model.UserBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.MerchantUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class HttpAsyncManager {

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

	private HttpAsyncListener mAsyncListener;

	private final int TOTAL_TASK = 30;

	public HttpAsyncManager(Context context) {

		this.mContext = context;
		
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
	}
	
	public void validateMerchant(String loginId, String password) {
		
		mLoginId = loginId;
		mPassword = password;
		
		mAsyncListener.setSyncProgress(0);
		mAsyncListener.setSyncMessage("Melaksanakan validasi ke server.");
		
		final HttpAsyncTask task = new HttpAsyncTask();
		task.execute(Constant.TASK_VALIDATE_MERCHANT);
			
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (task.getStatus() == AsyncTask.Status.RUNNING) {
					task.cancel(true);
					mAsyncListener.onTimeOut();
				}
			}
		}, Constant.TIMEOUT);
	}
	
	public void syncMerchants() {
		
		mAsyncListener.setSyncProgress(0);
		mAsyncListener.setSyncMessage("Melaksanakan koneksi ke server ...");

		final HttpAsyncTask task = new HttpAsyncTask();
		task.execute(Constant.TASK_ROOT_GET_LAST_SYNC);
			
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (task.getStatus() == AsyncTask.Status.RUNNING) {
					task.cancel(true);
					mAsyncListener.onTimeOut();
				}
			}
		}, Constant.TIMEOUT);
	}
	
	private void getMerchantsBasedOnLastSync() {
		
		mAsyncListener.setSyncProgress(1 / 100 * 3);
		mAsyncListener.setSyncMessage("Melaksanakan download data merchant.");

		new HttpAsyncTask().execute(Constant.TASK_ROOT_GET_MERCHANT);
	}
	
	private void updateMerchants() {
		
		mAsyncListener.setSyncProgress(2 / 100 * 3);
		mAsyncListener.setSyncMessage("Melaksanakan upload data merchant.");

		new HttpAsyncTask().execute(Constant.TASK_ROOT_UPDATE_MERCHANT);
	}
	
	public void sync() {
		
		mAsyncListener.setSyncProgress(0 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Melaksanakan koneksi ke server ...");

		final HttpAsyncTask task = new HttpAsyncTask();
		task.execute(Constant.TASK_GET_LAST_SYNC);
			
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (task.getStatus() == AsyncTask.Status.RUNNING) {
					task.cancel(true);
					mAsyncListener.onTimeOut();
				}
			}
		}, Constant.TIMEOUT);
	}

	private void getProductGroup() {

		new HttpAsyncTask().execute(Constant.TASK_GET_PRODUCT_GROUP);

		mAsyncListener.setSyncProgress(1 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Download data group produk dari server.");
	}

	private void updateProductGroup() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_PRODUCT_GROUP);

		mAsyncListener.setSyncProgress(2 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Upload data group produk ke server.");
	}

	private void getDiscount() {

		new HttpAsyncTask().execute(Constant.TASK_GET_DISCOUNT);

		mAsyncListener.setSyncProgress(3 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Download data diskon dari server.");
	}

	private void updateDiscount() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_DISCOUNT);

		mAsyncListener.setSyncProgress(4 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Upload data diskon ke server.");
	}

	private void getEmployee() {

		new HttpAsyncTask().execute(Constant.TASK_GET_EMPLOYEE);

		mAsyncListener.setSyncProgress(5 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Download data pegawai dari server.");
	}

	private void updateEmployee() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_EMPLOYEE);

		mAsyncListener.setSyncProgress(6 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Upload data pegawai ke server.");
	}

	private void getCustomer() {

		new HttpAsyncTask().execute(Constant.TASK_GET_CUSTOMER);

		mAsyncListener.setSyncProgress(7 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Download data pelanggan dari server.");
	}

	private void updateCustomer() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_CUSTOMER);

		mAsyncListener.setSyncProgress(8 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Upload data pelanggan ke server.");
	}

	private void getProduct() {

		new HttpAsyncTask().execute(Constant.TASK_GET_PRODUCT);

		mAsyncListener.setSyncProgress(9 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Download data produk dari server.");
	}

	private void updateProduct() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_PRODUCT);

		mAsyncListener.setSyncProgress(10 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Upload data produk ke server.");
	}

	private void getUser() {

		new HttpAsyncTask().execute(Constant.TASK_GET_USER);

		mAsyncListener.setSyncProgress(11 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Download data user dari server.");
	}

	private void updateUser() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_USER);

		mAsyncListener.setSyncProgress(12 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Upload data user ke server.");
	}
	
	private void getUserAccess() {

		new HttpAsyncTask().execute(Constant.TASK_GET_USER_ACCESS);

		mAsyncListener.setSyncProgress(13 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Download data akses user dari server.");
	}

	private void updateUserAccess() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_USER_ACCESS);

		mAsyncListener.setSyncProgress(14 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Upload data akses user ke server.");
	}

	private void getTransactions() {

		new HttpAsyncTask().execute(Constant.TASK_GET_TRANSACTIONS);

		mAsyncListener.setSyncProgress(15 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Download data transaksi dari server.");
	}

	private void updateTransactions() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_TRANSACTIONS);

		mAsyncListener.setSyncProgress(16 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Upload data transaksi ke server.");
	}

	private void getTransactionItem() {

		new HttpAsyncTask().execute(Constant.TASK_GET_TRANSACTION_ITEM);

		mAsyncListener.setSyncProgress(17 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Download data item transaksi dari server.");
	}

	private void updateTransactionItem() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_TRANSACTION_ITEM);

		mAsyncListener.setSyncProgress(18 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Upload data item transaksi ke server.");
	}
	
	private void getSupplier() {

		new HttpAsyncTask().execute(Constant.TASK_GET_SUPPLIER);

		mAsyncListener.setSyncProgress(19 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Download data supplier dari server.");
	}

	private void updateSupplier() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_SUPPLIER);

		mAsyncListener.setSyncProgress(20 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Upload data supplier ke server.");
	}
	
	private void getBills() {

		new HttpAsyncTask().execute(Constant.TASK_GET_BILL);

		mAsyncListener.setSyncProgress(21 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Download data pengeluaran dari server.");
	}

	private void updateBills() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_BILL);

		mAsyncListener.setSyncProgress(22 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Upload data pengeluaran ke server.");
	}
	
	private void getInventory() {

		new HttpAsyncTask().execute(Constant.TASK_GET_INVENTORY);

		mAsyncListener.setSyncProgress(23 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Download data inventori dari server.");
	}

	private void updateInventory() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_INVENTORY);

		mAsyncListener.setSyncProgress(24 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Upload data inventori ke server.");
	}
	
	private void getMerchant() {

		new HttpAsyncTask().execute(Constant.TASK_GET_MERCHANT);

		mAsyncListener.setSyncProgress(25 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Download data merchant dari server.");
	}

	private void updateMerchant() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_MERCHANT);

		mAsyncListener.setSyncProgress(26 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Upload data merchant ke server.");
	}
	
	private void getMerchantAccess() {

		new HttpAsyncTask().execute(Constant.TASK_GET_MERCHANT_ACCESS);

		mAsyncListener.setSyncProgress(27 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Download data akses merchant dari server.");
	}

	private void updateMerchantAccess() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_MERCHANT_ACCESS);

		mAsyncListener.setSyncProgress(28 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Upload data akses merchant ke server.");
	}

	private void updateLastSync() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_LAST_SYNC);

		mAsyncListener.setSyncProgress(29 * 100 / TOTAL_TASK);
		mAsyncListener.setSyncMessage("Update waktu terakhir sync up data ke server.");
	}

	private void syncCompleted() {

		mAsyncListener.setSyncProgress(100);
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {

		String task;

		@Override
		protected String doInBackground(String... tasks) {

			task = tasks[0];
			
			Merchant merchant = MerchantUtil.getMerchant();
			Long merchantId = null;
			
			if (merchant != null) {
			
				merchantId = merchant.getId();
			
			} else {
				
				// when the login id is root
				merchantId = Long.valueOf(-1);
			}
			
			String url = Constant.EMPTY_STRING;
			Object obj = null;

			if (Constant.TASK_VALIDATE_MERCHANT.equals(tasks[0])) {
				
				mAsyncListener.setSyncProgress(0);
				mAsyncListener.setSyncMessage("Melaksanakan validasi ke server.");
				
				url = Config.SERVER_URL + "/merchantValidateJsonServlet";

				MerchantBean bean = new MerchantBean();
				
				bean.setLogin_id(mLoginId);
				bean.setPassword(mPassword);
				
				obj = bean;

			} else if (Constant.TASK_ROOT_GET_MERCHANT.equals(tasks[0])) {

				url = Config.SERVER_URL + "/merchantGetAllJsonServlet";

				SyncRequestBean request = new SyncRequestBean();
				
				request.setLast_sync_date(mDevice.getLast_sync_date());
				
				obj = request;

			} else if (Constant.TASK_GET_LAST_SYNC.equals(tasks[0])) {

				url = Config.SERVER_URL + "/getLastSyncJsonServlet";

				DeviceBean bean = new DeviceBean();
				bean.setMerchant_id(merchantId);
				bean.setUuid(Installation.getInstallationId(mContext));
				obj = bean;

			} else if (Constant.TASK_ROOT_GET_LAST_SYNC.equals(tasks[0])) {

				url = Config.SERVER_URL + "/getLastSyncJsonServlet";
				
				DeviceBean bean = new DeviceBean();
				bean.setMerchant_id(merchantId);
				bean.setUuid(Installation.getInstallationId(mContext));
				obj = bean;

			} else if (Constant.TASK_GET_PRODUCT_GROUP.equals(tasks[0])) {

				url = Config.SERVER_URL + "/productGroupGetJsonServlet";

				SyncRequestBean request = new SyncRequestBean();

				request.setMerchant_id(merchantId);
				request.setLast_sync_date(mDevice.getLast_sync_date());

				obj = request;

			} else if (Constant.TASK_UPDATE_PRODUCT_GROUP.equals(tasks[0])) {

				url = Config.SERVER_URL + "/productGroupUpdateJsonServlet";

				obj = mProductGroupDaoService.getProductGroupsForUpload();

			} else if (Constant.TASK_GET_DISCOUNT.equals(tasks[0])) {

				url = Config.SERVER_URL + "/discountGetJsonServlet";

				SyncRequestBean request = new SyncRequestBean();

				request.setMerchant_id(merchantId);
				request.setLast_sync_date(mDevice.getLast_sync_date());

				obj = request;

			} else if (Constant.TASK_UPDATE_DISCOUNT.equals(tasks[0])) {

				url = Config.SERVER_URL + "/discountUpdateJsonServlet";

				obj = mDiscountDaoService.getDiscountsForUpload();

			} else if (Constant.TASK_GET_EMPLOYEE.equals(tasks[0])) {

				url = Config.SERVER_URL + "/employeeGetJsonServlet";

				SyncRequestBean request = new SyncRequestBean();

				request.setMerchant_id(merchantId);
				request.setLast_sync_date(mDevice.getLast_sync_date());

				obj = request;

			} else if (Constant.TASK_UPDATE_EMPLOYEE.equals(tasks[0])) {

				url = Config.SERVER_URL + "/employeeUpdateJsonServlet";

				obj = mEmployeeDaoService.getEmployeesForUpload();

			} else if (Constant.TASK_GET_CUSTOMER.equals(tasks[0])) {

				url = Config.SERVER_URL + "/customerGetJsonServlet";

				SyncRequestBean request = new SyncRequestBean();

				request.setMerchant_id(merchantId);
				request.setLast_sync_date(mDevice.getLast_sync_date());

				obj = request;

			} else if (Constant.TASK_UPDATE_CUSTOMER.equals(tasks[0])) {

				url = Config.SERVER_URL + "/customerUpdateJsonServlet";

				obj = mCustomerDaoService.getCustomersForUpload();

			} else if (Constant.TASK_GET_PRODUCT.equals(tasks[0])) {

				url = Config.SERVER_URL + "/productGetJsonServlet";

				SyncRequestBean request = new SyncRequestBean();

				request.setMerchant_id(merchantId);
				request.setLast_sync_date(mDevice.getLast_sync_date());

				obj = request;

			} else if (Constant.TASK_UPDATE_PRODUCT.equals(tasks[0])) {

				url = Config.SERVER_URL + "/productUpdateJsonServlet";

				obj = mProductDaoService.getProductsForUpload();

			} else if (Constant.TASK_GET_USER.equals(tasks[0])) {

				url = Config.SERVER_URL + "/userGetJsonServlet";

				SyncRequestBean request = new SyncRequestBean();

				request.setMerchant_id(merchantId);
				request.setLast_sync_date(mDevice.getLast_sync_date());

				obj = request;

			} else if (Constant.TASK_UPDATE_USER.equals(tasks[0])) {

				url = Config.SERVER_URL + "/userUpdateJsonServlet";

				obj = mUserDaoService.getUsersForUpload();

			} else if (Constant.TASK_GET_USER_ACCESS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/userAccessGetJsonServlet";

				SyncRequestBean request = new SyncRequestBean();

				request.setMerchant_id(merchantId);
				request.setLast_sync_date(mDevice.getLast_sync_date());

				obj = request;

			} else if (Constant.TASK_UPDATE_USER_ACCESS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/userAccessUpdateJsonServlet";

				obj = mUserAccessDaoService.getUserAccessesForUpload();

			} else if (Constant.TASK_GET_TRANSACTIONS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/transactionsGetJsonServlet";

				SyncRequestBean request = new SyncRequestBean();

				request.setMerchant_id(merchantId);
				request.setLast_sync_date(mDevice.getLast_sync_date());

				obj = request;

			} else if (Constant.TASK_UPDATE_TRANSACTIONS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/transactionsUpdateJsonServlet";

				obj = mTransactionsDaoService.getTransactionsForUpload();

			} else if (Constant.TASK_GET_TRANSACTION_ITEM.equals(tasks[0])) {

				url = Config.SERVER_URL + "/transactionItemGetJsonServlet";

				SyncRequestBean request = new SyncRequestBean();

				request.setMerchant_id(merchantId);
				request.setLast_sync_date(mDevice.getLast_sync_date());

				obj = request;

			} else if (Constant.TASK_UPDATE_TRANSACTION_ITEM.equals(tasks[0])) {

				url = Config.SERVER_URL + "/transactionItemUpdateJsonServlet";

				obj = mTransactionItemDaoService.getTransactionItemsForUpload();

			} else if (Constant.TASK_GET_SUPPLIER.equals(tasks[0])) {

				url = Config.SERVER_URL + "/supplierGetJsonServlet";

				SyncRequestBean request = new SyncRequestBean();

				request.setMerchant_id(merchantId);
				request.setLast_sync_date(mDevice.getLast_sync_date());

				obj = request;

			} else if (Constant.TASK_UPDATE_SUPPLIER.equals(tasks[0])) {

				url = Config.SERVER_URL + "/supplierUpdateJsonServlet";

				obj = mSupplierDaoService.getSuppliersForUpload();

			} else if (Constant.TASK_GET_BILL.equals(tasks[0])) {

				url = Config.SERVER_URL + "/billGetJsonServlet";

				SyncRequestBean request = new SyncRequestBean();

				request.setMerchant_id(merchantId);
				request.setLast_sync_date(mDevice.getLast_sync_date());

				obj = request;

			} else if (Constant.TASK_UPDATE_BILL.equals(tasks[0])) {

				url = Config.SERVER_URL + "/billUpdateJsonServlet";

				obj = mBillDaoService.getBillsForUpload();

			} else if (Constant.TASK_GET_INVENTORY.equals(tasks[0])) {

				url = Config.SERVER_URL + "/inventoryGetJsonServlet";

				SyncRequestBean request = new SyncRequestBean();

				request.setMerchant_id(merchantId);
				request.setLast_sync_date(mDevice.getLast_sync_date());

				obj = request;

			} else if (Constant.TASK_UPDATE_INVENTORY.equals(tasks[0])) {

				url = Config.SERVER_URL + "/inventoryUpdateJsonServlet";

				obj = mInventoryDaoService.getInventoriesForUpload();

			} else if (Constant.TASK_GET_MERCHANT.equals(tasks[0])) {

				url = Config.SERVER_URL + "/merchantGetJsonServlet";

				SyncRequestBean request = new SyncRequestBean();
				
				request.setMerchant_id(merchantId);
				request.setLast_sync_date(mDevice.getLast_sync_date());

				obj = request;

			} else if (Constant.TASK_UPDATE_MERCHANT.equals(tasks[0]) ||
					Constant.TASK_ROOT_UPDATE_MERCHANT.equals(tasks[0])) {

				url = Config.SERVER_URL + "/merchantUpdateJsonServlet";

				obj = mMerchantDaoService.getMerchantsForUpload();

			} else if (Constant.TASK_GET_MERCHANT_ACCESS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/merchantAccessGetJsonServlet";

				SyncRequestBean request = new SyncRequestBean();

				request.setMerchant_id(merchantId);
				request.setLast_sync_date(mDevice.getLast_sync_date());

				obj = request;

			} else if (Constant.TASK_UPDATE_MERCHANT_ACCESS.equals(tasks[0])) {

				url = Config.SERVER_URL + "/merchantAccessUpdateJsonServlet";

				obj = mMerchantAccessDaoService.getMerchantAccessesForUpload();

			} else if (Constant.TASK_UPDATE_LAST_SYNC.equals(tasks[0])) {

				url = Config.SERVER_URL + "/updateLastSyncJsonServlet";
				
				obj = mDevice;
			}

			return POST(url, obj);
		}

		@Override
		protected void onPostExecute(String result) {

			try {

				ObjectMapper mapper = new ObjectMapper();

				if (Constant.TASK_VALIDATE_MERCHANT.equals(task)) {

					MerchantBean bean = mapper.readValue(result, MerchantBean.class);
					
					Merchant merchant = null;
					
					if (bean != null) {
						
						merchant = new Merchant();
						BeanUtil.updateBean(merchant, bean);
					}
					
					LoginListener mLoginListener = (LoginListener) mContext;
					mLoginListener.onMerchantValidated(merchant);
					
				} else if (Constant.TASK_GET_LAST_SYNC.equals(task)) {

					mDevice = mapper.readValue(result, DeviceBean.class);
					getProductGroup();

				} else if (Constant.TASK_ROOT_GET_LAST_SYNC.equals(task)) {

					mDevice = mapper.readValue(result, DeviceBean.class);
					getMerchantsBasedOnLastSync();

				} else if (Constant.TASK_GET_PRODUCT_GROUP.equals(task)) {

					List<ProductGroupBean> productGroups = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, ProductGroupBean.class));

					mProductGroupDaoService.updateProductGroups(productGroups);
					updateProductGroup();

				} else if (Constant.TASK_UPDATE_PRODUCT_GROUP.equals(task)) {

					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, SyncStatusBean.class));

					mProductGroupDaoService.updateProductGroupStatus(syncStatusBeans);
					getDiscount();

				} else if (Constant.TASK_GET_DISCOUNT.equals(task)) {

					List<DiscountBean> discounts = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, DiscountBean.class));

					mDiscountDaoService.updateDiscounts(discounts);
					updateDiscount();

				} else if (Constant.TASK_UPDATE_DISCOUNT.equals(task)) {

					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, SyncStatusBean.class));

					mDiscountDaoService.updateDiscountStatus(syncStatusBeans);
					getEmployee();

				} else if (Constant.TASK_GET_EMPLOYEE.equals(task)) {

					List<EmployeeBean> employees = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, EmployeeBean.class));

					mEmployeeDaoService.updateEmployees(employees);
					updateEmployee();

				} else if (Constant.TASK_UPDATE_EMPLOYEE.equals(task)) {

					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, SyncStatusBean.class));

					mEmployeeDaoService.updateEmployeeStatus(syncStatusBeans);
					getCustomer();

				} else if (Constant.TASK_GET_CUSTOMER.equals(task)) {

					List<CustomerBean> customers = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, CustomerBean.class));

					mCustomerDaoService.updateCustomers(customers);
					updateCustomer();

				} else if (Constant.TASK_UPDATE_CUSTOMER.equals(task)) {

					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, SyncStatusBean.class));

					mCustomerDaoService.updateCustomerStatus(syncStatusBeans);
					getProduct();

				} else if (Constant.TASK_GET_PRODUCT.equals(task)) {

					List<ProductBean> products = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, ProductBean.class));

					mProductDaoService.updateProducts(products);
					updateProduct();

				} else if (Constant.TASK_UPDATE_PRODUCT.equals(task)) {

					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, SyncStatusBean.class));

					mProductDaoService.updateProductStatus(syncStatusBeans);
					getUser();

				} else if (Constant.TASK_GET_USER.equals(task)) {

					List<UserBean> users = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, UserBean.class));

					mUserDaoService.updateUsers(users);
					updateUser();

				} else if (Constant.TASK_UPDATE_USER.equals(task)) {

					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, SyncStatusBean.class));

					mUserDaoService.updateUserStatus(syncStatusBeans);
					getUserAccess();

				} else if (Constant.TASK_GET_USER_ACCESS.equals(task)) {

					List<UserAccessBean> userAccesses = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, UserAccessBean.class));

					mUserAccessDaoService.updateUserAccesses(userAccesses);
					updateUserAccess();

				} else if (Constant.TASK_UPDATE_USER_ACCESS.equals(task)) {

					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, SyncStatusBean.class));

					mUserAccessDaoService.updateUserAccessStatus(syncStatusBeans);
					getTransactions();

				} else if (Constant.TASK_GET_TRANSACTIONS.equals(task)) {

					List<TransactionsBean> transactions = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, TransactionsBean.class));

					mTransactionsDaoService.updateTransactions(transactions);
					updateTransactions();

				} else if (Constant.TASK_UPDATE_TRANSACTIONS.equals(task)) {

					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, SyncStatusBean.class));

					mTransactionsDaoService.updateTransactionsStatus(syncStatusBeans);
					getTransactionItem();

				} else if (Constant.TASK_GET_TRANSACTION_ITEM.equals(task)) {

					List<TransactionItemBean> transactionItems = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, TransactionItemBean.class));

					mTransactionItemDaoService.updateTransactionItems(transactionItems);
					updateTransactionItem();

				} else if (Constant.TASK_UPDATE_TRANSACTION_ITEM.equals(task)) {

					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, SyncStatusBean.class));

					mTransactionItemDaoService.updateTransactionItemStatus(syncStatusBeans);
					getSupplier();

				} else if (Constant.TASK_GET_SUPPLIER.equals(task)) {

					List<SupplierBean> suppliers = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, SupplierBean.class));

					mSupplierDaoService.updateSuppliers(suppliers);
					updateSupplier();

				} else if (Constant.TASK_UPDATE_SUPPLIER.equals(task)) {

					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, SyncStatusBean.class));

					mSupplierDaoService.updateSupplierStatus(syncStatusBeans);
					getBills();

				} else if (Constant.TASK_GET_BILL.equals(task)) {

					List<BillsBean> bills = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, BillsBean.class));

					mBillDaoService.updateBills(bills);
					updateBills();

				} else if (Constant.TASK_UPDATE_BILL.equals(task)) {

					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, SyncStatusBean.class));

					mBillDaoService.updateBillsStatus(syncStatusBeans);
					getInventory();

				} else if (Constant.TASK_GET_INVENTORY.equals(task)) {

					List<InventoryBean> inventories = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, InventoryBean.class));

					mInventoryDaoService.updateInventories(inventories);
					updateInventory();

				} else if (Constant.TASK_UPDATE_INVENTORY.equals(task)) {

					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, SyncStatusBean.class));

					mInventoryDaoService.updateInventoryStatus(syncStatusBeans);
					getMerchant();

				} else if (Constant.TASK_ROOT_GET_MERCHANT.equals(task)) {

					List<MerchantBean> merchants = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, MerchantBean.class));

					mMerchantDaoService.updateMerchants(merchants);
					updateMerchants();

				} else if (Constant.TASK_ROOT_UPDATE_MERCHANT.equals(task)) {

					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, SyncStatusBean.class));

					mMerchantDaoService.updateMerchantStatus(syncStatusBeans);
					getMerchantAccess();
					
					/*if (mContext instanceof LoginListener) {
						
						LoginListener mLoginListener = (LoginListener) mContext;
						mLoginListener.onMerchantsUpdated();
					}*/

				} else if (Constant.TASK_GET_MERCHANT.equals(task)) {

					List<MerchantBean> merchants = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, MerchantBean.class));

					mMerchantDaoService.updateMerchants(merchants);
					updateMerchant();

				} else if (Constant.TASK_UPDATE_MERCHANT.equals(task)) {

					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, SyncStatusBean.class));

					mMerchantDaoService.updateMerchantStatus(syncStatusBeans);
					getMerchantAccess();

				} else if (Constant.TASK_GET_MERCHANT_ACCESS.equals(task)) {

					List<MerchantAccessBean> merchantAccesses = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, MerchantAccessBean.class));

					mMerchantAccessDaoService.updateMerchantAccesses(merchantAccesses);
					updateMerchantAccess();

				} else if (Constant.TASK_UPDATE_MERCHANT_ACCESS.equals(task)) {

					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, SyncStatusBean.class));

					mMerchantAccessDaoService.updateMerchantAccessStatus(syncStatusBeans);
					updateLastSync();

				} else if (Constant.TASK_UPDATE_LAST_SYNC.equals(task)) {

					mDevice = mapper.readValue(result, DeviceBean.class);
					syncCompleted();
					
					if (mContext instanceof LoginListener) {
						
						LoginListener mLoginListener = (LoginListener) mContext;
						mLoginListener.onMerchantsUpdated();
					}
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

			HttpPost httpPost = new HttpPost(url);

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
