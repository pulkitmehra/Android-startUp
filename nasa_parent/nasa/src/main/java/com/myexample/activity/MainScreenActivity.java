package com.myexample.activity;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.myexample.R;
import com.myexample.adapter.ImageViewAdapter;

@ContentView(R.layout.main_screen)
public class MainScreenActivity extends RoboActivity implements
		OnItemClickListener {

	@InjectView(R.id.gridViewScreen)
	protected GridView gridView;

	@Inject
	protected ImageViewAdapter imageViewAdapter;

	static List<ExampleItems> exampleItems;

	static {
		exampleItems = new ArrayList<ExampleItems>();
		exampleItems.add(new ExampleItems("Nasa App", R.drawable.nasa_icon));
		exampleItems
				.add(new ExampleItems("MyNotes App", R.drawable.notes_icon));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		imageViewAdapter.setExamples(exampleItems);
		gridView.setAdapter(imageViewAdapter);
		gridView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		CharSequence text = ((TextView) view.findViewById(R.id.grid_item_label))
				.getText() + " Selected!";
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
				.show();
	}

	public static final class ExampleItems {

		private final String exampleName;
		private final Integer iconId;

		public ExampleItems(String exampleName, Integer iconId) {
			super();
			this.exampleName = exampleName;
			this.iconId = iconId;
		}

		public String getExampleName() {
			return exampleName;
		}

		public Integer getIconId() {
			return iconId;
		}

	}

}
