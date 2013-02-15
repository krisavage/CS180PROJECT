package com.ucr.scottytalk;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.stackmob.sdk.api.StackMobQuery;
import com.stackmob.sdk.api.StackMobQueryField;
import com.stackmob.sdk.callback.StackMobQueryCallback;
import com.stackmob.sdk.exception.StackMobException;

@SuppressLint("HandlerLeak")
public class Profile_Activity extends Activity {
	ListView profileview;
	ArrayAdapter <String> Info;
	String addOn;
	
	
	  @SuppressLint("HandlerLeak")
	protected Handler handler = new Handler() {
		    @Override
		    public void handleMessage(Message msg) {
		      Info.add((String)msg.obj);
		    }
		  };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_);
		
		profileview = (ListView) findViewById (R.id.ProfileView);
        Info = new ArrayAdapter <String> (this, R.layout.info);
        profileview.setAdapter(Info);
        
		Bundle extras = getIntent().getExtras(); 
		addOn = extras.getString("user");

	    UpdateTask updateTask = new UpdateTask();
	    updateTask.start();
	}
	
	
	  protected class UpdateTask extends Thread implements Runnable {
		    public void run() {
				Profile.query(Profile.class, new StackMobQuery().field(new StackMobQueryField("username").isEqualTo(addOn)), new StackMobQueryCallback<Profile>() {
				 	@Override
					    public void success(List<Profile> result) {
				 		
		    			Message message = handler.obtainMessage();
		    			message.obj = "Name: " + result.get(0).getName();
		    			handler.sendMessage(message);
		    			
		    			message = handler.obtainMessage();
		    			message.obj = "User Name: " + result.get(0).getUserName();
		    			handler.sendMessage(message);
					    
		    			message = handler.obtainMessage();
		    			message.obj = "Phone Number: " + result.get(0).getPhoneNumber();
		    			handler.sendMessage(message);
					    			
					    }
					    @Override
					    public void failure(StackMobException e) {
					    }
					}); 
		    }
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_profile_, menu);
		return true;	
	}

}
