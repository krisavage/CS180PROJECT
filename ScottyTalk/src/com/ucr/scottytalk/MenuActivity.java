package com.ucr.scottytalk;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;


public class MenuActivity extends Activity {
	
String addOn;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		Bundle extras = getIntent().getExtras(); 
		addOn = extras.getString("user");

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
    	intent.putExtra ("user", "-1");
    	startActivity (intent);
    }
    
    public void MMS(View view){
    	Intent intent = new Intent (this, MMS_Activity.class); 
    	startActivity (intent);
    }
    
    public void Profile(View view){
    	Intent intent = new Intent (this, Profile_Activity.class); 
    	intent.putExtra("user", addOn);
    	startActivity (intent);
    }
    
    public void search(View view){
    	Intent intent = new Intent (this, SearchActivity.class); 
    	intent.putExtra("user", addOn);
    	startActivity (intent);
    }
    
    public void Group(View view){
    	Intent intent = new Intent (this, GroupActivity.class); 
    	intent.putExtra("user", addOn);
    	startActivity (intent);
    }
    
    
    public void ViewFriends(View view){
    	Intent intent = new Intent (this, Friends_Activity.class); 
    	intent.putExtra("user", addOn);
    	startActivity (intent);
    }
    
    public void PTT(View view){
    	Intent intent = new Intent (this, RecordActivity.class); 
    	startActivity (intent);
    }
    
    public void ConferenceCall (View view){
    	Intent i = new Intent (this, CCallActivity.class);
    	startActivity (i);
    }
    
}
