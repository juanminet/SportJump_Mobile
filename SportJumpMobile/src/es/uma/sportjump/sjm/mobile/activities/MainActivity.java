package es.uma.sportjump.sjm.mobile.activities;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.inject.Inject;

import es.uma.sportjump.sjm.back.service.UserService;
import es.uma.sportjump.sjm.mobile.R;
import es.uma.sportjump.sjm.mobile.constants.MobileConstants;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActivity {

	@Inject
	UserService userService;
	
	Activity mActivity;
	private Handler logoHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		    
	    mActivity = this;
	    
	    logoHandler = new Handler();
	    logoHandler.postDelayed(new Runnable() {
            public void run() {
            	startApplication();
            }
        }, MobileConstants.LOGO_DURATION);        

    }

  
	
	@Override
	protected void onStart() {
		super.onStart();
		
		
	}



	@Override
	protected void onResume() {
		super.onResume();		
	}



	private void startApplication() {	
		
		
		
		if (userService.isUserLogged()){		
			startHomeActivity();			
		}else{
			startLoginActivity();
		}	
		
		mActivity.finish();
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
