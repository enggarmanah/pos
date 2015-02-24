package com.android.pos.async;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.pos.R;

public class HttpAsyncProgressDlgFragment extends DialogFragment {
	
	ProgressBar mDataSyncPb;
	TextView mSyncMessage;
	
	String mMessage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);

		setCancelable(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.data_sync_progress_fragment, container, false);
		
		return view;
	}
	
	@Override
	public void onStart() {

		super.onStart();
		
		mDataSyncPb = (ProgressBar) getView().findViewById(R.id.dataSyncPb);
		mSyncMessage = (TextView) getView().findViewById(R.id.syncMessageText);
		
		setProgress(0);
		mSyncMessage.setText(mMessage);
	}
	
	public void setProgress(int progress) {
		
		if (mDataSyncPb != null) {
			
			mDataSyncPb.setIndeterminate(progress == 0);
			mDataSyncPb.setProgress(progress);
		}
	}
	
	public void setMessage(String message) {
		
		mMessage = message;
		
		if (mSyncMessage != null) {
			mSyncMessage.setText(message);
		}
	}
}