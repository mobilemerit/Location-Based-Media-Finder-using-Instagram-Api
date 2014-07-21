package org.aynsoft.javafile;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;

public class Parser {
	
	private final  String KEY_DATA="data";
	private final String KEY_IMAGES="images";
	private final String KEY_IMG_LOW="low_resolution";
	private final String KEY_IMG_HIGH="standard_resolution";
	private final String KEY_IMG_URL="url";
	private final String KEY_ID="id";

	public volatile boolean parsingComplete = true;

	
	
	
	@SuppressLint("NewApi")
	public List<Image> getVideoContent(String URL) {
		String in=fetchJSON(URL);
		List<Image> videoList=new ArrayList<Image>();
		try {
			JSONObject reader = new JSONObject(in);					
			JSONArray resultArray=reader.getJSONArray(KEY_DATA);			
			Log.i("Size",""+resultArray.length());
			if(resultArray.length()>0){
				for(int i=0;i<resultArray.length();i++){
					Image video=new Image();
					JSONObject itemObject=resultArray.getJSONObject(i);		
					JSONObject imgObject=itemObject.getJSONObject(KEY_IMAGES);
					video.setmIconUrl(imgObject.getJSONObject(KEY_IMG_LOW).getString(KEY_IMG_URL));
					video.setsIconUrl(imgObject.getJSONObject(KEY_IMG_HIGH).getString(KEY_IMG_URL));
					videoList.add(video);
				}
			}
			parsingComplete = false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return videoList;

	}
	
	
	public String getLocationID(String URL) {
		String in=fetchJSON(URL);
		String ID=null;
		try {
			JSONObject reader = new JSONObject(in);					
			JSONArray resultArray=reader.getJSONArray(KEY_DATA);				
			if(resultArray.length()>0){
				JSONObject obj=resultArray.getJSONObject(0);
				ID=obj.getString(KEY_ID);
			}
			parsingComplete = false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ID;

	}

	public String fetchJSON(String URL) {
		try {
			URL url = new URL(URL);			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			// Starts the query
			conn.connect();
			InputStream stream = conn.getInputStream();
			String data = convertStreamToString(stream);			
			stream.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	

	static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
}