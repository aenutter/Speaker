package com.droid.aaspeaker;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * @author Sapan
 */
public class MusicActivity extends Activity implements MediaPlayerControl, SeekBar.OnSeekBarChangeListener {

	MediaController ctrl;
	ImageView songPicture;
	String mUrl;
	Resources res;
	TextView songNameView;
	TextView musicCurLoc;
	TextView musicDuration;
	SeekBar musicSeekBar;
	ToggleButton playPauseButton;
	ImageButton shuffleButton;
	ImageButton stopButton; 
	
	String mSongPicUrl = null;
	private String mSongTitle = null;
	protected boolean musicThreadFinished = false;
	ArrayList<String> playlist = new ArrayList<String>();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music);

		//shuffleButton = (ImageButton) findViewById(R.id.shufflebutton);

		res = getResources(); // Resource object to get Drawables

		//songPicture = (ImageView) findViewById(R.id.thumbnail); // new ImageView(this);
		songNameView = (TextView) findViewById(R.id.songName);

		ctrl = new MediaController(this);
		ctrl.setMediaPlayer(this);
		ctrl.setAnchorView(songPicture);

		musicCurLoc = (TextView) findViewById(R.id.musicCurrentLoc);
		musicDuration = (TextView) findViewById(R.id.musicDuration);
		musicSeekBar = (SeekBar) findViewById(R.id.musicSeekBar);
		playPauseButton = (ToggleButton) findViewById(R.id.playPauseButton);
		stopButton = (ImageButton) findViewById(R.id.stopButton);
		musicSeekBar.setOnSeekBarChangeListener(this);

		playPauseButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// Perform action on clicks
				if (playPauseButton.isChecked()) { // Checked -> Pause icon visible
					start();
				} else { // Unchecked -> Play icon visible
					pause();
				}
			}
		});

		stopButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startService(new Intent("STOP"));
				musicDuration.setText( getAsTime(0));
				musicCurLoc.setText(getAsTime(0));
				songNameView.setText("No Audio File");
				playPauseButton.setVisibility(0x00000004);
				stopButton.setVisibility(0x00000004);
				return;
			}
		});
		
		
		if (MusicService.getInstance() != null) {

			Intent intent = getIntent();
			if (intent.getAction().equals("DO_NOTHING")) 
				return;
			playlist = new ArrayList<String>(intent.getStringArrayListExtra("fileInfo"));
			String temp = playlist.get(0);
			String[] temp2 = temp.split("-", 2);
			Log.e("music activity line 96", "filename: "+temp2[0]+" title: "+temp2[1]);
			
			//intent.getData();
			//String temp = intent.getDataString();
			
			if (temp != null) {
				temp2 = temp.split("-", 2);
				MusicService.setSong(temp2[0], temp2[1], null);
				MusicService.getInstance().restartMusic();
				//Log.e("onCreate", "Line 117");
				//Log.e("MusicActivity", "Playing from URL/path: " + temp2[0]);
	        	//Log.e("MusicActivity", "Playing file: " + temp2[1]);
				songNameView.setText(temp2[1]);
			}
		}


		new Thread(new Runnable() {
			
			public void run() {
				int currentPosition = 0;
				while (!musicThreadFinished) {
					try {
						Thread.sleep(1000);
						currentPosition = getCurrentPosition();
					} catch (InterruptedException e) {
						return;
					} catch (Exception e) {
						return;
					}
					final int total = getDuration();

					final String totalTime = getAsTime(total);
					final String curTime = getAsTime(currentPosition);

					musicSeekBar.setMax(total);
					musicSeekBar.setProgress(currentPosition);
					musicSeekBar.setSecondaryProgress(getBufferPercentage());
					runOnUiThread(new Runnable() {
						
						public void run() {
							if (isPlaying()) {
								if (!playPauseButton.isChecked()) {
									playPauseButton.setChecked(true);
								}
							} else {
								if (playPauseButton.isChecked()) {
									playPauseButton.setChecked(false);
								}
							}
							musicDuration.setText(totalTime);
							musicCurLoc.setText(curTime);
						}
					});
					if (MusicService.isStopped())
						musicThreadFinished = true;
				}
			}
		}).start();

		if (MusicService.getInstance() != null) {
			
			
			Intent intent = getIntent();
			
			playlist = new ArrayList(intent.getStringArrayListExtra("fileInfo"));
			String temp = playlist.get(0);
			String[] temp2 = temp.split("-", 2);
			Log.e("music activity line 163", "filename: "+temp2[0]+" title: "+temp2[1]);

			if (temp != null) {
				temp2 = temp.split("-", 2);
				MusicService.setSong(temp2[0], temp2[1], null);
				MusicService.getInstance().restartMusic();
				//Log.e("onCreate", "Line 180");
				//Log.e("MusicActivity", "Playing from URL/path: " + temp2[0]);
	        	//Log.e("MusicActivity", "Playing file: " + temp2[1]);
				songNameView.setText(temp2[1]);
			}
			return;
		} 
		else {
			
			Intent intent = getIntent();
			playlist = new ArrayList(intent.getStringArrayListExtra("fileInfo"));
			String temp = playlist.get(0);

			String[] temp2 = temp.split("-", 2);
			Log.e("music activity line 188", "filename: "+temp2[0]+" title: "+temp2[1]);
			songNameView.setText(temp2[1]);
		}

		
		Intent intent = getIntent();
		
		playlist = new ArrayList(intent.getStringArrayListExtra("fileInfo"));
		int i;
		for (i=0; i<playlist.size(); i++) {
			Log.e("music activity", "playlist size: "+playlist.size());
			String temp = playlist.get(i);
			String[] temp2 = temp.split("-", 2);
			Log.e("music activity line 201", "filename: "+temp2[0]+" title: "+temp2[1]);

			MusicService.setSong(temp2[0], temp2[1], null);
			mSongTitle = temp2[1];
			songNameView.setText(temp2[1]);
				
			new Thread(new Runnable() {
				public void run() {
					startService(new Intent("PLAY"));
				}
			}).start();
		}
	}

	@SuppressWarnings("boxing")
	protected String getAsTime(int t) {
		return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toSeconds(t) / 60, TimeUnit.MILLISECONDS.toSeconds(t) - TimeUnit.MILLISECONDS.toSeconds(t) / 60
				* 60);
	}

	/*private void shuffleNext() {
		String shuffleUrl = R.string.host + "services/music.php?v=2.0&a=shuffle.next";
		String musicJson = res.getString(R.string.json);
		//Log.i("MusicActivity", musicJson);
		JSONObject musicDetails = null;
		try {
			musicDetails = new JSONObject(musicJson);
		} catch (JSONException e) {
			//Log.e("MusicActivity", "Error parsing JSON");
			// e.printStackTrace();
		}
		try {
			if (MusicService.getInstance() != null && musicDetails != null) {
				mSongTitle = musicDetails.getString("name");
				mSongPicUrl = musicDetails.getString("pic_url");
				MusicService.setSong(musicDetails.getString("link"), mSongTitle, mSongPicUrl);
				Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(mSongPicUrl).getContent());
				songPicture.setImageBitmap(bitmap);
				
				Intent intent = getIntent();
				ArrayList<String> playlist = new ArrayList(intent.getStringArrayListExtra("fileInfo"));
				//ArrayList<String> playlist = intent.getStringArrayListExtra("fileInfo");
				String temp = playlist.get(0);
				Log.e("music activity line 287", temp);
				
				//Intent intent = getIntent();
				//intent.getData();
				//String temp = intent.getDataString();
				String[] temp2 = temp.split("-", 2);
				MusicService.setSong(temp2[0], temp2[1], null);
				//mUrl = "http://www.aenutter.com/" + MusicDroid.getMediaPath();
				//Log.e("onCreate", "Line 248");
		        //Log.e("MusicActivity", "Playing from URL/path: " + temp2[0]);
		        //Log.e("MusicActivity", "Playing file: " + temp2[1]);
				//MusicService.setSong(mUrl, MusicDroid.getMediaPath(), null);
				//MusicService.setSong(intent.getDataString(), MusicDroid.getMediaPath(), null);
				songNameView.setText(temp2[1]);

			}
		} catch (JSONException e) {
			//Log.e("MusicActivity", "Link not found");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			//Log.e("MusicActivity", "Malformed pic url");
			e.printStackTrace();
		} catch (IOException e) {
			//Log.e("MusicActivity", "Cant read picture at pic_url");
			e.printStackTrace();
		}
	}*/

	// 
	// public boolean onTouchEvent(MotionEvent event) {
	// // the MediaController will hide after 3 seconds - tap the screen to make it appear again
	// ctrl.show();
	// return false;
	// }

    protected void onPause() {         
    	super.onPause();
    	Log.e("Music activity", "on pause");
    	//mp.stop();
    	//mp.release();
    }
    
    protected void onResume() {         
    	super.onResume();
    	Log.e("Music activity", "on resume");
    	//setupList();
    	//mp.stop();
    	//mp.release();
    }
	public boolean canPause() {
		return true;
	}

	
	public boolean canSeekBackward() {
		return true;
	}

	
	public boolean canSeekForward() {
		return true;
	}

	
	public int getBufferPercentage() {
		if (MusicService.getInstance() != null) {
			return MusicService.getInstance().getBufferPercentage();
		}
		return 0;
	}

	
	public int getCurrentPosition() {
		if (MusicService.getInstance() != null) {
			return MusicService.getInstance().getCurrentPosition();
		}
		return 0;
	}

	
	public int getDuration() {
		if (MusicService.getInstance() != null) {
			return MusicService.getInstance().getMusicDuration();
		}
		return 0;
	}

	
	public boolean isPlaying() {
		if (MusicService.getInstance() != null) {
			return MusicService.getInstance().isPlaying();
		}
		return false;
	}

	
	public void pause() {
		if (MusicService.getInstance() != null) {
			MusicService.getInstance().pauseMusic();
		}
	}

	
	public void seekTo(int pos) {
		if (MusicService.getInstance() != null) {
			MusicService.getInstance().seekMusicTo(pos);
		}

	}

	
	public void start() {
		if (MusicService.getInstance() != null) {
			MusicService.getInstance().startMusic();
		}
	}

	
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if (fromUser) {
			seekTo(progress);
		}
	}

	
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}
}
