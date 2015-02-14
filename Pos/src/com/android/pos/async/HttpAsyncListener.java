package com.android.pos.async;

public interface HttpAsyncListener {
	
	public void setSyncProgress(int progress);
	
	public void setSyncMessage(String message);
}
