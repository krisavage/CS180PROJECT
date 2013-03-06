package com.ucr.scottytalk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.stackmob.sdk.api.StackMobOptions;
import com.stackmob.sdk.callback.StackMobCallback;
import com.stackmob.sdk.exception.StackMobException;
import com.stackmob.sdk.model.StackMobUser;

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
    
    public void login(final StackMobUser user) {
    	user.login(StackMobOptions.depthOf(2), new StackMobCallback() {
    		
    		@Override
    		public void success(String arg0) {
    			successLogIn ();
    		}
    		
    		@Override
    		public void failure(StackMobException arg0) {
    			Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
    			
    		}
    	});
    }
    
    void successLogIn  () {
    	Intent i = new Intent (this, MenuActivity.class);
		i.putExtra("user", User.getText().toString());
	  	startActivity (i);
    }
    
    User getuser (){
    	return  new User(User.getText().toString(), pass.getText().toString());
    }
    
    public void LoggedInMenu(View view){ 

    	login (getuser ());
    }
}


