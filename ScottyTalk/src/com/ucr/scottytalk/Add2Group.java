package com.ucr.scottytalk;

import java.util.ArrayList;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@SuppressLint("HandlerLeak")
public class Add2Group extends Activity {
	TextView t;
	EditText group;
	ListView groupview;
	Button GG;
	String addOn;
	String NewMember;
	String GROUP;
	ArrayAdapter <String> Info;
	
	protected Handler handler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	      Info.add((String)msg.obj);
	    }
	  };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add2_group);
		
		Bundle extras = getIntent().getExtras(); 
		addOn = extras.getString("user");
		NewMember = extras.getString("member");
		
		
		groupview = (ListView) findViewById (R.id.GroupList);
        Info = new ArrayAdapter <String> (this, R.layout.info);
        groupview.setAdapter(Info);
		
		t=new TextView(this); 
		t=(TextView)findViewById(R.id.MemberText); 
		t.setText("New Group Member: " + NewMember);
		
	    UpdateTask updateTask = new UpdateTask();
	    updateTask.start();
		
		group = (EditText) findViewById (R.id.GroupName);
		GG = (Button) findViewById (R.id.NewGroup);
		
		GG.setOnClickListener(new Button.OnClickListener() {
		    public void onClick(View v) {
		    	
		    	if (group.getText().toString().equals("")){
		    		Toast.makeText(getApplicationContext(),
				  			"Enter a group Name", Toast.LENGTH_SHORT).show();
		    		return;
		    
		    	}
		    	groupExist check = new groupExist();
		    	check.start();
		    }
		});
		
    	groupview.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view,
    				int position, long id) {
    			
    			GROUP = ((TextView) view).getText().toString ();
    			UpdateGroup updateGroup = new UpdateGroup();
    		    updateGroup.start();
    		}
    	});	  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add2_group, menu);
		return true;
	}
	
	  protected class groupExist extends Thread implements Runnable {
		  public void run() {
			  ParseQuery query = new ParseQuery ("Groups");
			  query.whereEqualTo("User", addOn);
			  query.whereEqualTo("GroupName", group.getText().toString() );
			  query.findInBackground(new FindCallback() {
				    public void done(List<ParseObject> arg1, ParseException e) {
				    	if (arg1.size() == 0){
					    	List <String> t = new ArrayList <String>();
					    	t.add(NewMember);
					    	final ParseObject Groups = new ParseObject ("Groups");
					    	Groups.put("User", addOn);
					    	Groups.put("Groups", t);
					    	Groups.put("GroupName", group.getText().toString());
					    	Groups.saveInBackground();
					    	Toast.makeText(getApplicationContext(),
						  			"Succesfully added " + NewMember + " to " + group.getText().toString() + " group",
						  			Toast.LENGTH_SHORT).show();
					    	finish ();
				    	}
				    	else {
				    		
				    		GROUP = group.getText().toString();
			    			UpdateGroup updateGroup = new UpdateGroup();
			    		    updateGroup.start();
				    	}
				    }
				});
		  }
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
	 
	  protected class UpdateGroup extends Thread implements Runnable {
		  public void run() {
			ParseQuery query = new ParseQuery ("Groups");
			  query.whereEqualTo("User", addOn);
			  query.whereEqualTo("GroupName", GROUP);
			  query.getFirstInBackground(new GetCallback() {
				  @Override
				  public void done(ParseObject arg0, ParseException arg1) {
					  if (arg0 == null){
			    		    Toast.makeText(getApplicationContext(),
			    	    			"Unable to Save to Group", Toast.LENGTH_SHORT).show();
					  }
					  else {
						  @SuppressWarnings("unchecked")
						List <String>temp = (List<String>) arg0.get ("Groups");
						  for (int i = 0; i < temp.size (); i++)
							  if (NewMember.equals(temp.get(i))){
								  Toast.makeText(getApplicationContext(),
										  "All ready in Group", Toast.LENGTH_SHORT).show();
								  	return;
								  	}
								  	
								  	temp.add(NewMember);
								  	arg0.put ("Groups", temp);
								  	arg0.saveInBackground ();
								  	Toast.makeText(getApplicationContext(),
								  			"Succesfully added " + NewMember + " to " + GROUP + " group", Toast.LENGTH_SHORT).show();	
								  	finish ();
							  }
				  }
			  });
		  }
	  }
	  
	
}
