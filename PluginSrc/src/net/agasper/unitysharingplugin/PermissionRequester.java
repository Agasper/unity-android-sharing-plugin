package net.agasper.unitysharingplugin;

import java.io.IOException;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class PermissionRequester {
	
	public static interface Listener {
		public void onComplete(boolean status);
	}
	
	private Activity mActivity;
	private Listener mListener;
	public static PermissionRequester mListner = null;
	public static PermissionRequester instance() {
		return mListner;
	}
	
	public PermissionRequester(Activity a, Listener l) {
		mListner = this;
		mActivity = a;
		mListener = l;
		
		FragmentManager fm = mActivity.getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(new PermissionFragment(), "perm_fragment");
		ft.commit();
	}
	
	public void onComplete(boolean v) {
		mListener.onComplete(v);
	} 
}