package com.myexample.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myexample.R;
import com.myexample.handler.IotdHandler;

public class NasaRssActivity extends Activity {

	private static final String ERROR_SETTING_WALLPAPER = "Error Setting Wallpaper";
	private static final String WALLPAPER_SET = "Wallpaper Set";
	private static final String LOADING = "Loading";
	private static final String LOADING_THE_IMAGE_OF_THE_DAY = "Loading the image of the day";
	private static final String URL = "http://www.nasa.gov/rss/image_of_the_day.rss";
	private IotdHandler iotdHandler;
	private Handler handler;
	private Bitmap image;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		handler = new Handler();
		refreshFromFeed();
	}

	public void onRefresh(View view) {
		refreshFromFeed();
	}

	private void refreshFromFeed() {
		final ProgressDialog progressDialog = ProgressDialog.show(this,
				LOADING, LOADING_THE_IMAGE_OF_THE_DAY);

		Runnable job = new Runnable() {
			public void run() {
				getNasaFeedAsynch();
				pushBackFeedToMainThread(progressDialog);
			}
		};
		Thread threadWorker = new Thread(job);
		threadWorker.start();

	}

	private void pushBackFeedToMainThread(final ProgressDialog progressDialog) {

		handler.post(new Runnable() {

			public void run() {
				try {
					resetDisplay(iotdHandler.getTitle(), iotdHandler.getDate(),
							iotdHandler.getUrl(), iotdHandler.getDescription());
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					progressDialog.dismiss();
				}

			}
		});
	}

	
	@TargetApi(16)
	public void onSetWallpaper(View view) {
		Runnable job = new Runnable() {
			public void run() {
				WallpaperManager wallpaperManager = WallpaperManager
						.getInstance(NasaRssActivity.this);
				try {
					wallpaperManager.setBitmap(image);
					wallpaperMessage(WALLPAPER_SET);
				} catch (Exception e) {
					wallpaperMessage(ERROR_SETTING_WALLPAPER);
					e.printStackTrace();
				}
			}
		};
		Thread threadWorker = new Thread(job);
		threadWorker.start();
	}

	
	private void wallpaperMessage(final String message) {
		handler.post(new Runnable() {
			public void run() {
				Toast.makeText(NasaRssActivity.this, message,
						Toast.LENGTH_SHORT);

			}
		});
	}

	private void getNasaFeedAsynch() {
		iotdHandler = new IotdHandler();
		try {
			iotdHandler.processFeed(NasaRssActivity.this, new URL(URL));
			image = getBitmap(iotdHandler.getUrl());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private void resetDisplay(String title, String date, String imageUrl,
			String description) {
		TextView titleView = (TextView) findViewById(R.id.image_title);
		titleView.setText(title);

		TextView dateView = (TextView) findViewById(R.id.image_date);
		dateView.setText(date);

		ImageView imageView = (ImageView) findViewById(R.id.nasa_image);
		imageView.setImageBitmap(getBitmap(imageUrl));

		TextView descriptionView = (TextView) findViewById(R.id.image_desc);
		descriptionView.setText(description);
	}

	private Bitmap getBitmap(String url) {
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url)
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(input);
			input.close();
			connection.disconnect();
			return bitmap;
		} catch (IOException ioe) {
			return null;
		}
	}

}
