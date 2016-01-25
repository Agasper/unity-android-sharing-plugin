package net.agasper.unitysharingplugin;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.unity3d.player.UnityPlayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;


class PermissionListener implements PermissionRequester.Listener {
	public void onComplete(boolean status)
	{
	}
}

public class Sharing 
{
	
	static void Share(final String title, final String text, final int withImage) throws IOException
	{
		if (Build.VERSION.SDK_INT >= 23 && withImage != 0)
		{
			new PermissionRequester(UnityPlayer.currentActivity, new PermissionListener() {
				@Override
				public void onComplete(boolean status)
				{
					try 
					{
						if (status)
							ShareWoRequestPermissions(title, text, withImage);
					} catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
			});
		}
		else
			ShareWoRequestPermissions(title, text, withImage);
	}
	
	public static String GetImagePath()
	{
		File image = new File(UnityPlayer.currentActivity.getCacheDir() + File.separator + "temp.png");
		if (!image.getParentFile().exists())
			image.getParentFile().mkdirs();
		return image.getPath();
	}
	
	static void ShareWoRequestPermissions(String title, String text, int withImage) throws IOException
	{
		Intent sharingIntent = new Intent();
		sharingIntent.setAction(Intent.ACTION_SEND);
		sharingIntent.putExtra(Intent.EXTRA_TITLE, title);
		sharingIntent.putExtra(Intent.EXTRA_SUBJECT, title);
		sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
		if (withImage == 0)
		{
			sharingIntent.setType("text/plain");
		}
		else
		{
			String outputPath = Environment.getExternalStorageDirectory() + File.separator + "temporaryFile.png";
			CopyFile(GetImagePath(), outputPath);
			new File(GetImagePath()).delete();
			File resultFile = new File(outputPath);
			Uri uri = Uri.fromFile(resultFile);
			
			//File resultFile = new File(GetImagePath());
			//Uri uri = FileProvider.getUriForFile(UnityPlayer.currentActivity, UnityPlayer.currentActivity.getPackageName(), resultFile);
			if (resultFile.exists())
			{
				sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
				sharingIntent.setType("image/*");
				sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			}
		}

		UnityPlayer.currentActivity.startActivity(Intent.createChooser(sharingIntent, "Share with"));
	}

	static void CopyFile(String inputPath, String outputPath) {

	    InputStream in = null;
	    OutputStream out = null;
	    try {

	        in = new FileInputStream(inputPath);        
	        out = new FileOutputStream(outputPath);

	        byte[] buffer = new byte[1024];
	        int read;
	        while ((read = in.read(buffer)) != -1) {
	            out.write(buffer, 0, read);
	        }
	        in.close();
	        in = null;

	            // write the output file (You have now copied the file)
	            out.flush();
	        out.close();
	        out = null;        

	    }  catch (FileNotFoundException fnfe1) {
	        Log.e("tag", fnfe1.getMessage());
	    }
	            catch (Exception e) {
	        Log.e("tag", e.getMessage());
	    }

	}
}
