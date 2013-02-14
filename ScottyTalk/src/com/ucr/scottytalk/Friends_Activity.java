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
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@SuppressLint("HandlerLeak")
public class Friends_Activity extends Activity {
	ListView FriendsView;
	String addOn;
	ArrayAdapter <String> profiles;
	@SuppressLint("HandlerLeak")
	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			profiles.add((String)msg.obj);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends_);
		
		FriendsView = (ListView) findViewById (R.id.FriendsList);
        profiles = new ArrayAdapter <String> (this, R.layout.info);

        
		Bundle extras = getIntent().getExtras(); 
		addOn = extras.getString("user");
        FriendsView.setAdapter(profiles); 
        
	    UpdateTask updateTask = new UpdateTask();
	    updateTask.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_friends_, menu);
		return true;
	}
	
	  protected class UpdateTask extends Thread implements Runnable {
		  public void run() {
			  ParseQuery query = new ParseQuery ("Friends");
			  query.whereEqualTo("User", addOn);
			  query.getFirstInBackground(new GetCallback() {
				  @Override
				  public void done(ParseObject arg0, ParseException arg1) {
					  if (arg0 == null){
			    		    Toast.makeText(getApplicationContext(),
			    	    			"Unable to Fetch Friends", Toast.LENGTH_SHORT).show();
					  }
					  else {
						@SuppressWarnings("unchecked")
						List <String>temp = (List<String>) arg0.get ("Friends");
						  for (int i = 0; i < temp.size (); i++){
				    			Message message = handler.obtainMessage();
				    			message.obj =  (temp.get(i));
				    			handler.sendMessage(message);
						  }
					  }
				
				  }
			  });
		  }
	  }
	

}




