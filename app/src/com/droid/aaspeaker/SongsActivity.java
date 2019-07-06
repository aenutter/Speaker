package com.droid.aaspeaker;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import android.net.Uri;
import android.os.Bundle; 
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View; 
import android.view.WindowManager;
import android.widget.Button; 


import android.app.Activity;
import android.app.Application;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;
import android.app.TabActivity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.widget.AdapterView.OnItemSelectedListener;


public class SongsActivity extends Activity {
    
	public String MEDIA_PATH;
	private static final String FLAG_ACTIVITY_TASK_ON_HOME  = null;
	public String[] songs;
	//private MediaPlayer mp = new MediaPlayer();
	public static String file;
	AudioClip meeting;
    Button square,circle; 
    ArrayList<AudioClip> meetings = new ArrayList<AudioClip>();
    MovieAdapter movieAdapter;
    protected Object mActionMode;
    AudioClip audioClip;
    CheckBox checkbox;
    int key = 0;
    ListView listView;
    //View row;
	int publicIndex;
    View publicView;
    ArrayList<String> playlist = new ArrayList();

	@Override
    public void onCreate(Bundle icicle) {
        
        	super.onCreate(icicle);
        	//setContentView(R.layout.songlist);
        	MEDIA_PATH = getString(R.string.baseURL);
        	 
            setContentView(R.layout.meetingmain);
        	updateSongList();
        	movieAdapter = new MovieAdapter(getBaseContext(), SongsActivity.this, R.layout.listitem,  meetings);
        	movieAdapter.notifyDataSetChanged();		
        	setupList();
        	
        	

        	
    }
	
	void onScrollListener() {
	}
	
	@Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	   MenuInflater inflater = getMenuInflater();
	   inflater.inflate(R.menu.contextual, menu);
	   
