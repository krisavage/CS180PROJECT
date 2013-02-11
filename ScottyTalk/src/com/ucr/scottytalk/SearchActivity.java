package com.ucr.scottytalk;

import java.util.Arrays;
import java.util.List;

import com.stackmob.sdk.api.StackMobOptions;
import com.stackmob.sdk.api.StackMobQuery;
import com.stackmob.sdk.callback.StackMobQueryCallback;
import com.stackmob.sdk.exception.StackMobException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchActivity extends Activity {
	ListView profileview;
	ArrayAdapter <String> Info;
	String addOn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		profileview = (ListView) findViewById (R.id.SearchView);
        Info = new ArrayAdapter <String> (this, R.layout.info);
        profileview.setAdapter(Info);
        
		Bundle extras = getIntent().getExtras(); 
		addOn = extras.getString("user");
		
	
		 Profile.query(Profile.class, new StackMobQuery().isInRange(0, 9), StackMobOptions.selectedFields(Arrays.asList("name", "phonenum","username")), new StackMobQueryCallback<Profile>() {
			    @Override
			    public void success(List<Profile> result) {
			    	Info.add ("Name: " + result.get(0).getName() + " - " + 
			    	"User Name: " + result.get(0).getUserName() + " - " + 
			    	"Phone Number: " + result.get(0).getPhoneNumber());
			    }
			    @Override
			    public void failure(StackMobException e) {
			    }
			}); 
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search, menu);
		return true;
	}

}
