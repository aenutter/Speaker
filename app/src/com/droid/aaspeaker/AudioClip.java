package com.droid.aaspeaker;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.net.Uri;
//import android.util.Log;

/**
 * Class to represent meeting objects.  The class has private member variables and public methods 
*/
public class AudioClip {
  private String title;
  private String fileName;
  private String bitRate;
  private String length;
  private int order;
  private Boolean isSelected;
  private Object tag;
  
  /**
   * accessor for meeting start time
   * @return meeting.startTime
   */
  public String getTitle() { return title; }  
  public Object getTag() { return tag; }
  public Boolean getSelected() { return isSelected; }
  /**
   * accessor for meeting start time
   * @return meeting.startTime
   */
  public String getFileName() { return fileName; }
  /**
   * accessor for meeting name 
   * @return meeting.groupName
   */
  public String getLength() { return length; }
  /**
   * accessor for street address for a meeting 
   * @return meeting.street
   */
  public int getOrder() { return order; }
  /**
   * accessor for a meeting's phone number 
   * @return meeting.phone
   */
  
  /**
   * mutator for a meeting's start time 
   */
  public void setOrder(int _order) { order = _order; }
  public void setTag(Object _tag) { tag = _tag; }
  /**
   * mutator for a meeting's type 
   */
  public void setSelected(Boolean _isSelected) { isSelected = _isSelected; }
  /**
   * mutator for a meeting's group name 
   */
  //public void setGroupName(String _groupName) { groupName = _groupName; }
  /**
   * mutator for a meeting's street address 
   */
  //public void setStreet(String _street) { street = _street; }
    
  /**
   * Constructor for a meeting object 
   */
  public AudioClip(String _title, String _fileName, String _bitRate, String _length, Boolean _isSelected) {
    title = _title;
    fileName = _fileName;
	bitRate = _bitRate;
    length = _length;
    isSelected = _isSelected;
  }
  /**
   * Constructor for a meeting object 
   */
  /*public AudioClip() {
	    id = null;
		startTime = null;
		type = null;
	    groupName = null;
	    street = null;
	    phone = null;
	    city = null;
	    state = null;
	    day = null;
	    latitude = null;
	    longitude = null;
	   // MAC = _MAC;
	    fellowship = null;
	    location = null;
	    zipcode = null;
	  }
  /**
   * method to access an object's string members 
   * @return  an objects private members in a string
   */
  //@Override
  //public String toString() {
    //SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");
   // String dateString = sdf.format(date);
    //return dateString + ": " + magnitude + " " + details;
	  //return startTime + " " + day + " " + fellowship + "\nGroup: " + groupName + " " + type + " " + phone + "\nAddress: " + street + " " + city + " " + state + " " + zipcode + "\nNotes: " + location;
  //}
  /**
   * method to access an object's string members 
   * @return  an objects private members in a string
   */
  //public String toStringUnFormatted() {
    //SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");
   // String dateString = sdf.format(date);
    //return dateString + ": " + magnitude + " " + details;
	 // return startTime + " " + day + " " + fellowship + "Group: " + groupName + " " + type + " " + phone + "Address: " + street + " " + city + " " + state + " " + zipcode + " Notes: " + location;
  //}
public boolean isSelected() {
	// TODO Auto-generated method stub
	return isSelected;
}
  
 	
}