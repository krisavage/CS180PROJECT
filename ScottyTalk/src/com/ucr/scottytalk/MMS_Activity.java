package com.ucr.scottytalk;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

public class MMS_Activity extends Activity {

	String result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mms_);	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_mms_, menu);
		return true;
	}
	
	
	void SendMMS (int Person){
			byte [] data = "working at parse is great!".getBytes ();
			ParseFile file = new ParseFile ("resume.txt",data);
			//file.saveInBackground ();
			
			ParseObject MMS = new ParseObject ("File Transfer");
			MMS.put ("To",Person);
			MMS.put("File", file);
			MMS.saveInBackground ();
	}
	
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
    	    	 result = data.getStringExtra("File");
    	       Toast.makeText(this, "File Clicked: "+result, Toast.LENGTH_SHORT).show();

    	}

    }
    
}