package com.ucr.scottytalk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.stackmob.sdk.callback.StackMobModelCallback;
import com.stackmob.sdk.exception.StackMobException;

public class LogIn extends Activity{

	EditText pass;
	EditText User;
	boolean login;
	
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        
        pass = (EditText) findViewById (R.id.Password1);
        User = (EditText) findViewById (R.id.EmailAddress);
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
        	 intent.putExtra("user", User.getText().toString()); 
        	startActivity (intent);
        }
        else {
        	Toast.makeText(this, "Failed to LogIn", Toast.LENGTH_SHORT).show();
        }
    }
}
