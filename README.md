MakeWithMotoSampleApp
=====================

_This app is currently under construction, but is in a stable/usable state._

To get started using the MOIO board, open the Make With Moto! app and select MOIO. The MOIO board should power on (red LED should light). The MOIO will either be paired over bluetooth or not. If paired, you should see a 'READY!!!' toast towards the bottom of the app after no more than 15 seconds. If not paired, keep the app open by hitting the 'home' button, then goto the Android's bluetooth setup utility and pair with the MOIO (the MOIO should show up as 'IOIO(xx:xx)'). The passcode to pair is '4545'. 

Once paired the app displays 4 different interactions, based on 4 different hardware interfaces. From the top of the app to the bottom:

* **LED Output:** The radio buttons select if the on board yellow status LED is ON or OFF.

* **Analog Input:** The graph displays the analog voltage value on pin 31. Try touching **pin 31** with your finger and you should see the value drop to 0V. (Keep in mind, this pin is 0-3.3V tollerant).  

* **Pushbutton Input:** On **pin 1**, connect a momentary push button between pin 1 and GND. When the button is hit, you should see 'Active!' displayed next to 'Pushbutton:'.

* **PWM Output:** The slide bar controls the PWM duty cycle between 1ms and 2ms on **pin 2**. Connect a servo control line to this pin (as well as power, 5V, and GND to the servo) to control the sweep of the servo.

For more information on hardware implementations, see the [IOIO wiki](https://github.com/ytai/ioio/wiki).
