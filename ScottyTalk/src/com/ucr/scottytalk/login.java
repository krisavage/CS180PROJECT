package com.ucr.scottytalk;

import java.util.List;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.*;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.os.AsyncTask;

public class LogIn extends Activity{

	Button loginbutton;

    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        
        Log.e("debugger", "Created!");
        
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	getActionBar ().setDisplayHomeAsUpEnabled(true);
        }
    }
    
    public void LoggedInMenu(View view){
    	//Uncomment Once we are able to login
    	//new LoginUser().execute(); 
    	Intent intent = new Intent (this, MenuActivity.class); 
    	startActivity (intent);
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
