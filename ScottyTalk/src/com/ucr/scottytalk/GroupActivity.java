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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@SuppressLint("HandlerLeak")
public class GroupActivity extends Activity {
	ListView GroupsView;
	String addOn;
	ArrayAdapter <String> profiles;
	String NF;
	
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
		setContentView(R.layout.activity_group);
		
		GroupsView = (ListView) findViewById (R.id.GroupsList);
        profiles = new ArrayAdapter <String> (this, R.layout.info);

        
		Bundle extras = getIntent().getExtras(); 
		addOn = extras.getString("user");
        GroupsView.setAdapter(profiles); 
        
	    UpdateTask updateTask = new UpdateTask();
	    updateTask.start();
	    
	   	GroupsView.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view,
    				int position, long id) {
    			
    			NF = ((TextView) view).getText().toString ();
 
    			
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
			  query.findInBackground(new FindCallback() {
				    public void done(List<ParseObject> arg1, ParseException e) {
				        	for (int i = 0; i < arg1.size(); i++){
				        		ParseObject tt = arg1.get(i);
				        		String name = (String) tt.get("GroupName");
				        		Message message = handler.obtainMessage();
				        		message.obj = name;
				        		handler.sendMessage(message);
				        }
				    }
				});
		  }
	  }
	
	  void ViewGroup (){
		  Intent i =  new Intent (this, VGroupActivity.class);
		  i.putExtra("user", addOn);
		  i.putExtra("group", NF);
		  startActivity (i);
		  
	  }
		void Alert () {
   			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
   			alertDialogBuilder.setTitle("Scotty Talk");
   			
   			alertDialogBuilder
			.setMessage(NF)
			.setCancelable(false)
			.setPositiveButton("View Group",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					ViewGroup ();
				}
			  })
			.setNegativeButton("DO SOMETHING ELSE",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					
				}
			});
   			
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
	}
}