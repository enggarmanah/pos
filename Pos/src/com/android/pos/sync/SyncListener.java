package com.android.pos.sync;

public interface SyncListener {
	
	public void setSyncProgress(int progress);
	
	public void setSyncMessage(String message);
}
