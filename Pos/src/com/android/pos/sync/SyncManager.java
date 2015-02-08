package com.android.pos.sync;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.android.pos.Config;
import com.android.pos.Constant;
import com.android.pos.Installation;
import com.android.pos.model.CustomerBean;
import com.android.pos.model.DeviceBean;
import com.android.pos.model.DiscountBean;
import com.android.pos.model.EmployeeBean;
import com.android.pos.model.MerchantBean;
import com.android.pos.model.ProductBean;
import com.android.pos.model.ProductGroupBean;
import com.android.pos.model.SyncRequestBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.model.TransactionItemBean;
import com.android.pos.model.TransactionsBean;
import com.android.pos.model.UserBean;
import com.android.pos.service.CustomerDaoService;
import com.android.pos.service.DiscountDaoService;
import com.android.pos.service.EmployeeDaoService;
import com.android.pos.service.MerchantDaoService;
import com.android.pos.service.ProductDaoService;
import com.android.pos.service.ProductGroupDaoService;
import com.android.pos.service.TransactionItemDaoService;
import com.android.pos.service.TransactionsDaoService;
import com.android.pos.service.UserDaoService;
import com.android.pos.util.MerchantUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class SyncManager {

	private Context mContext;
	private DeviceBean mDevice;

	private ProductGroupDaoService mProductGroupDaoService;
	private DiscountDaoService mDiscountDaoService;
	private MerchantDaoService mMerchantDaoService;
	private EmployeeDaoService mEmployeeDaoService;
	private CustomerDaoService mCustomerDaoService;
	private ProductDaoService mProductDaoService;
	private UserDaoService mUserDaoService;
	private TransactionsDaoService mTransactionsDaoService;
	private TransactionItemDaoService mTransactionItemDaoService;

	private SyncListener mListener;

	private final int TOTAL_TASK = 20;

	public SyncManager(Context context) {

		this.mContext = context;
		mListener = (SyncListener) context;

		mProductGroupDaoService = new ProductGroupDaoService();
		mDiscountDaoService = new DiscountDaoService();
		mMerchantDaoService = new MerchantDaoService();
		mEmployeeDaoService = new EmployeeDaoService();
		mCustomerDaoService = new CustomerDaoService();
		mProductDaoService = new ProductDaoService();
		mUserDaoService = new UserDaoService();
		mTransactionsDaoService = new TransactionsDaoService();
		mTransactionItemDaoService = new TransactionItemDaoService();
	}

	public void sync() {

		mListener.setSyncProgress(0 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Cek waktu terakhir sync up data.");

		new HttpAsyncTask().execute(Constant.TASK_GET_LAST_SYNC);
	}

	private void getProductGroup() {

		new HttpAsyncTask().execute(Constant.TASK_GET_PRODUCT_GROUP);

		mListener.setSyncProgress(1 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Update data group produk dari server.");
	}

	private void updateProductGroup() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_PRODUCT_GROUP);

		mListener.setSyncProgress(2 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Upload data group produk ke server.");
	}

	private void getDiscount() {

		new HttpAsyncTask().execute(Constant.TASK_GET_DISCOUNT);

		mListener.setSyncProgress(3 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Update data diskon dari server.");
	}

	private void updateDiscount() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_DISCOUNT);

		mListener.setSyncProgress(4 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Upload data diskon ke server.");
	}

	private void getMerchant() {

		new HttpAsyncTask().execute(Constant.TASK_GET_MERCHANT);

		mListener.setSyncProgress(5 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Update data merchant dari server.");
	}

	private void updateMerchant() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_MERCHANT);

		mListener.setSyncProgress(6 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Upload data merchant ke server.");
	}

	private void getEmployee() {

		new HttpAsyncTask().execute(Constant.TASK_GET_EMPLOYEE);

		mListener.setSyncProgress(7 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Update data pegawai dari server.");
	}

	private void updateEmployee() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_EMPLOYEE);

		mListener.setSyncProgress(8 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Upload data pegawai ke server.");
	}

	private void getCustomer() {

		new HttpAsyncTask().execute(Constant.TASK_GET_CUSTOMER);

		mListener.setSyncProgress(9 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Update data pelanggan dari server.");
	}

	private void updateCustomer() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_CUSTOMER);

		mListener.setSyncProgress(10 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Upload data pelanggan ke server.");
	}

	private void getProduct() {

		new HttpAsyncTask().execute(Constant.TASK_GET_PRODUCT);

		mListener.setSyncProgress(11 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Update data produk dari server.");
	}

	private void updateProduct() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_PRODUCT);

		mListener.setSyncProgress(12 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Upload data produk ke server.");
	}

	private void getUser() {

		new HttpAsyncTask().execute(Constant.TASK_GET_USER);

		mListener.setSyncProgress(13 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Update data user dari server.");
	}

	private void updateUser() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_USER);

		mListener.setSyncProgress(14 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Upload data user ke server.");
	}

	private void getTransactions() {

		new HttpAsyncTask().execute(Constant.TASK_GET_TRANSACTIONS);

		mListener.setSyncProgress(15 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Update data transaksi dari server.");
	}

	private void updateTransactions() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_TRANSACTIONS);

		mListener.setSyncProgress(16 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Upload data transaksi ke server.");
	}

	private void getTransactionItem() {

		new HttpAsyncTask().execute(Constant.TASK_GET_TRANSACTION_ITEM);

		mListener.setSyncProgress(17 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Update data item transaksi dari server.");
	}

	private void updateTransactionItem() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_TRANSACTION_ITEM);

		mListener.setSyncProgress(18 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Upload data item transaksi ke server.");
	}

	private void updateLastSync() {

		new HttpAsyncTask().execute(Constant.TASK_UPDATE_LAST_SYNC);

		mListener.setSyncProgress(19 * 100 / TOTAL_TASK);
		mListener.setSyncMessage("Update waktu terakhir sync up data ke server.");
	}

	private void syncCompleted() {

		mListener.setSyncProgress(100);
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {

		String task;

		@Override
		protected String doInBackground(String... tasks) {

			task = tasks[0];

			Long merchantId = MerchantUtil.getMerchant().getId();
			String url = Constant.EMPTY_STRING;
			Object obj = null;

			if (Constant.TASK_GET_LAST_SYNC.equals(tasks[0])) {

				url = Config.SERVER_URL + "/getLastSyncJsonServlet";

				DeviceBean bean = new DeviceBean();
				bean.setMerchant_id(MerchantUtil.getMerchant().getId());
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

			} else if (Constant.TASK_GET_MERCHANT.equals(tasks[0])) {

				url = Config.SERVER_URL + "/merchantGetJsonServlet";

				SyncRequestBean request = new SyncRequestBean();

				request.setLast_sync_date(mDevice.getLast_sync_date());

				obj = request;

			} else if (Constant.TASK_UPDATE_MERCHANT.equals(tasks[0])) {

				url = Config.SERVER_URL + "/merchantUpdateJsonServlet";

				obj = mMerchantDaoService.getMerchantsForUpload();

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

			} else if (Constant.TASK_UPDATE_LAST_SYNC.equals(tasks[0])) {

				url = Config.SERVER_URL + "/updateLastSyncJsonServlet";

				DeviceBean bean = new DeviceBean();
				bean.setMerchant_id(MerchantUtil.getMerchant().getId());
				bean.setUuid(Installation.getInstallationId(mContext));
				obj = bean;
			}

			return POST(url, obj);
		}

		@Override
		protected void onPostExecute(String result) {

			try {

				ObjectMapper mapper = new ObjectMapper();

				if (Constant.TASK_GET_LAST_SYNC.equals(task)) {

					mDevice = mapper.readValue(result, DeviceBean.class);
					getProductGroup();

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
					getMerchant();

				} else if (Constant.TASK_GET_MERCHANT.equals(task)) {

					List<MerchantBean> merchants = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, MerchantBean.class));

					mMerchantDaoService.updateMerchants(merchants);
					updateMerchant();

				} else if (Constant.TASK_UPDATE_MERCHANT.equals(task)) {

					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class, SyncStatusBean.class));

					mMerchantDaoService.updateMerchantStatus(syncStatusBeans);
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
					updateLastSync();

				} else if (Constant.TASK_UPDATE_LAST_SYNC.equals(task)) {

					mDevice = mapper.readValue(result, DeviceBean.class);
					syncCompleted();
				}

			} catch (IOException e) {
				e.printStackTrace();
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
			final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

			String json = "";

			ow.writeValue(out, object);
			json = out.toString();
			
			json = compressString(json);

			result = json;

			StringEntity se = new StringEntity(json);

			httpPost.setEntity(se);

			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			HttpResponse httpResponse = httpclient.execute(httpPost);

			inputStream = httpResponse.getEntity().getContent();

			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {

			Log.d("InputStream", e.getLocalizedMessage());
		}

		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream) throws IOException {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		String line = "";
		StringBuffer result = new StringBuffer();

		while ((line = bufferedReader.readLine()) != null) {
			result.append(line);
		}

		inputStream.close();
		return result.toString();
	}

	public static String compressString(String srcTxt) throws IOException {

		ByteArrayOutputStream rstBao = new ByteArrayOutputStream();
		GZIPOutputStream zos = new GZIPOutputStream(rstBao);
		
		zos.write(srcTxt.getBytes());
		zos.close();

		byte[] bytes = rstBao.toByteArray();
		
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}
}
