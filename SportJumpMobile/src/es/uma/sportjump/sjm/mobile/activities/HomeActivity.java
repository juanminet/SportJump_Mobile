package es.uma.sportjump.sjm.mobile.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.inject.Inject;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import es.uma.sportjump.sjm.back.constants.ServiceConstants;
import es.uma.sportjump.sjm.back.dto.CalendarEventDto;
import es.uma.sportjump.sjm.back.exceptions.SportJumpBackException;
import es.uma.sportjump.sjm.back.responses.CalendarEventResponse;
import es.uma.sportjump.sjm.back.service.PlanningService;
import es.uma.sportjump.sjm.back.service.UserService;
import es.uma.sportjump.sjm.back.types.ApplicationStatusType;
import es.uma.sportjump.sjm.mobile.R;
import es.uma.sportjump.sjm.mobile.activities.fragments.TrainingDayDialogFragment;

@ContentView(R.layout.activity_home)
public class HomeActivity extends RoboFragmentActivity {
	
	protected static final String LOG_TAG = "[" + HomeActivity.class.getCanonicalName() + "]";
	
	@Inject UserService userService;
	@Inject PlanningService planningService;

	Activity mActivity;
	private boolean isDualPane;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = this;	
		isDualPane = getResources().getBoolean(R.bool.has_two_panes);		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		initCalendar();		
	}	

	
	private void initCalendar() {		
		
		
		CaldroidFragment caldroidFragment = new CaldroidFragment();
		Bundle args = new Bundle();
		Calendar cal = Calendar.getInstance();
		args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
		args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
		args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY);
		
		caldroidFragment.setArguments(args);
		
		cal.set(Calendar.DAY_OF_MONTH, 5);
		

		
		
		android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.frag_home_calendar, caldroidFragment);
		t.commit();
		
		caldroidFragment.refreshView();		
		
		caldroidFragment.setCaldroidListener(calListener);
		
		final CaldroidFragment caldroidFragmentHandler = caldroidFragment;
		final Handler handler = new Handler(Looper.getMainLooper()){
			
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				
				Bundle data = msg.getData();
				
				if (!data.containsKey(ServiceConstants.HANDLER_KEY_CANCEL)){
					if (data.containsKey(ServiceConstants.HANDLER_KEY_OBJECT)){
						
						CalendarEventResponse response = (CalendarEventResponse) data.getSerializable(ServiceConstants.HANDLER_KEY_OBJECT);
						
						
						if (ApplicationStatusType.STATUS_OK == response.getStatusType()){
						
							ArrayList<CalendarEventDto> listCalendarEvents = response.getListCalendarEvents();
							
							for (CalendarEventDto event : listCalendarEvents){
								caldroidFragmentHandler.setBackgroundResourceForDate(android.R.color.holo_orange_dark,event.getDateStart());
								caldroidFragmentHandler.setTextColorForDate(R.color.caldroid_white, event.getDateStart());
							}
							
							caldroidFragmentHandler.refreshView();
							
							if (isDualPane) {
								showTrainingDayFragment(planningService.findCalendarEvent(Calendar.getInstance().getTime()), Calendar.getInstance().getTime());
							}
						
						}else{
							ApplicationStatusType status = response.getStatusType();
							
							if (ApplicationStatusType.STATUS_UNAUTHORIZED == status){
								logout();
							}else if (ApplicationStatusType.STATUS_IO_ERROR == status){
								Toast.makeText(mActivity, getString(R.string.toast_http_error), Toast.LENGTH_LONG).show();
							}else{
								Toast.makeText(mActivity, getString(R.string.toast_internal_error), Toast.LENGTH_LONG).show();
							}
						}						
						
					}else{
						Toast.makeText(mActivity, getString(R.string.toast_internal_error), Toast.LENGTH_LONG).show();
					}					
				}				
			}			
		};
		planningService.findAllEvents(handler);

		if (isDualPane) {
			showTrainingDayFragment(planningService.findCalendarEvent(Calendar.getInstance().getTime()), Calendar.getInstance().getTime());
		}
	}
	
	private void showTrainingDayFragment(CalendarEventDto calendarEvent, Date date) {
		DialogFragment fragment = TrainingDayDialogFragment.newInstance(calendarEvent,date);
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Fragment previousFragment = getFragmentManager().findFragmentByTag("trainingDayFragment");
		if (previousFragment != null) {
			transaction.remove(previousFragment);
		}
		transaction.addToBackStack(null);
		transaction.replace(R.id.frag_home_training_day, fragment);
	
		transaction.commit();
		
	}

	private void showTrainingDayDialog(CalendarEventDto calendarEvent, Date date) {

	    FragmentTransaction transaction = getFragmentManager().beginTransaction();
	    Fragment previousFragment = getFragmentManager().findFragmentByTag("trainingDayFragmentDialog");
	    if (previousFragment != null) {
	        transaction.remove(previousFragment);
	    }
	    transaction.addToBackStack(null);

	    // Create and show the dialog.
	    DialogFragment newFragment = TrainingDayDialogFragment.newInstance(calendarEvent, date);
	    newFragment.show(transaction, "trainingDayFragmentDialog");
	}
	
	private void showTraining(CalendarEventDto calendarEvent, Date date) {
		if(isDualPane){
			showTrainingDayFragment(calendarEvent, date);
		}else{
			showTrainingDayDialog(calendarEvent, date);
		}
	}
	
	// Setup listener
	final CaldroidListener calListener = new CaldroidListener() {

		@Override
		public void onSelectDate(Date date, View view) {
			try{
			CalendarEventDto calendarEvent = planningService.findCalendarEvent(date);
			showTraining(calendarEvent, date);
			}catch (SportJumpBackException sportJumpBackException) {
				Toast.makeText(mActivity, getString(R.string.toast_db_error), Toast.LENGTH_LONG).show();
				Log.i(LOG_TAG, sportJumpBackException.toString());
			}
		}
	
	};
	

	
	
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.		
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.		
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}	
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Toast.makeText(this, "pulsado", Toast.LENGTH_LONG).show();
				return true;
			case R.id.action_refresh:
				initCalendar();
				return true;
			case R.id.action_settings:
				Toast.makeText(this, "Seteando", Toast.LENGTH_LONG).show();
				return true;
				
			case R.id.action_logout:
				logout();
			default:
	            return super.onOptionsItemSelected(item);
		}		
	}



	private void logout() {
		userService.logout();
		
		Intent intent = new Intent(mActivity,MainActivity.class);
		startActivity(intent);
		mActivity.finish();
	}
}
