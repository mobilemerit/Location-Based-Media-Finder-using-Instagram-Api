package org.aynsoft.javafile;

import android.app.Activity;

import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MarkerPositionListener implements OnMarkerDragListener,OnMapClickListener {

	Activity act;
	public MarkerPositionListener(Activity activity) {
		this.act=activity;
	}
	@Override
	public void onMarkerDrag(Marker arg0) {
	}

	@Override
	public void onMarkerDragEnd(Marker arg0) {
		//startSearch(arg0.getPosition());
		Utils.getInstance().fetchVideoResult(arg0.getPosition(),act);
	}

	@Override
	public void onMarkerDragStart(Marker arg0) {
	}
	
	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		//startSearch(arg0);
		Utils.getInstance().fetchVideoResult(arg0,act);
	}		
	
	/*private void startSearch(LatLng arg0){
		Intent intent =new Intent(act,ShowVideoActivity.class);
		intent.putExtra("url",UrlUtils.getInstance().getUrlForMediaSearch(
				arg0));
		act.startActivity(intent);
	}*/
	
}
