package com.makewithmoto.boards;

import com.makewithmoto.example.ActivityMOIO;

import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioButton;

/**
 * An example IOIO service. While this service is alive, it will attempt to
 * connect to a IOIO and blink the LED. A notification will appear on the
 * notification bar, enabling the user to stop the service.
 */
public class MOIOService extends IOIOService {
	
	private static final String TAG = "MOIO Service";
	
	@Override
	protected IOIOLooper createIOIOLooper() {
		return new BaseIOIOLooper() {
			private DigitalOutput led_;

			@Override
			protected void setup() throws ConnectionLostException,
					InterruptedException {
				led_ = ioio_.openDigitalOutput(IOIO.LED_PIN);
			}

			@Override
			public void loop() throws ConnectionLostException,
					InterruptedException {
				//led_.write(!ledon.isChecked());
				//led_.write(false);
				//Thread.sleep(500);
				//led_.write(true);
				//Thread.sleep(500);
			}
		};
	}

	
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
		Log.d(TAG, "started");
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
