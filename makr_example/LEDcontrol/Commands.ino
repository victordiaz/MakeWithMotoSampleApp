void getCommands() {

  if (Serial.available()) {

    while (Serial.available()) {
      char inChar = (char)Serial.read(); 
      inputString += inChar;
      if (inChar == '\n') {
        stringComplete = true;
        break;
      }
    }
  }

  if (stringComplete) {
    // this might represent a command send from the 
    // phone side.  After reading it we would
    // parse it and then schedule the action.

    if(inputString.startsWith("LEDON")){
      digitalWrite(led, HIGH);
    } 
    else if(inputString.startsWith("LEDOFF")){
      digitalWrite(led, LOW);
    } 


    inputString = "";
    stringComplete = false;
  }

}
