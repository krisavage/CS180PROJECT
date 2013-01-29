package com.ucr.scottytalk;

import java.util.List;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.*;
import java.io.*;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.telephony.gsm.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.net.URLConnection;
import java.net.URL;
import java.net.HttpURLConnection;
import android.util.Log;
import android.os.AsyncTask;

public class login extends Activity{
	
	Button loginbutton;
	
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	Intent intent = getIntent ();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        Log.e("debugger", "Created!");
        
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	getActionBar ().setDisplayHomeAsUpEnabled(true);
        }
        
        loginbutton = (Button) findViewById (R.id.loginbutton);
        
        //Listen to the send button. Check if the send button was pressed.
        loginbutton.setOnClickListener(new View.OnClickListener() 
        {

			@Override
			public void onClick(View arg0) 
			{
				new LoginUser().execute();
			}
        });
    }
}
			
class LoginUser extends AsyncTask<String, String, String> {
	protected String doInBackground(String... args) {
		Log.i("debugger", "Coo");
		JSONParser jsonParser = new JSONParser();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", "realemail"));
		params.add(new BasicNameValuePair("password", "supersecret"));
		
		Log.i("debugger", params.toString());
		
		JSONObject json = jsonParser.makeHttpRequest("http://krishna.frvl.us/cs180/login.php", "POST", params);
		
		Log.i("debugger", json.toString());
		
		try {
			int success = json.getInt("success");
			
			if (success == 1) {
				// Yay!
				// Login in user
			} else {
				Log.i("debugger", "Didn't work!");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
		
        	
				
      
    



	

				
				
