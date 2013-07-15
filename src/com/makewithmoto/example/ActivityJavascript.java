package com.makewithmoto.example;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.JavaAdapter;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ActivityJavascript extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		doit("var Toast = Packages.android.widget.Toast; "
				+ "Toast.makeText(ac, \"qq\", Toast.LENGTH_LONG).show(); " +
				"" +
				"setInterval(function() {" +
				"ac.qq(\"hola\");" +
				"}, 500);");

		
		//doit("var p = Packages.processing.core.PApplet; "
		//		+ "ac.setup = function setup() { " + ""
		//		+ "ac.ellipse(22, 22, 12, 12);" + "" + "};");

		

	}
	
	public void qq(String qq) {
		
		Log.d("qq", "qq");
	}



	public Object qq() {

		String o = "var o = {"
				+ "				  method1: function() {"

				+ "				  },"
				+ "				  method2: function() {"

				+ "				  }"
				+ "				}; "
				+ ""
				+ "var instanceThatIsAMyClass = new JavaAdapter(com.mgmedia.probandofragments.Processing.M, o);";

		JavaAdapter javaAdapter = new JavaAdapter();

		return o;

	}

	

	void doit(String code) {
		// Create an execution environment.
		Context cx = Context.enter();

		// Turn compilation off.
		cx.setOptimizationLevel(-1);

		try {
			// Initialize a variable scope with bindnings for
			// standard objects (Object, Function, etc.)
			Scriptable scope = cx.initStandardObjects();

			// Set a global variable that holds the activity instance.
			ScriptableObject.putProperty(scope, "ac",
					Context.javaToJS(this, scope));

			// Evaluate the script.
			Object result = cx.evaluateString(scope, code, "doit:", 1, null);

			// Function result2 = cx.compileFunction(scope, code, "doit:", 1,
			// null);
			// result2.call(cx, scope, scope, null);

			// cx.evaluateString(scope, "Toast.makeText('hola')", "qq", 1,
			// null);
		} catch (Exception evException) {
			Log.d("qq",
					"no se puede cargar el script " + evException.toString());
		} finally {
			// Context.exit();
		}

	}


}
