package org.aynsoft.javafile;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;

public class LoadVideos extends AsyncTask<String,Void, List<Image>>{

	Activity act;
	OnResultLoadedListener listener;	
	
	public LoadVideos(Activity activity) {
		this.act=activity;
		listener=(OnResultLoadedListener)act;
	}
	
	@Override
	protected void onPreExecute() {	
		listener.onStartLoading();
		super.onPreExecute();
	}
	
	@Override
	protected List<Image> doInBackground(String... params) {
		// TODO Auto-generated method stub
		Parser parser=new Parser();	
		//String locID=parser.getLocationID(params[0]);
		return parser.getVideoContent(params[0]);
		
	}
	
	@Override
	protected void onPostExecute(List<Image> result) {
		// TODO Auto-generated method stub
		//Toast.makeText(act, "Complete",Toast.LENGTH_SHORT).show();
		if(result!=null){
			listener.onResultLoad(result);	
		}			
		super.onPostExecute(result);
	}
	
	public interface OnResultLoadedListener{
		public void onStartLoading();
		public void onResultLoad(List<Image> result);
		
	}
	
}
