package org.aynsoft.locFinder;

import org.aynsoft.adapter.ImageFlipAdapter;

import se.emilsjolander.flipview.FlipView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class FlipImageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flipview_layout);
		FlipView flipView = (FlipView) findViewById(R.id.flip_view);
		ImageFlipAdapter adapter=new ImageFlipAdapter(FlipImageActivity.this, 
				MainActivity.imageResultList);
		flipView.setAdapter(adapter);
		flipView.flipTo(getIntent().getIntExtra("position",0));
	}
	
	
}
