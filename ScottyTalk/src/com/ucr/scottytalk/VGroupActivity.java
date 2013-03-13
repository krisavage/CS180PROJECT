package com.ucr.scottytalk;

import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.ucr.scottytalk.GroupActivity.UpdateTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class VGroupActivity extends Activity {
	ListView GroupsView;
	String addOn;
	ArrayAdapter <String> profiles;
	String GROUPNAME;
	String FRIEND;
	
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
		setContentView(R.layout.activity_vgroup);GroupsView = (ListView) findViewById (R.id.GroupFriendList);
        profiles = new ArrayAdapter <String> (this, R.layout.info);

        
		Bundle extras = getIntent().getExtras(); 
		addOn = extras.getString("user");
		GROUPNAME = extras.getString("group");
        GroupsView.setAdapter(profiles); 
        
	    UpdateTask updateTask = new UpdateTask();
	    updateTask.start();
	    
	   	GroupsView.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view,
    				int position, long id) {
    			
    			FRIEND = ((TextView) view).getText().toString ();
 
    			
    			Alert ();

    		}
    	});	   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_group, menu);
		return true;
	}

	  protected class UpdateTask extends Thread implements Runnable {
		  public void run() {
			  ParseQuery query = new ParseQuery ("Groups");
			  query.whereEqualTo("User", addOn);
			  query.whereEqualTo("GroupName", GROUPNAME);
			  query.getFirstInBackground(new GetCallback() {
				    public void done(ParseObject arg1, ParseException e) {
				    	List <String> temp = (List<String>) arg1.get ("Groups");
				    	
				        	for (int i = 0; i < temp.size (); i++){
				        		String tt = temp.get(i);
				        		Message message = handler.obtainMessage();
				        		message.obj = tt;
				        		handler.sendMessage(message);
				        }
				    }
				});
		  }
	  }
	  
	  protected class DeleteTask extends Thread implements Runnable {
		  public void run() {
			  ParseQuery query = new ParseQuery ("Groups");
			  query.whereEqualTo("User", addOn);
			  query.whereEqualTo("GroupName", GROUPNAME);
			  query.getFirstInBackground(new GetCallback() {
				  @Override
				  public void done(ParseObject arg0, ParseException arg1) {
					  if (arg0 == null){
			    		    Toast.makeText(getApplicationContext(),
			    	    			"Unable to Delete Friend", Toast.LENGTH_SHORT).show();
					  }
					  else {
						  @SuppressWarnings("unchecked")
						List <String>temp = (List<String>) arg0.get ("Groups");
						  for (int i = 0; i < temp.size (); i++)
							  if (FRIEND.equals(temp.get(i))){
								  temp.remove(i);
								  arg0.put ("Groups", temp);
								  arg0.saveInBackground ();
								  Toast.makeText(getApplicationContext(),
										  "Successfully deleted " + FRIEND + " from group " + GROUPNAME, Toast.LENGTH_SHORT).show();
								  finish ();
							  }
					  }
				
				  }
			  });
		  }
	  }
	  
	
	  void ViewGroup (){
		  Intent i =  new Intent (this, VGroupActivity.class);
		  i.putExtra("user", addOn);
		  startActivity (i);
		  
	  }
		void Alert () {
   			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
   			alertDialogBuilder.setTitle("Scotty Talk");
   			
   			alertDialogBuilder
			.setMessage("Delete: " + FRIEND)
			.setCancelable(false)
			.setPositiveButton("Delete",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
				    DeleteTask dTask = new DeleteTask();
				    dTask.start();
				}
			  })
			.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					
				}
			});
   			
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
	}
}