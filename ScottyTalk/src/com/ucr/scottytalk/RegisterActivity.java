package com.ucr.scottytalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

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
    	user.save();
    	Profile a = new Profile (name.getText().toString(), phoneNum.getText().toString() ,username.getText().toString());
    	
    	a.save();
    	
    	Intent intent = new Intent (this, MenuActivity.class); 
    	startActivity (intent);
    }
	
}
