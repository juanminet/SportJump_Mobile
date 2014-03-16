package es.uma.sportjump.sjm.mobile.activities;

import roboguice.activity.RoboActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.inject.Inject;

import es.uma.sportjump.sjm.back.service.UserService;

public class MainActivity extends RoboActivity {

	@Inject
	UserService userService;
	
	Activity mActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
		mActivity = this;
		
		if (userService.isUserLogged()){		
			startHomeActivity();			
		}else{
			startLoginActivity();
		}	
		
		this.finish();
	}
	
	private void startHomeActivity(){
		Intent intent = new Intent(mActivity,HomeActivity.class);
		startActivity(intent);
	}
	
	private void startLoginActivity(){
		Intent intent = new Intent(mActivity,LoginActivity.class);
		startActivity(intent);
	}

}
