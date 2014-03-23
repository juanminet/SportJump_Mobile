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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.inject.Inject;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import es.uma.sportjump.sjm.back.constants.ServiceConstants;
import es.uma.sportjump.sjm.back.dto.CalendarEventDto;
import es.uma.sportjump.sjm.back.dto.TrainingDto;
import es.uma.sportjump.sjm.back.service.PlanningService;
import es.uma.sportjump.sjm.back.service.UserService;
import es.uma.sportjump.sjm.back.service.types.ApplicationStatusType;
import es.uma.sportjump.sjm.mobile.R;
import es.uma.sportjump.sjm.mobile.activities.fragments.TrainingDayDialogFragment;

@ContentView(R.layout.activity_home)
public class HomeActivity extends RoboFragmentActivity {
	
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

		if (isDualPane) {
			showTrainingDayFragment(planningService.findCalendarEvent(Calendar.getInstance().getTime()));
		}
		
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
					if (data.containsKey(ServiceConstants.HANDLER_KEY_SUCCESS)){
						ArrayList<CalendarEventDto> listCalendarEvents = (ArrayList<CalendarEventDto>) data.getSerializable(ServiceConstants.HANDLER_KEY_OBJECT);
						
						for (CalendarEventDto event : listCalendarEvents){
							caldroidFragmentHandler.setBackgroundResourceForDate(android.R.color.holo_orange_dark,event.getDateStart());
							caldroidFragmentHandler.setTextColorForDate(R.color.caldroid_white, event.getDateStart());
						}
						
						caldroidFragmentHandler.refreshView();
					}else{
						ApplicationStatusType status = ApplicationStatusType.fromCode(data.getInt(ServiceConstants.HANDLER_KEY_STATUS));
						
						if (ApplicationStatusType.STATUS_UNAUTHORIZED == status){
							logout();
						}else{
							Toast.makeText(mActivity, "ERROR", Toast.LENGTH_LONG).show();
						}
					}					
				}				
			}			
		};
		planningService.findAllEvents(handler);

	}
	
	private void showTrainingDayFragment(CalendarEventDto calendarEvent) {
		DialogFragment fragment = TrainingDayDialogFragment.newInstance(calendarEvent);
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Fragment previousFragment = getFragmentManager().findFragmentByTag("trainingDayFragment");
		if (previousFragment != null) {
			transaction.remove(previousFragment);
		}
		transaction.addToBackStack(null);
		transaction.replace(R.id.frag_home_training_day, fragment);
	
		transaction.commit();
		
	}

	private void showTrainingDayDialog(CalendarEventDto calendarEvent) {

	    FragmentTransaction transaction = getFragmentManager().beginTransaction();
	    Fragment previousFragment = getFragmentManager().findFragmentByTag("trainingDayFragmentDialog");
	    if (previousFragment != null) {
	        transaction.remove(previousFragment);
	    }
	    transaction.addToBackStack(null);

	    // Create and show the dialog.
	    DialogFragment newFragment = TrainingDayDialogFragment.newInstance(calendarEvent);
	    newFragment.show(transaction, "trainingDayFragmentDialog");
	}
	
	private void showTraining(CalendarEventDto calendarEvent) {
		if(isDualPane){
			showTrainingDayFragment(calendarEvent);
		}else{
			showTrainingDayDialog(calendarEvent);
		}
	}
	
	// Setup listener
	final CaldroidListener calListener = new CaldroidListener() {

		@Override
		public void onSelectDate(Date date, View view) {
			CalendarEventDto calendarEvent = planningService.findCalendarEvent(date);
			showTraining(calendarEvent);
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
				Toast.makeText(this, "Refrescando", Toast.LENGTH_LONG).show();
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
