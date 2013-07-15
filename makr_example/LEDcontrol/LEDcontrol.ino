/*
  Blink
 Turns on an LED on or off
 */

// Pin 13 has an LED connected on most Arduino boards.
// give it a name:
int led = 13;
int ledBig = 12;
int lightSensor = 8;

String inputString = "";         // a string to hold incoming data
boolean stringComplete = false;  // whether the string is complete


// the setup routine runs once when you press reset:
void setup() {
  Serial.begin(9600);

  // initialize the digital pin as an output.
  pinMode(led, OUTPUT);
  digitalWrite(led, LOW);

  // led 
  pinMode(ledBig, OUTPUT);
  digitalWrite(ledBig, LOW);
  pinMode(lightSensor, INPUT);

  //Serial.println("hello");
  //Serial.flush();

}

int sensorValue = 0; 
int count = 0;

// the loop routine runs over and over again forever:
void loop() {
  sensorValue = analogRead(lightSensor);    
  String msg = "pr::";
  msg = msg + sensorValue;

  Serial.println(msg);  
  Serial.flush();
  
  
  getCommands(); 
  
  
  delay(100);

}


