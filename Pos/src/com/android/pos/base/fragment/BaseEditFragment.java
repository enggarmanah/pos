package com.android.pos.base.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.util.CommonUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public abstract class BaseEditFragment<T> extends BaseFragment {
    
	ArrayAdapter<CharSequence> statusArrayAdapter;
    
    protected T mItem;
    
    BaseItemListener<T> mItemListener;
    
    List<Object> mInputFields = new ArrayList<Object>();
    
    protected boolean isEnableInputFields = false;
    
    @SuppressWarnings("unchecked")
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mItemListener = (BaseItemListener<T>) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement BaseItemListener<T>");
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        initViewReference();
    	
        updateView(mItem);
        
        // new item case
        if (mItem != null && getItemId(mItem) == null) {
        	enableInputFields(true);
        }
    }
    
    protected abstract void initViewReference();
    
    @Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
		
		saveItem();
	}
    
    protected void registerField(Object field) {
    	
    	mInputFields.add(field);
    }
    
    public void enableInputFields(boolean isEnabled) {
    	
    	isEnableInputFields = isEnabled;
    	
    	for (Object field : mInputFields) {
    		
    		if (field instanceof TextView) {
    			
    			TextView textView = (TextView) field;
    			textView.setEnabled(isEnabled);
    		
    		} else if (field instanceof Spinner) {
    			
    			Spinner spinner = (Spinner) field;
    			spinner.setEnabled(isEnabled);
    		}
    	}
    }
    
    protected void linkDatePickerWithInputField(String fragmentTag, TextView inputField) {
    	
    	DatePickerFragment dp = (DatePickerFragment) getFragmentManager().findFragmentByTag(fragmentTag);
    	if (dp != null) {
    		dp.setInputField(inputField);
    	}
    }
    
    protected View.OnClickListener getDateFieldOnClickListener(final EditText dateInput, final String fragmentTag) {
    	
    	return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (getActivity().getCurrentFocus() != null) {
					getActivity().getCurrentFocus().clearFocus();
				}
				
				DatePickerFragment fragment = null;
				
				if (getFragmentManager().findFragmentByTag(fragmentTag) != null) {
					fragment = (DatePickerFragment) getFragmentManager().findFragmentByTag(fragmentTag);
				} else {
					fragment = new DatePickerFragment(dateInput);
				}
				
				fragment.show(getFragmentManager(), fragmentTag);
			}
		};
    }
    
    protected View.OnFocusChangeListener getCurrencyFieldOnFocusChangeListener(final EditText numberInput) {
    	
    	return new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				
				String digits = numberInput.getText().toString();
				
				String value = Constant.EMPTY_STRING;
				
				if (hasFocus) {
                	value = CommonUtil.formatString(CommonUtil.parseCurrency(digits));
                	numberInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                	numberInput.setText(value);
				} else {
					value = CommonUtil.formatCurrency(digits);
					numberInput.setInputType(InputType.TYPE_CLASS_TEXT);
					numberInput.setText(value);
				}
			}
		};
    }
    
    protected View.OnFocusChangeListener getNumberFieldOnFocusChangeListener(final EditText numberInput) {
    	
    	return new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				
				String digits = numberInput.getText().toString();
				
				String value = Constant.EMPTY_STRING;
				
				if (hasFocus) {
                	value = CommonUtil.formatString(CommonUtil.parseCurrency(digits));
                	numberInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                	numberInput.setText(value);
				} else {
					value = CommonUtil.formatNumber(digits);
					numberInput.setInputType(InputType.TYPE_CLASS_TEXT);
					numberInput.setText(value);
				}
			}
		};
    }
    
    protected abstract void updateView(T product);
    
    protected void showView() {
    	
    	if (getView().getVisibility() == View.INVISIBLE) {
    		
    		getView().setVisibility(View.VISIBLE);
    	}
    	
    	if (getFirstField() != null) {
    		getFirstField().requestFocus();
    	}
    }
    
    protected void hideView() {
    	
    	if (getView().getVisibility() == View.VISIBLE) {
    		
    		getView().setVisibility(View.INVISIBLE);
    	}
    }
    
    protected abstract void saveItem();
    
    protected abstract Long getItemId(T item);
    
    public void saveEditItem() {
    	
    	saveItem();
    	
    	if (!isValidated()) {
			return;
		}
		
    	if (getFirstField() != null) {
    		getFirstField().requestFocus();
    	}
    	
		saveItem();
    	
    	if (getItemId(mItem) == null) {
    		
    		if (addItem()) {
    			mItemListener.onAddCompleted();
    			showMessage(R.string.msg_data_save_success);
    		}
    	} else {
    		
    		if (updateItem()) {
    			mItemListener.onUpdateCompleted();
    			showMessage(R.string.msg_data_save_success);
    		}
    	}
   
    	hideKeyboard();
    }
    
    public void discardEditItem() {
    	
    	mItem = null;
		
		mItemListener.onItemUnselected();
		mItemListener.displaySearch();
		
		hideKeyboard();
    }
    
    protected abstract boolean addItem();
    
    protected abstract boolean updateItem();
    
    protected abstract TextView getFirstField();
    
    protected void hideKeyboard() {
    	
    	if (getFirstField() != null) {
        	InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        	imm.hideSoftInputFromWindow(getFirstField().getWindowToken(), 0);
    	}
    }
    
    public void setItem(T item) {
    	
    	this.mItem = item;
    }
    
    public abstract T updateItem(T item);
    
    public void refreshView() {
    	
    	// stop if the view is not yet initialized 
    	
    	if (getView() != null) {
    		updateView(mItem);
    	}
    }
    
    public void addItem(T item) {
    	
    	this.mItem = item;
    }
}