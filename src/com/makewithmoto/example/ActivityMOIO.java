package com.makewithmoto.example;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import ioio.lib.api.PwmOutput;
import ioio.lib.api.AnalogInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.DigitalInput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.makewithmoto.makr.views.PlotView;
import com.makewithmoto.makr.views.PlotView.Plot;

/*
 * Example using the MOIO board 
 */

@SuppressLint("NewApi")
public class ActivityMOIO extends IOIOActivity {

	private static final String MAKR_ENABLE = "/sys/class/makr/makr/5v_enable";
	
	private static final String TAG = "ExAPP";
	RadioButton ledon, ledoff;

	TextView buttonread;
	SeekBar pwmcontrol;
	//private DebugFragment df;
	private boolean f2V = true;

	ActionBar actionBar;

	PlotView graphView;
	Plot p1;

	int seekchange = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ioio);
		
		// turn the MOIO on
		enable(true);
		
		pwmcontrol = (SeekBar) findViewById(R.id.seekBar1);
		
		pwmcontrol.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					seekchange = progress;	
				}
	
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
	
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
		});
		
		buttonread = (TextView) findViewById(R.id.pushbuttonTextView);
		
		ledon = (RadioButton) findViewById(R.id.ledon);
		ledoff = (RadioButton) findViewById(R.id.ledoff);

		actionBar = getActionBar();
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setLogo(null);
		actionBar.setTitle("MakeWithMoto");

		//df = new DebugFragment();
		//addFragment(df, R.id.f1);

		graphView = (PlotView) findViewById(R.id.plotView1);
		
		p1 = graphView.new Plot(Color.RED);
		graphView.addPlot(p1);
		
		//TODO fix this 
		graphView.setLimits(-10, 10);

	}

	class Looper extends BaseIOIOLooper {
		
		/* create instances for each interface */
		private DigitalOutput led_;
		private AnalogInput analogin_;
		private DigitalInput pushbutton_;
		private PwmOutput pwm_;

		/**
		 * Called every time a connection with IOIO has been established.
		 * Typically used to open pins.
		 */
		@Override
		protected void setup() throws ConnectionLostException {
			analogin_ = ioio_.openAnalogInput(31);
			led_ = ioio_.openDigitalOutput(0, true); // start with the LED off
			pushbutton_ = ioio_.openDigitalInput(1, DigitalInput.Spec.Mode.PULL_UP);
			pwm_ = ioio_.openPwmOutput(2, 100);
			//pwm_.setDutyCycle(0);
			
		}

		/**
		 * Called repetitively while the IOIO is connected.
		 */
		@Override
		public void loop() throws ConnectionLostException, InterruptedException {
			
			// DIGITAL OUT - blinks LED on button press
			led_.write(!ledon.isChecked());
			
			// ANALOG IN - reads a voltage between 0 and 3.3V
			final float volts = analogin_.getVoltage();
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					graphView.setValue(p1, volts);					
				}
			});
			//Log.d(TAG, "analog volts " + volts);
			
			// DIGITAL IN - reads a push button press
			boolean value = pushbutton_.read();
			String pushbuttontxt;
			if (!value) {
				pushbuttontxt = getString(R.string.pushbuttonstring) + " active!";
			} else {
				pushbuttontxt = getString(R.string.pushbuttonstring);
			}
			setText(pushbuttontxt);
			
			// PWM OUT - outputs pulse width modulated waveform
			int pwmvalue = (1000 + (seekchange*10));
			pwm_.setPulseWidth(pwmvalue); //set a number between 1000 and 2000
			//Log.d(TAG, "seek " + pwmvalue);
			
			// this slows down the loop to save process time
			/*try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}*/
			
		}
	}
	
	// needed to print button read
	private void setText(final String str1) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				buttonread.setText(str1);
			}
		});
	}
	
	/*
	 * Turn on or off the device
	 */
    public void enable(boolean value)
    {
        BufferedWriter writer = null;
        try {
            FileOutputStream fos = new FileOutputStream(MAKR_ENABLE);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            writer = new BufferedWriter(osw);
            if (value)
                writer.write("on\n");
            else
                writer.write("off\n");
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (writer != null) {
                try { writer.close(); } catch (IOException e) { }
            }
        }
    }


	/**
	 * A method to create our IOIO thread.
	 */
	protected IOIOLooper createIOIOLooper() {
		return new Looper();
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