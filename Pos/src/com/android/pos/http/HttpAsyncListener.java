package com.android.pos.http;

public interface HttpAsyncListener {
	
	public void setSyncProgress(int progress);
	
	public void setSyncMessage(String message);
}
