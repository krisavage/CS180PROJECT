package com.ucr.scottytalk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.stackmob.sdk.callback.StackMobModelCallback;
import com.stackmob.sdk.exception.StackMobException;

public class LogIn extends Activity{

	//Button loginbutton;
	EditText pass;
	EditText User;
	boolean login = false;
	
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        
        Log.e("debugger", "Created!");
        
        pass = (EditText) findViewById (R.id.Password1);
        User = (EditText) findViewById (R.id.EmailAddress);
        
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	getActionBar ().setDisplayHomeAsUpEnabled(true);
        }
    }
    
    
    public void LoggedInMenu(View view){    	
        User user = new User(User.getText().toString(), pass.getText().toString());
        user.login(new StackMobModelCallback() {
         
            @Override
            public void success() {
            	login = true;
            }
         
            @Override
            public void failure(StackMobException e) {

            }
        });
        
        if (login){
        	Intent intent = new Intent (this, MenuActivity.class); 
        	startActivity (intent);
        }
        else {
        	Toast.makeText(this, "Failed to LogIn", Toast.LENGTH_SHORT).show();
        }

    }
    
}
