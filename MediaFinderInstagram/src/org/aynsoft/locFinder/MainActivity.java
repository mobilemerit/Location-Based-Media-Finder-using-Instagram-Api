package org.aynsoft.locFinder;

import java.util.ArrayList;
import java.util.List;

import org.aynsoft.adapter.ImageGridAdapter;
import org.aynsoft.javafile.LoadVideos.OnResultLoadedListener;
import org.aynsoft.javafile.MarkerPositionListener;
import org.aynsoft.javafile.Utils;
import org.aynsoft.javafile.Image;
import org.aynsoft.javafile.ViewUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements OnClickListener,OnResultLoadedListener, OnItemClickListener {
	
  
   private LinearLayout mapLayout,indicatorLayout;
   private TextView txtIndicator,txtProgressIndicator;
   private ImageView imgIndicator;
   private GridView gridView;
   private ProgressBar barIndicator;
   
   public static List<Image> imageResultList;
   private ImageGridAdapter adapter;
   private GoogleMap googleMap;
   private  Location loc;
   private static final String MSG_LOADING="loading....";
   private static final String MSG_NOPIC="Oops! no pic found";
   private static final String ERROR_MAP_ERROR="Sorry! unable to create maps";
   
   
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
        
        barIndicator=(ProgressBar)findViewById(R.id.barIndicator);
        txtProgressIndicator=(TextView)findViewById(R.id.txtProgressIndicator);        
        mapLayout=(LinearLayout)findViewById(R.id.mapLayout);
        indicatorLayout=(LinearLayout)findViewById(R.id.indicatorLayout);
        txtIndicator=(TextView)findViewById(R.id.txtIndicator);
        imgIndicator=(ImageView)findViewById(R.id.imgIndicator);
        
        gridView =(GridView)findViewById(R.id.video_gridView);
		gridView.setNumColumns(ViewUtils.getInstance().getNoOfColumns(MainActivity.this));
		imageResultList=new ArrayList<Image>();
		adapter=new ImageGridAdapter(MainActivity.this,imageResultList);
		gridView.setAdapter(adapter);	
		gridView.setOnItemClickListener(this);
        
        indicatorLayout.setOnClickListener(this);
        
        try {
            initilizeMap(); 
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
    } 
   
    
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            Location cLoc=getCurrentLocation();
            if(cLoc!=null){
            	//Toast.makeText(getBaseContext(), "NotNull",Toast.LENGTH_SHORT).show();
            	LatLng latLng = new LatLng(cLoc.getLatitude(), cLoc.getLongitude());
            	MarkerOptions markerOptions = new MarkerOptions();
            	markerOptions.position(latLng);
            	markerOptions.draggable(true);
            	googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 4));
            	googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            	googleMap.addMarker(markerOptions);
            	googleMap.setOnMarkerDragListener(new MarkerPositionListener(MainActivity.this));
            	//Load Videos from Current Position
            	Utils.getInstance().fetchVideoResult(latLng,MainActivity.this);
            }else{
            	googleMap.setOnMapClickListener(new MarkerPositionListener(MainActivity.this));
            }
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                       ERROR_MAP_ERROR , Toast.LENGTH_SHORT).show();
            }
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
    
    public  Location getCurrentLocation(){
    	
		LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		String provider=manager.getBestProvider(new Criteria(),true);
		if(provider!=null){	
			loc=manager.getLastKnownLocation(provider);
			manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, 
					new LocationListener() {						
						@Override
						public void onStatusChanged(String provider, int status, Bundle extras) {
						}
						
						@Override
						public void onProviderEnabled(String provider) {
						}
						
						@Override
						public void onProviderDisabled(String provider) {
						}
						
						@Override
						public void onLocationChanged(Location location) {
							loc=location;
							initilizeMap();
						}
					});
			
			return loc;
		}
		return null;
	}


	
    
    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.indicatorLayout:
			toggleMapVisibility();
			break;

		default:
			break;
		}	
	}	
    
    private void toggleMapVisibility(){
    	int VISIBILITY=mapLayout.getVisibility();
    	if(VISIBILITY==LinearLayout.GONE){
    		mapLayout.setVisibility(LinearLayout.VISIBLE);
    		txtIndicator.setText("Hide");
    		imgIndicator.setImageDrawable(getResources().getDrawable(R.drawable.ic_top));
    	}else{
    		mapLayout.setVisibility(LinearLayout.GONE);
    		txtIndicator.setText("Show");
    		imgIndicator.setImageDrawable(getResources().getDrawable(R.drawable.ic_bottom));
    	}
    }


	@Override
	public void onStartLoading() {	
		imageResultList.clear();
		adapter.notifyDataSetChanged();
		txtProgressIndicator.setText(MSG_LOADING);
		txtProgressIndicator.setVisibility(TextView.VISIBLE);
		barIndicator.setVisibility(ProgressBar.VISIBLE);
	}

	@Override
	public void onResultLoad(List<Image> result) {	
		if(result.isEmpty()){
			txtProgressIndicator.setText(MSG_NOPIC);			
		}else{
			txtProgressIndicator.setVisibility(TextView.INVISIBLE);
			imageResultList.addAll(result);
			adapter.notifyDataSetChanged();
		}
		barIndicator.setVisibility(ProgressBar.INVISIBLE);		
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(this,FlipImageActivity.class);
		intent.putExtra("position",arg2);
		startActivity(intent);
	}
	
	
}
