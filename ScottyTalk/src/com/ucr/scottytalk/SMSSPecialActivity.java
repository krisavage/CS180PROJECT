package com.ucr.scottytalk;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.telephony.gsm.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stackmob.sdk.api.StackMobQuery;
import com.stackmob.sdk.api.StackMobQueryField;
import com.stackmob.sdk.callback.StackMobQueryCallback;
import com.stackmob.sdk.exception.StackMobException;



@SuppressWarnings("deprecation")
public class SMSSPecialActivity extends Activity {

	Button btnSendSMS;
	EditText txtPhoneNo;
	EditText txtMessage;
	String addOn;
	String phoneNum;
	TextView Number;
	
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		Bundle extras = getIntent().getExtras(); 
		addOn = extras.getString("user");
		

		setContentView(R.layout.activity_smsspecial);
        
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	getActionBar ().setDisplayHomeAsUpEnabled(true);
        }
        
        btnSendSMS = (Button) findViewById (R.id.btnSendSMS);
        txtPhoneNo = (EditText) findViewById (R.id.txtPhoneNo);
        txtMessage = (EditText) findViewById (R.id.txtMessage);
        Number = new TextView (this);
        Number=(TextView)findViewById(R.id.USER);
        
	    getnum check = new getnum();
	    check.start();
        
        btnSendSMS.setOnClickListener(new View.OnClickListener() 
        {

			@Override
			public void onClick(View arg0) {
				String phoneNo = phoneNum;
				String message = txtMessage.getText().toString();
				
				//If the phone number and message field are not empty send it.
				if (phoneNo.length() > 0 && message.length () > 0) {
					sendSMS(phoneNo,message);
				}
				//else display an error message.
				else
					Toast.makeText(getBaseContext(),
							"Please Enter both phone number and message.",
							Toast.LENGTH_SHORT).show();
			}
        	
        });
      
      ;
    }
    
    
    protected class getnum extends Thread implements Runnable {
    	public void run (){
    		Profile.query(Profile.class, new StackMobQuery().field(new StackMobQueryField("username").isEqualTo(addOn)), new StackMobQueryCallback<Profile>() {
    			public void success(List<Profile> result) {
    				phoneNum = result.get(0).getPhoneNumber();
    				Number.setText("Texting: " + addOn + " Phone Number: " + phoneNum);
    			}
				public void failure(StackMobException e) {
			
				}
    		});
    	};
     
    }    
    private void sendSMS (String number, String message)
    {
    	String sent = "SMS_SENT";
    	String delivered = "SMS_DELIVERED";
    	
    	//Intent for handling the send activity
    	PendingIntent sPI = PendingIntent.getBroadcast 
    			(this,0, new Intent(sent),0);
    	
    	//Intnet for handling the delivered activity 
    	PendingIntent dPI = PendingIntent.getBroadcast
    			(this,0, new Intent (delivered), 0);
    	
    	//SMS HAS BEEN SENT
    	
    	//checks the status of the send message
    	registerReceiver (new BroadcastReceiver(){
    		public void onReceive (Context arg0, Intent arg1){
    		switch (getResultCode())
    		{
    		case Activity.RESULT_OK:
    			Toast.makeText(getBaseContext(),"SMS sent",
    					Toast.LENGTH_SHORT).show();
    			break;
    		case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
    			Toast.makeText(getBaseContext(),"Generic Failure",
    					Toast.LENGTH_SHORT).show();
    			break;
    		case SmsManager.RESULT_ERROR_NO_SERVICE:
    			Toast.makeText(getBaseContext(), "No Service", 
    					Toast.LENGTH_SHORT).show();
    			break;
    		case SmsManager.RESULT_ERROR_NULL_PDU:
    			Toast.makeText(getBaseContext(), "Null PDU", 
    					Toast.LENGTH_SHORT).show();
    			break;
    		case SmsManager.RESULT_ERROR_RADIO_OFF:
    				Toast.makeText(getBaseContext(), "Radio off", 
    						Toast.LENGTH_SHORT).show();
    				break;	
    		}
    	}
    	
    	 }, new IntentFilter(sent));
    	
    	//SMS HAS BEEN DELIVERED
    	//Checks the status of the message
    	
    	registerReceiver(new BroadcastReceiver(){
    		public void onReceive (Context arg0, Intent arg1){
    			switch (getResultCode())
    			{
    			case Activity.RESULT_OK:
    				Toast.makeText(getBaseContext(), "SMS delivered", 
    						Toast.LENGTH_SHORT).show();
    				break;
    			case Activity.RESULT_CANCELED:
    				Toast.makeText(getBaseContext(), "SMS not delivered",
    						Toast.LENGTH_SHORT).show ();
    				break;
    			}
    		}
    	}, new IntentFilter (delivered));
    	
    	SmsManager sms = SmsManager.getDefault();
    	sms.sendTextMessage(number, null,message, sPI, dPI);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId())
    	{
    	case android.R.id.home:
    		NavUtils.navigateUpFromSameTask(this);
    		return true;
    }
    return super.onOptionsItemSelected (item);
    }
   
}

