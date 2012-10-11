package com.myexample.activity;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
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

	private static final String TODO_APP = "Todo App";
	protected static final String NASA_APP = "Nasa App";
	protected static final String MY_NOTES_APP = "MyNotes App";

	@InjectView(R.id.gridViewScreen)
	protected GridView gridView;

	@Inject
	protected ImageViewAdapter imageViewAdapter;

	static protected List<ExampleItems> exampleItems;

	static {
		exampleItems = new ArrayList<ExampleItems>();
		exampleItems.add(new ExampleItems(NASA_APP, R.drawable.nasa_icon,
				NasaRssActivity.class));
		exampleItems.add(new ExampleItems(MY_NOTES_APP, R.drawable.notes_icon,
				ListViewActivity.class));
		exampleItems.add(new ExampleItems(TODO_APP, R.drawable.todo_icon, ToDoListActivity.class));
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
				.getText()
				+ " Selected!"
				+ " Activity Class is :"
				+ exampleItems.get(position).getActivityClass().getSimpleName();
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
				.show();
		Intent intent = new Intent(this, exampleItems.get(position)
				.getActivityClass());
		startActivity(intent);
	}

	@SuppressWarnings("rawtypes")
	public static final class ExampleItems {

		private final String exampleName;
		private final Integer iconId;
		private final Class activityClass;

		public ExampleItems(String exampleName, Integer iconId,
				Class activityClass) {
			super();
			this.exampleName = exampleName;
			this.iconId = iconId;
			this.activityClass = activityClass;
		}

		public String getExampleName() {
			return exampleName;
		}

		public Integer getIconId() {
			return iconId;
		}

		public Class getActivityClass() {
			return activityClass;
		}
	}
}
