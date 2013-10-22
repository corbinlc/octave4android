// Copyright (C) 2013 Free Software Foundation FSF
//
// This file is part of octave4android.
//
// octave4android is free software; you can redistribute it and/or modify it
// under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 3 of the License, or (at
// your option) any later version.
//
// octave4android is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with octave4android. If not, see <http://www.gnu.org/licenses/>.


package com.octave;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;
import android.widget.Toast;
import com.octave.R;

public class octaveMain extends Activity {

	private ProgressDialog mPd_ring;
	private boolean mAlreadyStarted;
	private boolean mExpectingResult = false;
	private boolean mHoldOff = false;
	private boolean mAskForDonation = false;
	private boolean mErrOcc = false;
	private String mPrefix = "com.octave";
	private String mHome = "/data/data/" + mPrefix;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); 
		TextView textView = (TextView)findViewById(R.id.myTextView);
		textView.setText("Octave is unpacking and launching.\nIf this fails, this is probabably the cause:\n  You may not have enough storage space for Octave to unpack.  Please free up space and then uninstall and then reinstall Octave.");

		mAlreadyStarted = false;

		ViewTreeObserver viewTreeObserver = textView.getViewTreeObserver();
		if (viewTreeObserver.isAlive()) { 
			viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					if (mAlreadyStarted == false) {
						mAlreadyStarted = true;
						mPd_ring = ProgressDialog.show(octaveMain.this, "Unpacking Octave", "This may take a while.",true);
						mPd_ring.setCancelable(false);
						Thread t = new Thread() {
							public void run() {
								try {
									unpackAll();
								} catch (Exception e) {
									Log.e("LongToast", "", e);
								}
							}
						};
						t.start();
					}
				}
			});
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);

		if(getIntent().hasExtra("returnFromMeasure")) {
			launchATE();
		}
	}

	@Override
	public void onResume() {
		super.onResume(); 
		if (mHoldOff == true) {
			mHoldOff = false;
			launchATE();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		if (mExpectingResult) {
			mExpectingResult = false;
			if (requestCode == 0) {
				if (resultCode == 0) {
					kickItOff();
				}
			}
		}
	}

	private void goMeasure() {
		mPd_ring.dismiss();
		Intent i1 = new Intent();
		i1.setClassName("com.droidplot", "com.droidplot.droidplotMain");
		i1.putExtra("measureAndExit",true);
		try {
			mExpectingResult = true;
			startActivityForResult(i1,0);
		} catch (ActivityNotFoundException e) {
			//TODO: Should give them information that plotting is disabled
			mExpectingResult = false;
			kickItOff();
		}
	}

	private void kickItOff() {
		if (mHoldOff == false) {
			if (mAskForDonation == true) {
				mAskForDonation = false;
				askForDonation();
				return;
			}
			launchATE();
		} 
	}

	private void unpackAll() {

		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			mHome = Environment.getExternalStorageDirectory().getAbsolutePath()+"/freeRoot";
			File freeRoot = new File(mHome);
			if (freeRoot.exists()==false) { 
				freeRoot.mkdir();
				exec("chmod 0777 " + mHome); 
			}
		} else {
			mHome = "/data/data/" + mPrefix;
		}

		File octaverc = new File(mHome+"/.octaverc");
		if (octaverc.exists()==false) { 
			try {
				octaverc.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			exec("chmod 0777 " + mHome + "/.octaverc");
		}

		installPackage("com.octave.donate5");
		installPackage("com.octave.donate10");
		installPackage("com.octave.donate25");
		installPackage("com.octave.donate50");
		installPackage("com.octave.donate100");
		
		mAskForDonation = true;
		File testFile = new File("/data/data/" + mPrefix + "/donate5");
		if (testFile.exists()) {
			mAskForDonation = false;
		}
		testFile = new File("/data/data/" + mPrefix + "/donate10");
		if (testFile.exists()) {
			mAskForDonation = false;
		}
		testFile = new File("/data/data/" + mPrefix + "/donate25");
		if (testFile.exists()) {
			mAskForDonation = false;
		}
		testFile = new File("/data/data/" + mPrefix + "/donate50");
		if (testFile.exists()) {
			mAskForDonation = false;
		}
		testFile = new File("/data/data/" + mPrefix + "/donate100");
		if (testFile.exists()) {
			mAskForDonation = false;
		}

		if (updateRequired(mPrefix)) {

			exec("chmod 0777 /data/data/" + mPrefix);
			exec("chmod 0777 /data/data/" + mPrefix + "/lib"); 

			//delete old unused directories
			exec("rm -rf /data/data/" + mPrefix + "/bin");
			exec("rm -rf /data/data/" + mPrefix + "/mylib");
			exec("rm -rf /data/data/" + mPrefix + "/unzippedFiles");

			//delete freeRoot to get a clean update
			exec("rm -rf /data/data/" + mPrefix + "/freeRoot");

			File tmpDir = new File("/data/data/" + mPrefix + "/tmp/");
			if (tmpDir.exists()==false) { 
				tmpDir.mkdir();
			}
			exec("chmod 0777 /data/data/" + mPrefix + "/tmp");

		}

		installPackage("com.octave");
		installPackage("com.octave.signal");
		installPackage("com.octave.mapping");
		installPackage("com.octave.fuzzy");
		installPackage("com.octave.control");
		installPackage("com.octave.io");
		installPackage("com.octave.missing");
		installPackage("com.octave.optim");
		installPackage("com.octave.statistics");
		installPackage("com.octave.specfun");
		installPackage("com.octave.ode");
		installPackage("com.octave.financial");

		goMeasure();
	}

	private void installPackage(String packageName) {
		String packageTopStr = "/data/data/"+packageName+"/lib/";

		mErrOcc = false;

		if (updateRequired(packageName)) {
			//first create directories needed
			processDirFile(packageTopStr+"lib__install_dir.so");
			//create all files needed, but linking them to actual files in the lib dir
			processLinkFile(packageTopStr+"lib__install_file.so");
			//create all links needed
			processLinkFile(packageTopStr+"lib__install_link.so");

			if (mErrOcc == false) {
				createVersionFile(packageName);
			}
		}

	}

	public boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// The directory is now empty so delete it
		return dir.delete();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{ 
		super.onConfigurationChanged(newConfig);
	}

	private void processDirFile(String dirFile) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(dirFile));
			String line;
			while ((line = br.readLine()) != null) {
				line = line.replace("/data/data/com.octave/", "/data/data/" + mPrefix + "/");
				createDir(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	private void processLinkFile(String linkFile) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(linkFile));
			String line;
			while ((line = br.readLine()) != null) {
				String[] temp = line.split(" ");
				File tmpFile = new File(temp[1]);
				if (tmpFile.exists()) {
					tmpFile.delete();
				}
				line = line.replace("/data/data/com.octave/", "/data/data/" + mPrefix + "/");
				exec("ln -s " + line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createDir(File dir) {
		if (!dir.getParentFile().exists()) { 
			createDir(dir.getParentFile()); 
		}
		if (dir.exists()) { 
			return; 
		} 
		Log.v("Unzip", "Creating dir " + dir.getName()); 
		if (!dir.mkdir()) { 
			throw new RuntimeException("Can not create dir " + dir); 
		}
		exec("chmod 0777 " + dir.getAbsolutePath());
	}

	private void createDir(String path) {
		File dir = new File(path);
		createDir(dir);
	}

	private void exec(String command) {
		Runtime runtime = Runtime.getRuntime(); 
		Process process;
		try {
			process = runtime.exec(command);
			try {
				String str;
				process.waitFor();
				BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				while ((str = stdError.readLine()) != null) {
					Log.e("Exec",str);
					mErrOcc = true;
				}
				process.getInputStream().close(); 
				process.getOutputStream().close(); 
				process.getErrorStream().close(); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void cleanHouse() {
		File directory = new File("/data/data/" + mPrefix + "/tmp");

		// Get all files in directory
		if (directory.exists()) {
			File[] files = directory.listFiles();
			for (File file : files)
			{
				// Delete each file
				if (!file.delete())
				{
					// Failed to delete file
					System.out.println("Failed to delete "+file);
				}
			}
		}
	}

	final CharSequence[] mItemsShort={"Get GNURoot", "Get Tiny Utils","Donate $5","Donate $10","Donate $25","Donate $50","Donate $100","Maybe Later"};
	private void askForDonation() {

		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				AlertDialog.Builder builder=new AlertDialog.Builder(octaveMain.this);

				builder.setTitle("Please support free (as in speech) SW for Android by:").setItems(mItemsShort, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent goToMarket0 = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://details?id=champion.gnuroot"));
							startActivity(goToMarket0);
							mHoldOff = true;
							break;
						case 1:
							Intent goToMarket1 = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://details?id=champion.tinyutils"));
							startActivity(goToMarket1);
							mHoldOff = true;
							break;
						case 2:
							Intent goToMarket2 = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://details?id=com.octave.donate5"));
							startActivity(goToMarket2);
							mHoldOff = true;
							break;
						case 3:
							Intent goToMarket3 = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://details?id=com.octave.donate10"));
							startActivity(goToMarket3);
							mHoldOff = true;
							break;
						case 4:
							Intent goToMarket4 = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://details?id=com.octave.donate25"));
							startActivity(goToMarket4);
							mHoldOff = true;
							break;
						case 5:
							Intent goToMarket5 = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://details?id=com.octave.donate50"));
							startActivity(goToMarket5);
							mHoldOff = true;
							break;
						case 6:
							Intent goToMarket6 = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://details?id=com.octave.donate100"));
							startActivity(goToMarket6);
							mHoldOff = true;
							break;
						case 7:
							Toast.makeText(getApplicationContext(), "Thanks for considering this!", Toast.LENGTH_LONG).show();
							break;
						}
						kickItOff();
					}

				});

				builder.show();
			}
		});

	}

	private void launchATE() {
		mPd_ring.dismiss();
		cleanHouse();
		Intent termIntent = new Intent(octaveMain.this, jackpal.androidterm.RemoteInterface.class);
		termIntent.addCategory(Intent.CATEGORY_DEFAULT);
		termIntent.setAction("jackpal.androidterm.OPEN_NEW_WINDOW");
		startActivity(termIntent);
		finish();
	}

	private boolean updateRequired(String packageName) {
		String version;

		try {
			PackageInfo pi = getPackageManager().getPackageInfo(packageName, 0);
			version = pi.versionName;     // this is the line Eclipse complains
		}
		catch (PackageManager.NameNotFoundException e) {
			return false;
		}

		File versionFile = new File("/data/data/" + mPrefix + "/"+packageName+"."+version);

		if (versionFile.exists()==false) {
			return true;
		} else {
			return false;
		}
	}

	private void createVersionFile(String packageName) {
		String version;

		try {
			PackageInfo pi = getPackageManager().getPackageInfo(packageName, 0);
			version = pi.versionName;     // this is the line Eclipse complains
		}
		catch (PackageManager.NameNotFoundException e) {
			version = "?";
		}

		File versionFile = new File("/data/data/" + mPrefix + "/"+packageName+"."+version);

		if (versionFile.exists()==false) {
			try {
				versionFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}