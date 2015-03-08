package com.android.pos.base.fragment;

import java.util.List;

import com.android.pos.util.CommonUtil;
import com.android.pos.util.NotificationUtil;

import android.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {
	
	protected List<FormField> mandatoryFields;
	
	public void onStart() {
		super.onStart();
	}
	
	protected boolean isValidated() {
    	
    	for (FormField field : mandatoryFields) {
    		
    		View input = field.getField();
    		String value = null;
    		
    		if (input instanceof TextView) {
    			
    			TextView inputText = (TextView) input;
    			value = inputText.getText().toString();
    		}
    		
    		if (CommonUtil.isEmpty(value)) {
    			
    			String fieldLabel = getString(field.getLabel()).replace(" :", "");
    			
    			NotificationUtil.setAlertMessage(getFragmentManager(), fieldLabel + " tidak boleh kosong.");
    			
    			input.requestFocus();
    			
    			return false;
    		}
    	}
    	
    	return true;
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
	
	protected boolean isViewInitialized() {
		
		if (getView() != null) {
			return true;
		} else {
			return false;
		}
	}
}
