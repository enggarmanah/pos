package com.android.pos;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.pos.model.DeviceBean;
import com.android.pos.model.RequestBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class SyncManager {
	
	private Context context;
	private DataManager dataMgr;
	private DeviceBean device;
	
	public SyncManager(Context context) {
		
		this.context = context;
		dataMgr = new DataManager();
	}
	
	public void sync() {
		
		new HttpAsyncTask().execute(Constant.TASK_GET_LAST_SYNC);
	}
	
	private void getProductGroup() {
		
		new HttpAsyncTask().execute(Constant.TASK_GET_PRODUCT_GROUP);
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
				
				RequestBean request = new RequestBean();
				request.setLastSyncDate(device.getLast_sync_date());
				obj = request;
			}
			
			return POST(url, obj);
		}

		@Override
		protected void onPostExecute(String result) {
			
			try {
				
				if (Constant.TASK_GET_LAST_SYNC.equals(task)) {
					
					ObjectMapper mapper = new ObjectMapper();
					device = mapper.readValue(result, DeviceBean.class);
					
					getProductGroup();
				
				} else if (Constant.TASK_GET_PRODUCT_GROUP.equals(task)) {
					
					Toast.makeText(context, "Data Sent!", Toast.LENGTH_LONG).show();

					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
					alertDialogBuilder.setTitle("Alert");

					alertDialogBuilder.setMessage("Result : " + result);

					AlertDialog alertDialog = alertDialogBuilder.create();

					alertDialog.show();
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

		// 11. return result
		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream) throws IOException {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;
	}
}
