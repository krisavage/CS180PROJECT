/**
 * 
 */
package com.ucr.scottytalk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.widget.Toast;

/**
 * @author ndlerena
 *
 */
@SuppressWarnings("deprecation")
public class SmsReceiver extends BroadcastReceiver{
	
	@Override
	//Rceives the message and outputs it.
	public void onReceive (Context context, Intent intent)
	{
		Bundle bundle = intent.getExtras ();
		SmsMessage[] msgs = null;
		String str = "";
		if (bundle != null)
		{
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage [pdus.length];
			for (int i = 0; i < msgs.length; i++){
				msgs [i] = SmsMessage.createFromPdu((byte[])pdus[i]);
				
				str += "SMS from " + msgs [i].getOriginatingAddress()
				 + " : " + msgs [i].getMessageBody().toString() + "\n";
			}
			
			Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
		}
	}
}