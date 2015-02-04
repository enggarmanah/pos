package com.android.pos.sync;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.pos.Config;
import com.android.pos.Constant;
import com.android.pos.Installation;
import com.android.pos.model.DeviceBean;
import com.android.pos.model.DiscountBean;
import com.android.pos.model.ProductGroupBean;
import com.android.pos.model.SyncRequestBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.MerchantUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class SyncManager {
	
	private Context context;
	private DeviceBean device;
	
	private ProductGroupDataProvider productGroupDataProvider;
	private DiscountDataProvider discountDataProvider;
	
	private SyncListener listener;
	
	private final int totalTask = 6;
	
	public SyncManager(Context context) {
		
		this.context = context;
		listener = (SyncListener) context;
		
		productGroupDataProvider = new ProductGroupDataProvider();
		discountDataProvider = new DiscountDataProvider();
	}
	
	public void sync() {
		
		new HttpAsyncTask().execute(Constant.TASK_GET_LAST_SYNC);
		
		listener.setSyncProgress(0 * 100 / totalTask);
		listener.setSyncMessage("Cek waktu terakhir melaksanan sync up data.");
	}
	
	private void getProductGroup() {
		
		new HttpAsyncTask().execute(Constant.TASK_GET_PRODUCT_GROUP);
		
		listener.setSyncProgress(1 * 100 / totalTask);
		listener.setSyncMessage("Update data group produk dari server.");
	}
	
	private void updateProductGroup() {
		
		new HttpAsyncTask().execute(Constant.TASK_UPDATE_PRODUCT_GROUP);
		
		listener.setSyncProgress(2 * 100 / totalTask);
		listener.setSyncMessage("Upload data group produk ke server.");
	}
	
	private void getDiscount() {
		
		new HttpAsyncTask().execute(Constant.TASK_GET_DISCOUNT);
		
		listener.setSyncProgress(3 * 100 / totalTask);
		listener.setSyncMessage("Update data diskon dari server.");
	}
	
	private void updateDiscount() {
		
		new HttpAsyncTask().execute(Constant.TASK_UPDATE_DISCOUNT);
		
		listener.setSyncProgress(4 * 100 / totalTask);
		listener.setSyncMessage("Upload data diskon ke server.");
	}
	
	private void updateLastSync() {
		
		new HttpAsyncTask().execute(Constant.TASK_UPDATE_LAST_SYNC);
		
		listener.setSyncProgress(5 * 100 / totalTask);
		listener.setSyncMessage("Update waktu terakhir sync up data ke server.");
	}
	
	private void syncCompleted() {
		
		listener.setSyncProgress(100);
	}
	
	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		
		String task;
		
		@Override
		protected String doInBackground(String... tasks) {
			
			task = tasks[0];
			
			String url = Constant.EMPTY_STRING;
			Object obj = null;
			
			if (Constant.TASK_GET_LAST_SYNC.equals(tasks[0])) {
				
				url = Config.SERVER_URL + "/getLastSyncJsonServlet";
				
				DeviceBean bean = new DeviceBean();
				bean.setMerchant_id(MerchantUtil.getMerchant().getId());
				bean.setUuid(Installation.getInstallationId(context));
				obj = bean;
			
			} else if (Constant.TASK_GET_PRODUCT_GROUP.equals(tasks[0])) {
				
				url = Config.SERVER_URL + "/productGroupGetJsonServlet";
				
				SyncRequestBean request = new SyncRequestBean();
				request.setLastSyncDate(device.getLast_sync_date());
				obj = request;
				
			} else if (Constant.TASK_UPDATE_PRODUCT_GROUP.equals(tasks[0])) {
				
				url = Config.SERVER_URL + "/productGroupUpdateJsonServlet";
				
				obj = productGroupDataProvider.getProductGroupsForUpload();
			
			} else if (Constant.TASK_GET_DISCOUNT.equals(tasks[0])) {
				
				url = Config.SERVER_URL + "/discountGetJsonServlet";
				
				SyncRequestBean request = new SyncRequestBean();
				request.setLastSyncDate(device.getLast_sync_date());
				obj = request;
				
			} else if (Constant.TASK_UPDATE_DISCOUNT.equals(tasks[0])) {
				
				url = Config.SERVER_URL + "/discountUpdateJsonServlet";
				
				obj = discountDataProvider.getDiscountsForUpload();
			
			} else if (Constant.TASK_UPDATE_LAST_SYNC.equals(tasks[0])) {
				
				url = Config.SERVER_URL + "/updateLastSyncJsonServlet";
				
				DeviceBean bean = new DeviceBean();
				bean.setMerchant_id(MerchantUtil.getMerchant().getId());
				bean.setUuid(Installation.getInstallationId(context));
				obj = bean;
			}
			
			return POST(url, obj);
		}

		@Override
		protected void onPostExecute(String result) {
			
			try {
				
				ObjectMapper mapper = new ObjectMapper();
				
				if (Constant.TASK_GET_LAST_SYNC.equals(task)) {
					
					device = mapper.readValue(result, DeviceBean.class);
					getProductGroup();
				
				} else if (Constant.TASK_GET_PRODUCT_GROUP.equals(task)) {
					
					List<ProductGroupBean> productGroups = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class,  
							ProductGroupBean.class));
					
					productGroupDataProvider.updateProductGroups(productGroups);
					updateProductGroup();
				
				} else if (Constant.TASK_UPDATE_PRODUCT_GROUP.equals(task)) {
					
					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class,  
							SyncStatusBean.class));
					
					productGroupDataProvider.updateProductGroupStatus(syncStatusBeans);
					getDiscount();
				
				} else if (Constant.TASK_GET_DISCOUNT.equals(task)) {
					
					List<DiscountBean> discounts = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class,  
							DiscountBean.class));
					
					discountDataProvider.updateDiscounts(discounts);
					updateDiscount();
				
				} else if (Constant.TASK_UPDATE_DISCOUNT.equals(task)) {
					
					List<SyncStatusBean> syncStatusBeans = mapper.readValue(result,
							TypeFactory.defaultInstance().constructCollectionType(List.class,  
							SyncStatusBean.class));
					
					discountDataProvider.updateDiscountStatus(syncStatusBeans);
					updateLastSync();
				
				} else if (Constant.TASK_UPDATE_LAST_SYNC.equals(task)) {
					
					device = mapper.readValue(result, DeviceBean.class);
					
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
}
