package com.droid.aaspeaker;

import java.util.ArrayList;
import java.util.Queue;

import android.app.Activity;
import android.content.Context;
//import android.util.Log;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
/*
 * open source custom array adapter
 * modified for Movie objects
 * and custom layout 
 * the lack of code indents and formatting 
 * were not present in open source
 */
public class MovieAdapter extends ArrayAdapter<AudioClip> {
private ArrayList<AudioClip> meetings;
private Activity activity;
int key = 0;
Context context;

public MovieAdapter(Context Context, SongsActivity songsActivity, int textViewResourceId, ArrayList<AudioClip> meetings) {
super(songsActivity, textViewResourceId, meetings);
this.meetings = meetings;
activity = songsActivity;
context = Context;

}



public static class ViewHolder{
	public TextView title;
	public TextView fileName;
	public TextView length;
	//protected CheckBox checkbox;
	public TextView day;
	public TextView cityLabel;
	public TextView city;
	public TextView groupLabel;
	public TextView group;
	public TextView detailsLabel;
	public TextView details;
}

// inflate the list view and format
// text and image views 
@Override
public View getView(final int position, View convertView, ViewGroup parent) {
	
View v = convertView;
final ViewHolder holder;
//Log.e("movie adapter", "inside getview");
final AudioClip meeting = meetings.get(position);
if (v == null) {	
LayoutInflater vi =
(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
v = vi.inflate(R.layout.listitem, null);
//Log.e("movie adapter: ", "number meetings: "+String.format("%s", meetings.size()));
holder = new ViewHolder();
//holder.timeLabel = (TextView) v.findViewById(R.id.timeLabel);
holder.title = (TextView) v.findViewById(R.id.title);
holder.length = (TextView) v.findViewById(R.id.length);
//holder.day = (TextView) v.findViewById(R.id.day);
//holder.cityLabel = (TextView) v.findViewById(R.id.cityLabel);
//holder.city = (TextView) v.findViewById(R.id.city);
//holder.groupLabel = (TextView) v.findViewById(R.id.groupLabel);
//holder.group = (TextView) v.findViewById(R.id.group);
//holder.detailsLabel = (TextView) v.findViewById(R.id.detailsLabel);
//holder.details = (TextView) v.findViewById(R.id.details);


//final Boolean[] status  = {false,false,false,false,false,false,false,false,false,false,false,false,false,false};
//holder.checkbox = (CheckBox) v.findViewById(R.id.check);
//holder.checkbox.setBackgroundColor(0xFFFCFCFC);
/*holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        meeting.setTag(holder.checkbox.getTag());
        if (buttonView.isChecked()) {
        	status[position] = true;
        	buttonView.setId(position);
        	meeting.setSelected(true);
        	key += 1;
         	meeting.setOrder(key);
        	Log.e("movie adapter", "# "+String.format("%s", position)+" "+meeting.getTitle()+" added: "+String.format("%s", meeting.getOrder()));
        }
        else {
        	status[position] = false;
        	buttonView.setId(position);
        	meeting.setSelected(false);
        	key -= 1;
         	meeting.setOrder(0);
        	Log.e("movie adapter", "# "+String.format("%s", position)+" "+meeting.getTitle()+" removed: "+String.format("%s", meeting.getOrder()));
        }
        	
       
        
      }
    });*/
v.setTag(holder);
//holder.checkbox.setTag(meeting.getTag());
//holder.checkbox.setChecked(status[position]);
//v.setTag(holder);

}
else
holder=(ViewHolder)v.getTag();
//Log.e("movie adapter: ", "number meetings: "+String.format("%s", meetings.size()));


// list rows alternate color
if (meeting!= null) {

	//holder.timeLabel.setText("Time:     ");
	//holder.timeLabel.setTypeface(holder.timeLabel.getTypeface(), Typeface.BOLD);
	holder.title.setText(meeting.getTitle());
	holder.length.setText(meeting.getLength());
	//holder.checkbox.setChecked(meeting.isSelected());
	//Log.e("movie adapter title: ", meeting.getTitle());
	//holder.dayLabel.setText("Day:     ");
	//holder.dayLabel.setTypeface(holder.dayLabel.getTypeface(), Typeface.BOLD);
	//holder.day.setText(meeting.getDay());
	//holder.cityLabel.setText("City:      ");
	//holder.cityLabel.setTypeface(holder.cityLabel.getTypeface(), Typeface.BOLD);
	//holder.city.setText(meeting.getCity());
	//holder.groupLabel.setText("Group: ");
	//holder.groupLabel.setTypeface(holder.groupLabel.getTypeface(), Typeface.BOLD);
	//holder.group.setText(meeting.getGroupName());
	//holder.detailsLabel.setText("Details: ");
	//holder.detailsLabel.setTypeface(holder.detailsLabel.getTypeface(), Typeface.BOLD);
	//holder.details.setText(meeting.getType());
	//v.setAlpha((float) 0.7);
	//Log.e("movie adapter title: ", movie.getTitle());
	if (position  == 0)
		v.setBackgroundColor(0xFF000000);
	else if ((position % 2) == 0) 
		v.setBackgroundColor(0xFF000000);
	else if ((position % 2) == 1)
		v.setBackgroundColor(0xFF606060);
	
		//NOW YOU CHECK IF THAT POSTION WAS THE ONE CLICKED, IT SETS THE COLOR BLUE
		if(meeting.isSelected())  	  		    
			v.setBackgroundColor(0xff0099cc);
}
return v;
}
@Override
public void notifyDataSetChanged() {
    super.notifyDataSetChanged();  
}
}