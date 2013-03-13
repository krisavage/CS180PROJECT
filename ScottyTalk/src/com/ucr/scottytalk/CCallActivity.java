package com.ucr.scottytalk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;

public class CCallActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ccall);
		
		PhoneCallListener phoneListener = new PhoneCallListener();
		TelephonyManager telephonyManager = (TelephonyManager) this
			.getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_ccall, menu);
		return true;
	}



public void startConference (View view){
	Intent callIntent = new Intent(Intent.ACTION_CALL);
	callIntent.setData(Uri.parse("tel:8186601633"));
	startActivity(callIntent);
}

private class PhoneCallListener extends PhoneStateListener {
	 
	private boolean isPhoneCalling = false;

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {

		if (TelephonyManager.CALL_STATE_OFFHOOK == state) {

			isPhoneCalling = true;
		}

		if (TelephonyManager.CALL_STATE_IDLE == state) {


			if (isPhoneCalling) {

				Intent i = getBaseContext().getPackageManager()
					.getLaunchIntentForPackage(
						getBaseContext().getPackageName());
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);

				isPhoneCalling = false;
			}

		}
	}
}

}