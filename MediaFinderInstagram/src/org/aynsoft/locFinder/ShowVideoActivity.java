package org.aynsoft.locFinder;

import java.util.List;

import org.aynsoft.adapter.ImageGridAdapter;
import org.aynsoft.javafile.LoadVideos.OnResultLoadedListener;
import org.aynsoft.javafile.LoadVideos;
import org.aynsoft.javafile.Image;
import org.aynsoft.javafile.ViewUtils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

public class ShowVideoActivity extends Activity implements OnResultLoadedListener{

	private ImageGridAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		fetchVideoResult();
	}
	private void fetchVideoResult(){
		LoadVideos loader=new LoadVideos(ShowVideoActivity.this);
		loader.execute(getIntent().getStringExtra("url"));
	}

	@Override
	public void onStartLoading() {
		setContentView(R.layout.loader_layout);
	}

	@Override
	public void onResultLoad(List<Image> result) {
		if(result.size()>0){
			setContentView(R.layout.gridview);
			GridView grid=(GridView)findViewById(R.id.video_gridView);
			grid.setNumColumns(ViewUtils.getInstance().getNoOfColumns(ShowVideoActivity.this));
			adapter=new ImageGridAdapter(ShowVideoActivity.this, result);
			grid.setAdapter(adapter);	
		}else{
			finish();
			Toast.makeText(getBaseContext(), "No Video Found",Toast.LENGTH_SHORT).show();
		}
			
	}
}
