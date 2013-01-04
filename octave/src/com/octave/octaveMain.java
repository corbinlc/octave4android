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

public class octaveMain extends Activity {

	private ProgressDialog mPd_ring;
	private boolean mAlreadyStarted;
	private Toast mToast;
	private Toast mToast2;
	private boolean mExpectingResult = false;
	private boolean mAskForDonation = false;
	private boolean mErrOcc = false;
	private String mHome = "/data/data/com.octave";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); 
		TextView textView = (TextView)findViewById(R.id.myTextView);
		textView.setText("Octave is unpacking and launching via Android Terminal Emulator\nIf this fails, there are two primary reasons:\n1) You must have Android Terminal Emulator by Jack Palevick installed BEFORE you attempt to install Octave.  Make sure you have an up to date version installed and then uninstall and reinstall Octave.\n2) You may not have enough storage space for Octave to unpack.  Please free up space and then uninstall and then reinstall Octave.");
		mToast = Toast.makeText(this, "For now, users are required to have a recent version of the Android Terminal Emulator installed before installing Octave.  Please uninstall Octave, confirm you have Android Terminal Emulator installed and up to date and then reinstall Octave.", Toast.LENGTH_LONG);

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
		if (mAskForDonation == true) {
			mAskForDonation = false;
			File tmpFile = new File("/data/data/com.octave/askForDonation");
			tmpFile.delete();
			askForDonation();
			return;
		}
		launchATE();
	}

	private void fireLongToast() {
		Thread t = new Thread() {
			public void run() {
				int count = 0;
				try {
					while (count < 5) {
						mToast.show();
						sleep(2500);
						count++;
					}
				} catch (Exception e) {
					Log.e("LongToast", "", e);
				}
			}
		};
		t.start();
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
			mHome = "/data/data/com.octave";
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

		File donationFile = new File("/data/data/com.octave/askForDonation");
		if (donationFile.exists() == true) {
			mAskForDonation = true;
		}
		if (updateRequired("com.octave")) {

			exec("chmod 0777 /data/data/com.octave");
			exec("chmod 0777 /data/data/com.octave/lib"); 

			//delete old unused directories
			exec("rm -rf /data/data/com.octave/bin");
			exec("rm -rf /data/data/com.octave/mylib");
			exec("rm -rf /data/data/com.octave/unzippedFiles");

			//delete freeRoot to get a clean update
			exec("rm -rf /data/data/com.octave/freeRoot");

			File tmpDir = new File("/data/data/com.octave/tmp/");
			if (tmpDir.exists()==false) { 
				tmpDir.mkdir();
			}
			exec("chmod 0777 /data/data/com.octave/tmp");

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

	@Override
	public void onDestroy() {
		super.onDestroy();
		File directory = new File("/data/data/com.octave/tmp");

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
		finish();
	}

	final CharSequence[] mItemsShort={"Any amount via Paypal","Maybe Later"};
	private void askForDonation() {

		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				AlertDialog.Builder builder=new AlertDialog.Builder(octaveMain.this);

				builder.setTitle("Please consider supporting free SW for Android.").setItems(mItemsShort, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0: 
							Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=4JELWYF6CNHVU&lc=US&item_name=Corbin%20Champion%20Designs&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted"));
							startActivity(viewIntent);
							break;	
						case 1:
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
		Intent i2 = new Intent("jackpal.androidterm.RUN_SCRIPT");
		i2.addCategory(Intent.CATEGORY_DEFAULT);
		i2.putExtra("jackpal.androidterm.iInitialCommand", "umask 000; export HOME="+mHome+"; cd /data/data/com.octave/; /data/data/com.octave/freeRoot/lib/ld-linux.so.3 --library-path /data/data/com.octave/freeRoot/lib:/data/data/com.octave/freeRoot/usr/local/lib /data/data/com.octave/freeRoot/usr/local/bin/octave");
		try {
			startActivity(i2);
		} catch (ActivityNotFoundException e1) {
			fireLongToast();
			String packageName = "jackpal.androidterm";
			Intent goToMarket = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://details?id="+packageName));
			startActivity(goToMarket);
		} catch (SecurityException e2) {
			fireLongToast();
		}
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

		File versionFile = new File("/data/data/com.octave/"+packageName+"."+version);

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

		File versionFile = new File("/data/data/com.octave/"+packageName+"."+version);

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