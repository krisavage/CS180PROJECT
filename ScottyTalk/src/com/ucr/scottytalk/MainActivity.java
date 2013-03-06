package com.ucr.scottytalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.parse.Parse;
import com.stackmob.android.sdk.common.StackMobAndroid;


public class MainActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.ucr.scottytalk.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);	 
		StackMobAndroid.init(getApplicationContext(), 0, "58c2c611-5623-49ab-a835-9bcb2cf98e72");
		Parse.initialize(this, "4khclHJuSyBO3TYEiQBUKeZSt7lhvRm9Z6j2sgTF", "IUQ4QpdIAOgcHG2rXKoVKmkHk2Cj923S5wAh1Ry2"); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_menu_, menu);
		return true;
	}
	
	//Function that handles the Login button
    public void menu(View view){
    	
    	Intent intent = new Intent (this, LogIn.class); 
    	startActivity (intent);

    }
    
    public void register(View view){
    	Intent intent = new Intent (this, RegisterActivity.class); 
    	startActivity (intent);
    }
    
}