	package com.ucr.scottytalk;


import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


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

    
    public void Attach(View view){
    	Intent intent = new Intent (this, FileChooser.class); 
    	startActivityForResult(intent, 1);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	if (requestCode == 1)

    	     if(resultCode == RESULT_OK){

    	    	 result = data.getStringExtra("Path");

    	    	 File file = new File(result);  
    	    	 long size = file.length ();
    	    	 size = ((size *8)/1000000) + 1;
    	    	 
    	    	 Toast.makeText(this,result, Toast.LENGTH_SHORT).show();
    	    	 Toast.makeText(this,"Estimated Transfer Time:  " + size + "s", Toast.LENGTH_SHORT).show();
    	       
    	    	 Intent intent = new Intent();  
    	    	 intent.setAction(Intent.ACTION_SEND);  
    	    	 intent.setType("image/jpg");  
    	    	 intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file) );  
    	    	 startActivity(intent); 
    	}
    }   
}