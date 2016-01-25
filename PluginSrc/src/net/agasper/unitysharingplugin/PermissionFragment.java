package net.agasper.unitysharingplugin;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
import java.util.ArrayList;
 
public class PermissionFragment extends Fragment {
 
	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
	        Manifest.permission.READ_EXTERNAL_STORAGE,
	        Manifest.permission.WRITE_EXTERNAL_STORAGE
	};

 
    private boolean mAskedPermission = false;
 
    public PermissionFragment() {}
 
 
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        checkThemePermissions();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
 
    public void checkThemePermissions() {
 
  // Check and request for permissions for Android M and older versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !mAskedPermission) {
 
            ArrayList<String> requiredPermissions = new ArrayList<String>();
 
            for (String perm : PERMISSIONS_STORAGE) {
                if (getActivity().checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                    requiredPermissions.add(perm);
                }
            }
 
            if (requiredPermissions.size() > 0) {
                boolean pr = false;
                for (String p: requiredPermissions) {
                    pr = shouldShowRequestPermissionRationale(p);
                    if (pr) {
                        break;
                    }
                }
 
                if (pr) {
                    // We've been denied once before,
                    // Add you logic here as to why we should request for permission once again
                }
                this.requestPermissions(requiredPermissions.toArray(new String[requiredPermissions.size()]), REQUEST_EXTERNAL_STORAGE);
            } else {
                PermissionRequester.instance().onComplete(true);
            }
        } else {
            PermissionRequester.instance().onComplete(true);
        }
 
        mAskedPermission = true;
    }
 
 // Once user allow or deny permissions this method is called
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            boolean res = true;
            for (int i: grantResults) {
                if (i != PackageManager.PERMISSION_GRANTED) {
                    res = false;
                    break;
                }
            }
 
            PermissionRequester.instance().onComplete(res);
        }
 
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}