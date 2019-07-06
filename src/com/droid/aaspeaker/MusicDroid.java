package com.droid.aaspeaker;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;

import android.net.Uri;
import android.os.Bundle; 
import android.view.View; 
import android.view.WindowManager;
import android.widget.Button; 


import android.app.Activity;
import android.app.Application;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.app.TabActivity;
import com.droid.aaspeaker.HelloTabWidget;



import com.droid.aaspeaker.HelloTabWidget;

import java.util.HashMap;

import android.content.Context;

class Mp3Filter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.endsWith(".mp3"));
    }
}



public class MusicDroid extends ListActivity {
    
	public final String MEDIA_PATH = getString(R.string.baseURL);
	private static final String FLAG_ACTIVITY_TASK_ON_HOME  = null;
	public String[] songs;
	//private MediaPlayer mp = new MediaPlayer();
	public static String file;
	
    Button square,circle; 

  
	
	@Override
    public void onCreate(Bundle icicle) {
        
        	super.onCreate(icicle);
        	//setContentView(R.layout.songlist);
        	final ListView listView = getListView(); 
            listView.setEmptyView(listView); 
            setContentView(listView);
        	updateSongList();
       
    }
	    public void updateSongList() {
    	/*File home = new File(MEDIA_PATH);
		if (home.listFiles( new Mp3Filter()).length > 0) {
    		for (File file : home.listFiles( new Mp3Filter())) {
    			songs.add(file.getName());
    		}*/
    		Resources res = getResources();
    		songs = (res.getStringArray(R.array.files_array));
    		//String[] parameter = songs.toString().split("-", 3);
    		String[] parameter = null;
    		int index = songs.length;
    		int i = 0;
			while ( i  < index) {
				parameter = songs[i].split("-",4);
				songs[i] = parameter[0];
				i++;
    		}
    		//Log.e(getString(R.string.app_name), parameter[0].toString());
    		
    		
    		ArrayAdapter<String> songList = new ArrayAdapter<String>(this,R.layout.song_item,songs);
    		
    		setListAdapter(songList);
    		songs = (res.getStringArray(R.array.files_array));
		}    	
    //}
   
    public void onStop() {
    	super.onStop();
    	//mp.stop();
    	//mp.release();
    	
    }
    
    //protected void onPause() {         
    	//super.onPause();
    	//mp.stop();
    	//mp.release();
    //}
    
	@SuppressWarnings("deprecation")
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//mp.reset();
		//mp.setDataSource(MEDIA_PATH + l.getItemAtPosition(position));
		//Log.v(getString(R.string.app_name), (String) l.getItemAtPosition(position));
		String temp =  songs[position];
		String[] temp2 = temp.split("-", 4);
		file = temp2[0];
		//Log.e(getString(R.string.app_name), file);
		//file = temp2[1];
		//mp.prepare();
		//mp.start(); 
		
		/*int FLAG_ACTIVITY_TASK_ON_HOME = 16384;
		Intent intent = null;
		//intent.setFlags(FLAG_ACTIVITY_TASK_ON_HOME);
		
		Instrumentation instrumentation = null;
		//Context packageContext = instrumentation.getContext();*/
	
		
		/*Intent intent = new Intent().setClass(this, Tutorial3.class);
		int FLAG_ACTIVITY_TASK_ON_HOME = 16384;
		intent.setFlags(FLAG_ACTIVITY_TASK_ON_HOME);
		intent.addCategory("CATEGORY_TAB");
		startActivity(intent);
		*/
		//Intent intent = new Intent(this, Tutorial3.class);
		//startActivity(intent);
		//Application activity = getApplication()
		//startActivity("Tutorial3.class");
		
		
		//Activity activity = getParent();
		//activity.getCallingActivity();
		//activity.setResult(RESULT_OK);
		
		// TabHost tabHost = tabActivity.getTabHost();
        //tabActivity.getTabHost();
        // tabActivity.setDefaultTab("Player");
		
		
		
		/*Intent i = new Intent(MusicService.ACTION_URL);
        Uri uri = Uri.parse(MEDIA_PATH + temp2[1]);
        Log.v(getString(R.string.app_name), uri.toString());
        i.setData(uri);
        startService(i);*/
        
		//switchTabSpecial(1);
		  


		
        Intent i = new Intent(this,MusicActivity.class);
        Uri uri = Uri.parse(MEDIA_PATH + temp2[1] + '-' + temp2[0]);
        Log.v(getString(R.string.app_name), uri.toString());
        i.setData(uri);
        
        startActivity(i);
        
        //TabHost tabHost =  (TabHost) this.getParent().findViewById(android.R.id.tabhost); 
        //tabHost.setCurrentTab(2);
		}

	
	public static  String getMediaPath() {
		return file;
	}
	
	//private void switchTabSpecial(long l){                 
		//HelloTabWidget ParentActivity = (HelloTabWidget) this.getParent();                 
		//ParentActivity.switchTabSpecial(1);         
		//}



		
	}
	
	

	
