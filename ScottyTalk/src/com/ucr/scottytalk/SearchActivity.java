package com.ucr.scottytalk;

import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.stackmob.sdk.api.StackMobOptions;
import com.stackmob.sdk.api.StackMobQuery;
import com.stackmob.sdk.callback.StackMobQueryCallback;
import com.stackmob.sdk.exception.StackMobException;

@SuppressLint("HandlerLeak")
public class SearchActivity extends Activity {
	ListView profileview;
	ArrayAdapter <String> Info;
	String addOn;
	String NF;
	
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
		setContentView(R.layout.activity_search);
		
		profileview = (ListView) findViewById (R.id.mylist);
        Info = new ArrayAdapter <String> (this, R.layout.info);

        
		Bundle extras = getIntent().getExtras(); 
		addOn = extras.getString("user");
        profileview.setAdapter(Info); 
	    UpdateTask updateTask = new UpdateTask();
	    updateTask.start();
	    
    	profileview.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view,
    				int position, long id) {
    			
    			NF = ((TextView) view).getText().toString ();
    			
    			UpdateFriends updateFriends = new UpdateFriends ();
    			updateFriends.start ();
    		}
    	});	   
	}
	
	  protected class UpdateTask extends Thread implements Runnable {
		    public void run() {
		        Profile.query(Profile.class, new StackMobQuery().isInRange(0, 9), StackMobOptions.selectedFields(Arrays.asList("name", "phonenum","username")), new StackMobQueryCallback<Profile>() {
					    @Override
					    public void success(List<Profile> result) {
					    	for (int i = 0; i < result.size (); i++) {
					    		if (result.get(i).getUserName().equals(addOn)){
					    		}
					    		else {
					    			Message message = handler.obtainMessage();
					    			message.obj =  (result.get(i).getName());
					    			handler.sendMessage(message);
					    		}
					    	}
					    }
					    @Override
					    public void failure(StackMobException e) {
			    		    Toast.makeText(getApplicationContext(),
			    	    			"Unable to fetch Users", Toast.LENGTH_SHORT).show();
					    }
					});
		    }
	  }
	  
	  protected class UpdateFriends extends Thread implements Runnable {
		  public void run() {
			  ParseQuery query = new ParseQuery ("Friends");
			  query.whereEqualTo("User", addOn);
			  query.getFirstInBackground(new GetCallback() {
				  @Override
				  public void done(ParseObject arg0, ParseException arg1) {
					  if (arg0 == null){
			    		    Toast.makeText(getApplicationContext(),
			    	    			"Unable to Save New Friend", Toast.LENGTH_SHORT).show();
					  }
					  else {
						  @SuppressWarnings("unchecked")
						List <String>temp = (List<String>) arg0.get ("Friends");
						  for (int i = 0; i < temp.size (); i++)
							  if (NF.equals(temp.get(i))){
								  Toast.makeText(getApplicationContext(),
										  "All ready a friend", Toast.LENGTH_SHORT).show();
								  return;
							  }
						  
						  temp.add (NF);
						  arg0.put ("Friends", temp);
						  arg0.saveInBackground ();
						  Toast.makeText(getApplicationContext(),
								  "Succesfully added " + NF + " as a friends", Toast.LENGTH_SHORT).show();
					  }
				
				  }
			  });
		  }
	  }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search, menu);
		return true;
	}
	
}




