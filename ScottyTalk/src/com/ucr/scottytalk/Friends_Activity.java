package com.ucr.scottytalk;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
	String friend;

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
	    
    	FriendsView.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view,
    				int position, long id) {
    			
    			friend =  ((TextView) view).getText().toString ();
    			Alert (friend);
    			

    		}
    	});	 
	}

	void friendsprofile (String user){
    	Intent intent = new Intent (this, Profile_Activity.class); 
    	intent.putExtra("user",user);
    	startActivity (intent);
	}
	
	
	void Alert (final String NF) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
			alertDialogBuilder.setTitle("Scotty Talk");
			
			alertDialogBuilder
		.setMessage(NF)
		.setCancelable(false)
		.setNeutralButton("Delete",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
			    DeleteTask updateTask = new DeleteTask();
			    updateTask.start();
			}
		  })
		.setPositiveButton("SMS",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
    			SMS (NF);
			}
		  })
		.setNegativeButton("View Profile",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				friendsprofile (NF);
			}
		});
			
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_friends_, menu);
		return true;
	}
	
	void SMS (String user){
    	Intent intent = new Intent (this, SMSSPecialActivity.class); 
    	intent.putExtra("user",user);
    	startActivity (intent);
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
	
	  protected class DeleteTask extends Thread implements Runnable {
		  public void run() {
			  ParseQuery query = new ParseQuery ("Friends");
			  query.whereEqualTo("User", addOn);
			  query.getFirstInBackground(new GetCallback() {
				  @Override
				  public void done(ParseObject arg0, ParseException arg1) {
					  if (arg0 == null){
			    		    Toast.makeText(getApplicationContext(),
			    	    			"Unable to Delete Friend", Toast.LENGTH_SHORT).show();
					  }
					  else {
						@SuppressWarnings("unchecked")
						List <String>temp = (List<String>) arg0.get ("Friends");
						  for (int i = 0; i < temp.size (); i++){
							  if (friend.equals(temp.get(i))){
								  temp.remove(i);
								  arg0.put ("Friends", temp);
								  arg0.saveInBackground ();
								  Toast.makeText(getApplicationContext(),
										  "Successfully deleted " + friend, Toast.LENGTH_SHORT).show();
								  	profiles.clear ();
								    UpdateTask updateTask = new UpdateTask();
								    updateTask.start();
							  }
						  }
					  }
				
				  }
			  });
		  }
	  }
}




