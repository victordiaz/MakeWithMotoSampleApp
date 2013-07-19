package com.makewithmoto.example;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/*
 * comment / uncomment according the board you will be using 
 * 
 */

public class LauncherActivity extends FragmentActivity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(0x000000));
				
	}
	
	public void launchActivityBT(View view) {
		startActivity(new Intent(this, ActivityBT.class));
		
	} 
	
	public void launchActivityMAKr(View view) {
		startActivity(new Intent(this, ActivityMAKr.class));
		
	} 
	
	public void launchActivityMOIO(View view) {
		startActivity(new Intent(this, ActivityMOIO.class));
	} 


	@Override
	public void onSaveInstanceState(Bundle state) {
		super.onSaveInstanceState(state);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


}
