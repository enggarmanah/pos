package com.android.pos;

import com.android.pos.util.CommonUtil;

import android.app.Application;

public class TokokuApp extends Application {

	@Override
	public void onCreate() {
		CommonUtil.initTracker(this);
	}
}
