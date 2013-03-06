package com.ucr.scottytalk;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;
import com.stackmob.sdk.api.StackMobQuery;
import com.stackmob.sdk.api.StackMobQueryField;
import com.stackmob.sdk.callback.StackMobQueryCallback;
import com.stackmob.sdk.exception.StackMobException;

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
    	
    	if (username.getText().toString().equals (""))
    		return;
    	if(password.getText().toString().equals(""))
    		return;
    	if (name.getText().toString().equals (""))
    		return;
    	if(phoneNum.getText().toString().equals(""))
    		return;
    	
	    existuser check = new existuser();
	    check.start();
    }
    
    protected class existuser extends Thread implements Runnable {
    	public void run (){
    		User.query(User.class, new StackMobQuery().field(new StackMobQueryField("username").isEqualTo(username.getText().toString ())), new StackMobQueryCallback<User>() {
    			public void success(List<User> result) {

    				if (result.size() != 0 && result.get(0).getUsername().equals (username.getText().toString())){
        				finish();
        				//Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
    				}
    				else
    				createuser ();
    			}

				public void failure(StackMobException e) {
    				finish(); //Toast.makeText(getApplicationContext(), "failed to create user", Toast.LENGTH_SHORT).show();
    			}
    			});
    	};
    }
    
    public void createuser (){
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