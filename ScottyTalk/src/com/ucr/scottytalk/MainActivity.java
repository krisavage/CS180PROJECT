package com.ucr.scottytalk;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.ucr.scottytalk.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_menu_, menu);
		return true;
	}
	
	//Function that handles the SMS button
    public void outputMessage(View view){
    	Intent intent = new Intent (this, SMSActivity.class); 
    	startActivity (intent);
    }

    public void loginScreen(View view) {
    	Intent intent = new Intent (this, login.class);
    	startActivity (intent);
    }
}
