package com.android.pos.reference;

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

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.pos.Constant;
import com.android.pos.DataManager;
import com.android.pos.DbHelper;
import com.android.pos.R;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.model.ProductGroupBean;
import com.android.pos.reference.customer.CustomerMgtActivity;
import com.android.pos.reference.discount.DiscountMgtActivity;
import com.android.pos.reference.employee.EmployeeMgtActivity;
import com.android.pos.reference.merchant.MerchantMgtActivity;
import com.android.pos.reference.product.ProductMgtActivity;
import com.android.pos.reference.productGrp.ProductGrpMgtActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class MainMenuActivity extends BaseActivity {

	final Context context = this;
	
	private DataManager dataMgr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	
		setContentView(R.layout.ref_main_menu_activity);
		
		DbHelper.initDb(this);
		
		dataMgr = new DataManager();
		
		initDrawerMenu();
		
		setTitle(getString(R.string.menu_data_management));
		
		mDrawerList.setItemChecked(Constant.MENU_DATA_MANAGEMENT_POSITION, true);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.data_mgt_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_item_sync:
			
			new HttpAsyncTask().execute("http://192.168.0.104:8888/productGroupServlet");

			return true;

		default:
			
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void goToModuleMerchant(View view) {
		
		Intent intent = new Intent(this, MerchantMgtActivity.class);
		startActivity(intent);
	}
	
	public void goToModuleProductGroup(View view) {
		
		Intent intent = new Intent(this, ProductGrpMgtActivity.class);
		startActivity(intent);
	}
	
	public void goToModuleProduct(View view) {
		
		Intent intent = new Intent(this, ProductMgtActivity.class);
		startActivity(intent);
	}
	
	public void goToModuleEmployee(View view) {
		
		Intent intent = new Intent(this, EmployeeMgtActivity.class);
		startActivity(intent);
	}
	
	public void goToModuleCustomer(View view) {
		
		Intent intent = new Intent(this, CustomerMgtActivity.class);
		startActivity(intent);
	}
	
	public void goToModuleDiscount(View view) {
		
		Intent intent = new Intent(this, DiscountMgtActivity.class);
		startActivity(intent);
	}
	
	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        
		@Override
        protected String doInBackground(String... urls) {
			
			List<ProductGroupBean> productGroups = dataMgr.getProductGroups();
            ProductGroupBean[] list = productGroups.toArray(new ProductGroupBean[productGroups.size()]);
            return POST(urls[0], list);
        }
        
		// onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Alert");
            
            alertDialogBuilder.setMessage("Result : " + result);

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
       }
    }
	
	public static String POST(String url, ProductGroupBean[] list){
        
		InputStream inputStream = null;
        String result = "";
        
        try {
 
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);
 
            final OutputStream out = new ByteArrayOutputStream();
            final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            
            String json = "";
            
            ow.writeValue(out, list);
            json = out.toString();
            
            result = json;
            
            StringEntity se = new StringEntity(json);
 
            // 6. set httpPost Entity
            httpPost.setEntity(se);
 
            // 7. Set some headers to inform server about the type of the content   
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
 
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
 
            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        // 11. return result
        return result;
    }
	
	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
    }
}