	   return true;
	  }

	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	   //Toast.makeText(this, "Just a test", Toast.LENGTH_SHORT).show();
	   return true;
	  }

	  private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

	   // Called when the action mode is created; startActionMode() was called
	   public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	    // Inflate a menu resource providing context menu items
	    MenuInflater inflater = mode.getMenuInflater();
	    // Assumes that you have "contexual.xml" menu resources
	    inflater.inflate(R.menu.contextual, menu);
	    return true;
	   }

	   // Called each time the action mode is shown. Always called after
	   // onCreateActionMode, but
	   // may be called multiple times if the mode is invalidated.
	   public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	    return false; // Return false if nothing is done
	   }

	   // Called when the user selects a contextual menu item
	   public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.addToPlaylist:
	    	key += 1;
         	meeting.setOrder(key);
        	//Log.e("movie adapter", "# "+String.format("%s", item)+" "+meeting.getTitle()+" added: "+String.format("%s", meeting.getOrder()));
        	
        	movieAdapter.getView(publicIndex, publicView, listView).setBackgroundColor(0xff0099cc);
        	//movieAdapter.getView(publicIndex, publicView, listView).setBackgroundColor(0xFFFFFFFF);
	    	//showDialog(MEETING_DIALOG);
	     mode.finish(); // Action picked, so close the CAB
	     return true;
	    case R.id.removeFromPlaylist:
	    	key -= 1;
         	meeting.setOrder(0);
        	//Log.e("movie adapter", "# "+String.format("%s", item)+" "+meeting.getTitle()+" removed: "+String.format("%s", meeting.getOrder()));
        	
        	if (publicIndex  == 0)
        		movieAdapter.getView(publicIndex, publicView, listView).setBackgroundColor(0x80000000);
        	else if ((publicIndex % 2) == 0) 
        		movieAdapter.getView(publicIndex, publicView, listView).setBackgroundColor(0x80000000);
        	else if ((publicIndex % 2) == 1)
        		movieAdapter.getView(publicIndex, publicView, listView).setBackgroundColor(0xFF303030);
        	
        	
        	//showDialog(MEETING_DIALOG);
	     mode.finish(); // Action picked, so close the CAB
	     return true;
	    case R.id.share:
	    	//socialShare();
	     mode.finish(); // Action picked, so close the CAB
	     return true;
	    case R.id.playSelected:
	    	Intent i = new Intent(SongsActivity.this,MusicActivity.class);
	        //Uri uri = Uri.parse(MEDIA_PATH + meetings.get(publicIndex).getFileName() + '-' + meetings.get(publicIndex).getTitle());
	     	         
	        //Log.e(getString(R.string.app_name), uri.toString());
	        int j, k =0;
	        for (j=0; j<meetings.size(); j++) {
	    		if (meetings.get(j).getOrder() > 0)	{
	    			Log.e("playSelected", String.format("Track: %s", j)+" "+meetings.get(j).getTitle()+" file name: "+meetings.get(j).getFileName()+" queue: "+String.format("%s", meetings.get(j).getOrder()));
	    			//Log.e("play selected", "playlist["+String.format("%s", k)+"] = http://192.168.1.117/mp3/" + meetings.get(j).getFileName() + '-' + meetings.get(j).getTitle());
	    			//String string = new String("http://192.168.1.117/mp3/" + meetings.get(j).getFileName() + '-' + meetings.get(j).getTitle());
	    			playlist.add("http://192.168.1.117/mp3/" + meetings.get(j).getFileName() + '-' + meetings.get(j).getTitle());
	    			k++;
	    		
	    		}
	    	}
	        
	        i.putStringArrayListExtra("fileInfo", playlist);
	        //i.setData(uri);
	        
	        startActivity(i);
	     mode.finish(); // Action picked, so close the CAB
	     return true;
	    case R.id.player:
	    	Intent l = new Intent(SongsActivity.this,MusicActivity.class);
	    	l.setAction("DO_NOTHING");
	    	startActivity(l);
	    	mode.finish(); // Action picked, so close the CAB
	    default:
	     return false;
	    }
	   }

	   // Called when the user exits the action mode
	   public void onDestroyActionMode(ActionMode mode) {
	    mActionMode = null;
	   }
	  };

            
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
      super.onOptionsItemSelected(item);
           
      switch (item.getItemId()) {
      case R.id.playSelected:
  		Log.e("on action item clicked", "meetings size: "+String.format("%s", meetings.size()));

	    	int i;
	    	for (i=0; i<meetings.size(); i++) {
	    			Log.e("option item selected", "movie # "+String.format("%s", i)+" title "+meetings.get(i).getTitle()+" queue #: "+String.format("%s", meetings.get(i).getOrder()));
	    	}
	    		//showDialog(MEETING_DIALOG);
	     //mode.finish(); // Action picked, so close the CAB
	     return true;
	    case R.id.share:
	    	//socialShare();
	     //mode.finish(); // Action picked, so close the CAB
	     return true;
	    case R.id.edit:
	    	//editMeetingDialog();
	     //mode.finish(); // Action picked, so close the CAB
	     return true;
	    case R.id.delete:
	    		     //mode.finish(); // Action picked, so close the CAB
	     return true;

	    default:
	     return false;

        }
   
    }*/
	
	/*@Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	   MenuInflater inflater = getMenuInflater();
	   inflater.inflate(R.menu.contextual, menu);
	   
	   return true;
	  }

	 @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	   //Toast.makeText(this, "Just a test", Toast.LENGTH_SHORT).show();
  		Log.e("on options item selected", "meetings size: "+String.format("%s", meetings.size()));

	   return true;
	  }

	  private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

	   // Called when the action mode is created; startActionMode() was called
	   public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	    // Inflate a menu resource providing context menu items
	    MenuInflater inflater = mode.getMenuInflater();
	    // Assumes that you have "contexual.xml" menu resources
	    inflater.inflate(R.menu.contextual, menu);
	    return true;
	   }

	   // Called each time the action mode is shown. Always called after
	   // onCreateActionMode, but
	   // may be called multiple times if the mode is invalidated.
	   public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	    return false; // Return false if nothing is done
	   }

	   // Called when the user selects a contextual menu item
	   public boolean onActionItemClicked(ActionMode mode, MenuItem item) {


	    switch (item.getItemId()) {
	    case R.id.playSelected:
    		Log.e("on action item clicked", "meetings size: "+String.format("%s", meetings.size()));

	    	int i;
	    	for (i=0; i<meetings.size(); i++) {
	    			Log.e("movie adapter", "title "+meeting.getTitle()+" selected: "+String.format("%s", meeting.getOrder()));
	    	}
	    		//showDialog(MEETING_DIALOG);
	     mode.finish(); // Action picked, so close the CAB
	     return true;
	    case R.id.share:
	    	//socialShare();
	     mode.finish(); // Action picked, so close the CAB
	     return true;
	    case R.id.edit:
	    	//editMeetingDialog();
	     mode.finish(); // Action picked, so close the CAB
	     return true;
	    case R.id.delete:
	    		     mode.finish(); // Action picked, so close the CAB
	     return true;

	    default:
	     return false;
	    }
	   }

	public void onDestroyActionMode(ActionMode arg0) {
		// TODO Auto-generated method stub
		
	}
	  };*/
	
	 public void setupList() {
			
			//Log.e("meeting activity meeting count: ", String.format("%s", meetings.size()));
		 listView = (ListView) findViewById(R.id.ListViewId);
			listView.setAdapter(movieAdapter);
			/*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1, int index,
						long arg3) {


				
				//String temp =  songs[index];
				//String[] temp2 = temp.split("-", 4);
				//file = temp2[0];
				
		        Intent i = new Intent(SongsActivity.this,MusicActivity.class);
		        Uri uri = Uri.parse(MEDIA_PATH + meetings.get(index).getFileName() + '-' + meetings.get(index).getTitle());
		        //Log.e(getString(R.string.app_name), uri.toString());
		        int j;
		        for (j=0; j<meetings.size(); j++) {
		    		if (meetings.get(j).getOrder() > 0)	
		    			i.setData(uri);
		    			//Log.e("movie adapter", String.format("Track: %s", j)+" "+meetings.get(j).getTitle()+" queue: "+String.format("%s", meetings.get(j).getOrder()));
		    	}
		        
		        //i.setData(uri);
		        
		        startActivity(i);
		        
		}});*/
			
			//listView = (ListView) findViewById(R.id.ListViewId);
			//listView.setAdapter(movieAdapter);
        	
        	listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

	            public boolean onItemLongClick(AdapterView<?> listview, View view,
	                    int index, long arg3) {
	            	movieAdapter.getView(index, view, listView).setBackgroundColor(0xff0099cc);
					meeting.setSelected(true);//WHIT THIS YOU KEEP TRACK OF THE ITEM THAT WAS CLICKED
	            	
	            	publicView = view;
	            	publicIndex = index;
	            	
	            	meeting = meetings.get(index);

	            	 if (mActionMode != null) {
	  	  		       return false;
	  	  		      }

	  	  		      // Start the CAB using the ActionMode.Callback defined above
	  	  		      mActionMode = startActionMode(mActionModeCallback);
	  	  		      listview.setSelected(true);
	  	  		      return true;
	  	  		     }
	           
	}); 

			
			
}

	    public void updateSongList() {
    	/*File home = new File(MEDIA_PATH);
		if (home.listFiles( new Mp3Filter()).length > 0) {
    		for (File file : home.listFiles( new Mp3Filter())) {
    			songs.add(file.getName());
    		}*/
    		Resources res = getResources();
    		songs = (res.getStringArray(R.array.big_book));
    		//String[] parameter = songs.toString().split("-", 3);
    		String[] parameter = null;
    		int index = songs.length;
    		int i = 0;
			while ( i  < index) {
				parameter = songs[i].split("-",4);
				meeting = new AudioClip(parameter[0], parameter[1], parameter[2], parameter[3], false);
				meetings.add(meeting);
				//Log.e("songs activity", "#: "+String.format("%s", i)+" file name: "+meeting.getFileName() + " title: "+meeting.getTitle());
				i++;
    		}
    		//Log.e(getString(R.string.app_name), "meetings size: "+String.format("%s", index));
    		

		}    	
    //}
	   
	    
   
    public void onStop() {
    	super.onStop();
    	//mp.stop();
    	//mp.release();
    	
    }
    
    protected void onPause() {         
    	super.onPause();
    	Log.e("songs activity", "on pause");
    	//mp.stop();
    	//mp.release();
    }
    
    protected void onResume() {         
    	super.onResume();
    	Log.e("songs activity", "on resume");
    	//setupList();
    	//mp.stop();
    	//mp.release();
    }

	
	public static  String getMediaPath() {
		return file;
	}
	
	
}
	
	

	
