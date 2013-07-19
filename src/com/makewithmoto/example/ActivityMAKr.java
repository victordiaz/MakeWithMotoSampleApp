package com.makewithmoto.example;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.makewithmoto.boards.MAKr;
import com.makewithmoto.boards.MAKr.MAKrListener;
import com.makewithmoto.makr.fragments.DebugFragment;
import com.makewithmoto.makr.views.PlotView;
import com.makewithmoto.makr.views.PlotView.Plot;

/*
 * write to the board makr.writeSerial(cmd)
 * get data from the board onCommandReceived
 * 
 * 
 */

@SuppressLint("NewApi")
public class ActivityMAKr extends FragmentActivity {

	private static final String TAG = "ExAPP";
	public MAKr makr;
	RadioButton ledon, ledoff;

	private DebugFragment df;
	private boolean f2V = true;


	ActionBar actionBar;
	


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_makr);

		ledon = (RadioButton) findViewById(R.id.ledon);
		ledoff = (RadioButton) findViewById(R.id.ledoff);

		ledon.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					makr.writeSerial("LEDON");
					Toast.makeText(getApplicationContext(), "LEDON",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		ledoff.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					makr.writeSerial("LEDOFF");
					Toast.makeText(getApplicationContext(), "LEDOFF",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		actionBar = getActionBar();
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setLogo(null);
		actionBar.setTitle("MakeWithMoto");

		df = new DebugFragment();
		addFragment(df, R.id.f1);

		final PlotView graphView = (PlotView) findViewById(R.id.plotView1);
		final Plot p1 = graphView.new Plot(Color.RED);
		graphView.addPlot(p1);

		makr = new MAKr(this);
		makr.addListener(new MAKrListener() {


			@Override
			public void onRawDataReceived(byte[] buffer, int size) {

			}

			@Override
			public void onMessageReceived(String cmd, String value) {
				Log.d("received", cmd);
				df.adapter.addRightItem(cmd + " " + value);
				float val = Float.parseFloat(value);
				graphView.setValue(p1, val);

			}
		});
		makr.start();
	}

	@Override
	public void onSaveInstanceState(Bundle state) {
		super.onSaveInstanceState(state);
	}

	@Override
	protected void onResume() {
		super.onResume();
		makr.resume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		makr.pause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		makr.pause();

	}

	public void addFragment(Fragment f, int fragmentPosition) {

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(fragmentPosition, f);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		FrameLayout layout = (FrameLayout) findViewById(R.id.f2);

		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {

			if (f2V) {
				layout.setVisibility(View.GONE);
				f2V = false;
			} else {
				layout.setVisibility(View.VISIBLE);
				f2V = true;
			}
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {

		}

		return super.onKeyDown(keyCode, event);
	}



	

	@Override
	public void onStart() {
		super.onStart();
	}



	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult " + resultCode);
	}

}
