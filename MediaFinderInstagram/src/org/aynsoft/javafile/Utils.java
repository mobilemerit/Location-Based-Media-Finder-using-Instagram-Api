package org.aynsoft.javafile;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class Utils {
	
	private static final String APIKEY="2f8a844330444c97a6d71cf882e19964";

	private static Utils utils;
	private  Utils() {
		
	}
	
	public static Utils getInstance(){
		if(utils==null){
			utils=new Utils();
			return utils;
		}
		return utils;
	}		
	
	public String getUrlForMediaSearch(LatLng latlng){
		String url="https://api.instagram.com/v1/media/search?" +
				"lat=" +latlng.latitude+
				"&lng=" +latlng.longitude+
				"&client_id="+APIKEY;
		Log.i("Search Url",""+url);
		return url;
	}	
	
	public  void fetchVideoResult(LatLng arg0,Activity activity){
		LoadVideos loader=new LoadVideos(activity);
		loader.execute(Utils.getInstance().getUrlForMediaSearch(
				arg0));
	}
 }
