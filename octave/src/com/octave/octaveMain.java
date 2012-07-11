package com.octave;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;
import android.widget.Toast;

public class octaveMain extends Activity implements Observer {

	private ProgressDialog mPd_ring;
	private int mUnzipCnt;
	private boolean mNoUnzipNecessary;
	private boolean mUnzipsAllKickedOff;
	private boolean mAlreadyStarted;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); 
		TextView textView = (TextView)findViewById(R.id.myTextView);
		
		mAlreadyStarted = false;

		ViewTreeObserver viewTreeObserver = textView.getViewTreeObserver();
		if (viewTreeObserver.isAlive()) {
			viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					if (mAlreadyStarted == false) {
						mAlreadyStarted = true;
						mNoUnzipNecessary = true;
						mUnzipsAllKickedOff = false;
						mPd_ring = ProgressDialog.show(octaveMain.this, "Unpacking Octave", "This may take a while (several minutes if this is the first time).",true);
						mPd_ring.setCancelable(false);
						unpackAllZipFiles();
					}
				}
			});
		}
	}

	private void kickItOff() {
		mPd_ring.dismiss();
		// opens a new window and runs "echo 'Hi there!'"
		// application must declare jackpal.androidterm.permission.RUN_SCRIPT in manifest
		Intent i = new Intent("jackpal.androidterm.RUN_SCRIPT");
		i.addCategory(Intent.CATEGORY_DEFAULT);
		i.putExtra("jackpal.androidterm.iInitialCommand", "/data/data/com.octave/mylib/ld-linux.so.3 --library-path /data/data/com.octave/mylib /data/data/com.octave/bin/octave");
		try {
			startActivity(i);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, "You must have a recent version of the Android Terminal Emulator installed for this to work.", Toast.LENGTH_LONG).show();
			//To do: send them to the market in the future
		}

	}

	private void unpackAllZipFiles() {
		
		Runtime runtime = Runtime.getRuntime(); 
		Process process;
		try {
			process = runtime.exec("chmod 0755 /data/data/com.octave");
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			process = runtime.exec("chmod 0755 /data/data/com.octave/lib");
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} 

		File unzipList = new File("/data/data/com.octave/unzippedFiles/");
		if (unzipList.exists()==false) { 
			unzipList.mkdir();
		} 

		File fileList = new File("/data/data/com.octave/lib/"); 
		if (fileList != null) { 
			File[] filenames = fileList.listFiles(); 
			for (File tmpf : filenames) { 
				String fileName = tmpf.getName();
				if (fileName.startsWith("libzip")) {
					boolean alreadyUnpacked = false;
					File[] unzipnames = unzipList.listFiles(); 
					for (File tmpUnzipName: unzipnames) {
						String unzipName = tmpUnzipName.getName();
						if (unzipName.equalsIgnoreCase(fileName)) {
							alreadyUnpacked = true;
						}
					}
					if (alreadyUnpacked == false) {
						mUnzipCnt++;
						mNoUnzipNecessary = false;

						unzipFile(fileName);
						File tmpFile = new File("/data/data/com.octave/unzippedFiles/" + fileName);
						try {
							tmpFile.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			if (mNoUnzipNecessary) {
				kickItOff();
			}
			mUnzipsAllKickedOff = true;
		}
	}

	private void unzipFile(String filename) { 
		String unzipLocation = "/data/data/com.octave/";
		String filePath = "/data/data/com.octave/lib/";

		String fullPath = filePath + filename; 
		Log.d("UnZip", "unzipping " + fullPath + " to " + unzipLocation); 
		new UnZipTask().execute(fullPath, unzipLocation); 
	}

	private class UnZipTask extends AsyncTask<String, Void, Boolean> { 

		private static final String TAG = "UnZip"; 

		@SuppressWarnings("rawtypes") 
		@Override 
		protected Boolean doInBackground(String... params) { 
			String filePath = params[0]; 
			String destinationPath = params[1]; 

			File archive = new File(filePath); 
			try { 
				ZipFile zipfile = new ZipFile(archive); 
				for (Enumeration e = zipfile.entries(); e.hasMoreElements();) { 
					ZipEntry entry = (ZipEntry) e.nextElement(); 
					unzipEntry(zipfile, entry, destinationPath); 
				} 
			} catch (Exception e) { 
				Log.e(TAG, "Error while extracting file " + archive, e);  
				return false; 
			}

			mUnzipCnt--;
			if ((mUnzipsAllKickedOff == true) && (mUnzipCnt == 0)) {
				kickItOff();
			}

			return true; 
		} 

		private void unzipEntry(ZipFile zipfile, ZipEntry entry, 
				String outputDir) throws IOException { 

			File outputFile = new File(outputDir, entry.getName());
			if (entry.isDirectory()) { 
				createDir(outputFile);
				return; 
			} 

			if (!outputFile.getParentFile().exists()) { 
				createDir(outputFile.getParentFile()); 
			} 

			Log.v(TAG, "Extracting: " + entry); 
			BufferedInputStream inputStream = new BufferedInputStream(zipfile.getInputStream(entry)); 
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile)); 

			try { 
				IOUtils.copy(inputStream, outputStream); 
			} finally { 
				outputStream.close(); 
				inputStream.close(); 
			} 
			
			Runtime runtime = Runtime.getRuntime(); 
			Process process;
			try {
				process = runtime.exec("chmod 0755 " + outputFile.getAbsolutePath());
				try {
					process.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
		} 

		private void createDir(File dir) {
			if (!dir.getParentFile().exists()) { 
				createDir(dir.getParentFile()); 
			}
			if (dir.exists()) { 
				return; 
			} 
			Log.v(TAG, "Creating dir " + dir.getName()); 
			if (!dir.mkdir()) { 
				throw new RuntimeException("Can not create dir " + dir); 
			}
			Runtime runtime = Runtime.getRuntime(); 
		    Process process;
			try {
				process = runtime.exec("chmod 0755 " + dir.getAbsolutePath());
				try {
					process.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
		} 
	}

	@Override
	public void update(Observable arg0, Object arg1) {
	} 


}