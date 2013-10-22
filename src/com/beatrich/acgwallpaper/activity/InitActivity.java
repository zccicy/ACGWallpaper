package com.beatrich.acgwallpaper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.beatrich.acgwallpaper.R;

public class InitActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_init);
		startActivity(new Intent(this, WallpaperActivity.class));

	}

}
