package com.myexample.adapter;

import java.util.List;

import roboguice.inject.ContextSingleton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.myexample.R;
import com.myexample.activity.MainScreenActivity.ExampleItems;

@ContextSingleton
public class ImageViewAdapter extends BaseAdapter {

	@Inject
	protected LayoutInflater layoutInflater;

	protected List<ExampleItems> examples;

	public ImageViewAdapter() {
		super();
	}

	public ImageViewAdapter(List<ExampleItems> examples) {
		super();
		this.examples = examples;
	}

	@Override
	public int getCount() {
		return examples.size();
	}

	@Override
	public Object getItem(int position) {
		return examples.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(
					R.layout.main_screen_example_items, parent, false);
		}

		TextView gridItem = (TextView) convertView
				.findViewById(R.id.grid_item_label);
		ExampleItems exampleItems = examples.get(position);
		gridItem.setText(exampleItems.getExampleName());

		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.grid_item_image);
		imageView.setImageResource(exampleItems.getIconId());

		return convertView;
	}

	public void setExamples(List<ExampleItems> examples) {
		this.examples = examples;
	}

}
