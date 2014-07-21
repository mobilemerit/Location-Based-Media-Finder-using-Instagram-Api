package org.aynsoft.adapter;

import java.util.List;

import org.aynsoft.javafile.Image;
import org.aynsoft.locFinder.R;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aynsoft.lazy.ImageLoader;

public class ImageGridAdapter extends BaseAdapter {

	public List<Image> itemsList;
	Activity a;
	LayoutInflater inflater;

	ImageLoader loader;

	public ImageGridAdapter(Activity activity, List<Image> movieInfoList) {
		this.a = activity;
		itemsList = movieInfoList;
		inflater = LayoutInflater.from(activity);		
		loader=new ImageLoader(a,100);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return itemsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.griditem_layout,
					parent, false);
		
			holder.poster = (ImageView) convertView.findViewById(R.id.grid_img);
			convertView.setTag(holder);
		}
			holder = (ViewHolder) convertView.getTag();
			try {		
				String img_url=itemsList.get(position).getmIconUrl();			
				Log.i("IMG_URL",""+img_url);				
				ImageView img=holder.poster;
				loader.DisplayImage(img_url,img);			
				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		
		return convertView;
	}

	static class ViewHolder {
	
		ImageView poster;
	}	
}
