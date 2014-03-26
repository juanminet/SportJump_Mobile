package es.uma.sportjump.sjm.mobile.activities;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.inject.Inject;

import es.uma.sportjump.sjm.back.constants.ServiceConstants;
import es.uma.sportjump.sjm.back.service.UserService;
import es.uma.sportjump.sjm.back.types.ApplicationStatusType;
import es.uma.sportjump.sjm.mobile.R;


@ContentView(R.layout.activity_login)
public class LoginActivity extends RoboActivity {	

	@Inject UserService userService;	

	// UI references.	
	@InjectView(R.id.login_form_username) private EditText usernameView;
	@InjectView(R.id.login_form_password) private EditText passwordView;
	@InjectView(R.id.login_form) private View loginFormView;
	@InjectView(R.id.login_status) private View loginStatusView;
	@InjectView(R.id.login_status_message) private TextView loginStatusMessageView;	
	@InjectView(R.id.login_form_sign_in_button) private Button signInButton;
	
	
	Activity mActivity = this;
	// Values for login attempt.
	private boolean loginAttempting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		loginAttempting = false;
		
		signInButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptLogin();
			}
		});
	}


	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	private void attemptLogin() {
		if (loginAttempting) {
			return;
		}
		
		usernameView.setError(null);
		passwordView.setError(null);
		
		String username = usernameView.getText().toString();
		String password = passwordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(password)) {
			passwordView.setError(getString(R.string.login_error_field_required));
			focusView = passwordView;
			cancel = true;
		} 

		// Check for a valid username.
		if (TextUtils.isEmpty(username)) {
			usernameView.setError(getString(R.string.login_error_field_required));
			focusView = usernameView;
			cancel = true;
		}

		if (!cancel) {		
			showProgress(true);
			startAuth(username,password);			
		} else {	
			focusView.requestFocus();		
		}
	}	

	private void hideKeyboard(){
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(passwordView.getWindowToken(), 0);
	}

	private void showProgress(final boolean show) {
		
		loginStatusMessageView.setText(R.string.login_progress_signing_in);
		
		int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

		loginStatusView.setVisibility(View.VISIBLE);
		loginStatusView.animate().setDuration(shortAnimTime)
				.alpha(show ? 1 : 0)
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						loginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
					}
				});

		loginFormView.setVisibility(View.VISIBLE);
		loginFormView.animate().setDuration(shortAnimTime)
				.alpha(show ? 0 : 1)
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
					}
				});
		
	}
	
	
	private void startAuth(String username, String password) {		
		final Handler handler = new Handler(Looper.getMainLooper()){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				
				Bundle data = msg.getData();
				
				showProgress(false);
				loginAttempting = false;
				if (!data.containsKey(ServiceConstants.HANDLER_KEY_CANCEL)){
					if (data.containsKey(ServiceConstants.HANDLER_KEY_SUCCESS) && data.getBoolean(ServiceConstants.HANDLER_KEY_SUCCESS)){
						startHomeActivity();
					}else{	
						ApplicationStatusType status = null;
						if (data.containsKey(ServiceConstants.HANDLER_KEY_STATUS)){
							status = ApplicationStatusType.fromCode(data.getInt(ServiceConstants.HANDLER_KEY_STATUS)); 
						}
						
						if (status != null && ApplicationStatusType.STATUS_UNAUTHORIZED.equals(status)){
							usernameView.setError(getString(R.string.login_error_invalid_username));
							passwordView.setError(getString(R.string.login_error_incorrect_password));
							usernameView.requestFocus();	
						}else{
							//TODO other errors
						}
					}
				}				
			}			
		};
		
		loginAttempting = true;
		userService.loginUser(username, password, handler);
	}
	
	private void startHomeActivity(){
		hideKeyboard();
		Intent intent = new Intent(mActivity, HomeActivity.class);
		startActivity(intent);	
	}


	
}
