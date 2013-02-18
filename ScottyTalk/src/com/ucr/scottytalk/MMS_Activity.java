package com.ucr.scottytalk;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.SendCallback;


public class MMS_Activity extends Activity {

	String result;
	EditText editText1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mms_);
		editText1 = (EditText) findViewById (R.id.editText1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_mms_, menu);
		return true;
	}
	
	
	void SendMMS (String Person){
			//byte [] data = "Parth".getBytes ();
			//ParseFile file = new ParseFile (data);
			//file.saveInBackground ();
		
			 ParseFile file = new ParseFile(result.getBytes()); 
			 file.saveInBackground(); 
			 ParseObject object = new ParseObject("TestObject"); 
			 object.put("file", file); 
			 object.put("To", Person);
			 object.saveInBackground();
		
		Intent sendIntent = new Intent(android.content.Intent.ACTION_SENDTO); 
		sendIntent.putExtra("sms_body", "some text"); 
		sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(result));
		sendIntent.setType("*/*"); 
		//sendIntent.getData();
		startActivity(Intent.createChooser(sendIntent, "MMS to Friend"));
		
		
	}
	
	@SuppressWarnings("null")
	void ReceiveMMS (){
		ParseObject MMS = null;
		ParseFile File = (ParseFile)MMS.get("File");
		
		File.getDataInBackground (new GetDataCallback(){
			public void done (byte [] data, ParseException e) {
				if ( e == null){
					//DO Something
				}
				else {
					//something went wrong
					e.printStackTrace();
				}
			}
		
		});
	}
    
    public void Attach(View view){
    	Intent intent = new Intent (this, FileChooser.class); 
    	startActivityForResult(intent, 1);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	if (requestCode == 1)

    	     if(resultCode == RESULT_OK){

    	    	 //FILE SAVED IN RESULT
    	    	 result = data.getStringExtra("Path");
    	       Toast.makeText(this, "File Clicked: "+result, Toast.LENGTH_SHORT).show();
    	       String phoneNo = editText1.getText().toString();
    	       SendMMS(phoneNo);
    	       
    	       // send notifications to whoever is on channel ""
    	       ParsePush push = new ParsePush(); 
    	       push.setChannel("");
    	       push.setMessage("sent from Parth computer!");
    	       push.sendInBackground(new SendCallback() {
    	         public void done(ParseException e) {
    	           if (e == null) {
    	             Log.d("push", "success!");
    	           } else {
    	             Log.d("push", "failure");
    	           }
    	         }
    	       });
    	 
    	}
    }   
}