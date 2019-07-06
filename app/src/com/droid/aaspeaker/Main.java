package com.droid.aaspeaker;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnCancelListener;
//import android.net.wifi.WifiInfo;
//import android.net.wifi.WifiManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Main extends Activity {
	public String day;
	public String time;
	public String state;
	public String fellowship;
	String locInfo = null;
	static final private int TIME_DIALOG = 2;
	static final private int NULL_DIALOG = 3;
	static final private int CREATE_MEETING_DIALOG = 4;
	private TextView editTextTime, editTextType, editTextGroupName, editTextStreet, editTextPhone, editTextCity, editTextZipcode, editTextLocation;    
	private int mHour;    
	private int mMinute;    
	//Meeting selectedMeeting;    
	BroadcastReceiver mConnReceiver;
	Button bigBookButton;
    Button joeButton;
    Button oldTimersButton;
    Button playerButton;
    
    
	public final String LOGTAG = "Main.java";	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        bigBookButton = (Button)findViewById(R.id.bigBookButton);
        joeButton = (Button)findViewById(R.id.joeButton);
        oldTimersButton = (Button)findViewById(R.id.oldTimersButton);
        playerButton = (Button)findViewById(R.id.playerButton);

            
            
        	bigBookButton.setOnClickListener( new OnClickListener() {
            	public void onClick(View view) {
              		Intent intent = new Intent();
              		intent.setClass(getApplicationContext(), SongsActivity.class);
              		//intent.setAction("MAP_MEETINGS");
              		//Uri uri = Uri.parse(day + '-' + time + '-' + state);
              		//Log.e("Inside WebViewDemo Calling EarthQuake.class", uri.toString());
              		//intent.setData(uri);
              		startActivity(intent);

              	}
              });
              joeButton.setOnClickListener( new OnClickListener() {
              	public void onClick(View view) {
              		Intent intent = new Intent();
              		intent.setClass(getApplicationContext(), MusicDroid.class);
              		//intent.setAction("QUERY_MEETINGS");
              		//Uri uri = Uri.parse(day + '-' + time + '-' + state);
              		        		//intent.setData(uri);
              		startActivity(intent);
              		
              		
              	}
              });
              oldTimersButton.setOnClickListener( new OnClickListener() {
              	public void onClick(View view) {
              		Intent intent = new Intent();
              		intent.setClass(getApplicationContext(), AlbumsActivity.class);
              		//intent.setAction("QUERY_MEETINGS");
              		//Uri uri = Uri.parse(day + '-' + time + '-' + state);
              		        		//intent.setData(uri);
              		startActivity(intent);	
              	}
              });
      
              playerButton.setOnClickListener( new OnClickListener() {
                	public void onClick(View view) {
                		Intent intent = new Intent();
                		intent.setClass(getApplicationContext(), MusicActivity.class);
                		//intent.setAction("QUERY_MEETINGS");
                		//Uri uri = Uri.parse(day + '-' + time + '-' + state);
                		        		//intent.setData(uri);
                		//startActivity(intent);	
                	}
                });
      
        

            
    }
    
    @Override
	protected void onPause() {
	//Log.e(LOGTAG, "onPause");
	super.onPause();
	//Log.e("main activity on pause", "un registering receiver");
	try {
		unregisterReceiver(mConnReceiver);
	} 
	catch(IllegalArgumentException e) {
		e.printStackTrace();
	}
	}
	
	@Override
	protected void onResume() {
	//Log.e(LOGTAG, "onResume");
	super.onResume();
	//Log.e("on resume", "registering receiver");
	registerReceiver(mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
	}
}
