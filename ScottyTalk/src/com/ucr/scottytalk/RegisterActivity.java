package com.ucr.scottytalk;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseObject;

public class RegisterActivity extends Activity {
	EditText username;
	EditText password;
	EditText name;
	EditText phoneNum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		username = (EditText)findViewById (R.id.userName);
		password = (EditText)findViewById (R.id.Password);
		name = (EditText)findViewById (R.id.Name);
		phoneNum = (EditText)findViewById (R.id.PhoneNum);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_register, menu);
		return true;
	}

    public void register(View view){
    	User user = new User(username.getText().toString(), password.getText().toString());
    	Profile a = new Profile (name.getText().toString(), phoneNum.getText().toString() ,username.getText().toString());
    	final ParseObject Friends = new ParseObject ("Friends");
    	Friends.put("User", username.getText().toString());
    	Friends.put("Friends", Arrays.asList());
    	
    	Friends.saveInBackground();
    	a.save();
    	user.save();
    	
    	
    	Intent intent = new Intent (this, MenuActivity.class); 
   	 	intent.putExtra("user", username.getText().toString()); 
   	 	startActivity (intent);
    }
}

