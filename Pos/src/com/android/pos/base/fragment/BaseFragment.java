package com.android.pos.base.fragment;

import java.util.List;

import com.android.pos.CommonUtil;
import com.android.pos.Constant;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {
	
	protected List<FormField> mandatoryFields;
	
	protected boolean isValidated() {
    	
    	for (FormField field : mandatoryFields) {
    		
    		View input = field.getField();
    		String value = null;
    		
    		if (input instanceof TextView) {
    			
    			TextView inputText = (TextView) input;
    			value = inputText.getText().toString();
    		}
    		
    		if (CommonUtil.isEmpty(value)) {
    			
    			new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo))
    			.setTitle("Perhatian!")
    		    .setMessage(getResources().getString(field.getLabel()) + " tidak boleh kosong.")
    		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
    		        public void onClick(DialogInterface dialog, int which) { 
    		            // continue with delete
    		        }
    		     })
    		    .setIcon(android.R.drawable.ic_dialog_alert)
    		     .show();
    			
    			input.requestFocus();
    			
    			return false;
    		}
    	}
    	
    	return true;
    }
	
	public void afterStart() {};
	
	public void initWaitAfterStartTask() {
		
		WaitAfterStartTask task = new WaitAfterStartTask();
		task.execute();
	}
	
	public void showMessage(int resourceId) {
		
		Toast toast = Toast.makeText(getActivity(), getResources().getString(resourceId), Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
	}
	
	public void showMessage(String message) {
		
		Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
	}
	
	protected class FormField {

		View field;
		int label;
		
		public FormField(View field, int label) {
			
			this.field = field;
			this.label = label;
		}
		
		public View getField() {
			return field;
		}

		public void setField(View field) {
			this.field = field;
		}

		public int getLabel() {
			return label;
		}

		public void setLabel(int label) {
			this.label = label;
		}
    }
	
	public class WaitAfterStartTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {

			try {
				Thread.sleep(Constant.WAIT_ON_LOAD_PERIOD);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return true;
		}

		@Override
		protected void onProgressUpdate(Void... progress) {
		}

		@Override
		protected void onPostExecute(Boolean result) {

			afterStart();
		}
	}
	
	protected boolean isViewInitialized() {
		
		if (getView() != null) {
			return true;
		} else {
			return false;
		}
	}
}
