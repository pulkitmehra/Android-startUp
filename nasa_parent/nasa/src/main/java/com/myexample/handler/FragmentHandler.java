package com.myexample.handler;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.inject.Singleton;

@Singleton
public class FragmentHandler {

	public void addFragment(Fragment fragment, int containerId, FragmentActivity fragmentActivity) {
		FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		fragmentTransaction.add(containerId, fragment);
		fragmentTransaction.commit();
	}

	public void addFragment(Fragment fragment, int containerId, String tag, FragmentActivity fragmentActivity) {
		FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		fragmentTransaction.add(containerId, fragment, tag);
		fragmentTransaction.commit();
	}

	public void replaceAndStackFragment(Fragment fragment, int containerId, FragmentActivity fragmentActivity) {
		FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(containerId, fragment);
		fragmentTransaction.commit();
	}

	public static void finishFragment(FragmentActivity parentActivity, Fragment removefragment) {
		FragmentManager fragmentManager = parentActivity.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.remove(removefragment);
		fragmentTransaction.commit();
	}

}
