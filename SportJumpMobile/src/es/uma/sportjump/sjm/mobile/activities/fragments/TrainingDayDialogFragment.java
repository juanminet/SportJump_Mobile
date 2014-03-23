package es.uma.sportjump.sjm.mobile.activities.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import es.uma.sportjump.sjm.back.dto.CalendarEventDto;
import es.uma.sportjump.sjm.back.dto.ExerciseDto;
import es.uma.sportjump.sjm.back.dto.TrainingDto;
import es.uma.sportjump.sjm.mobile.R;
import es.uma.sportjump.sjm.mobile.constants.MobileConstants;

public class TrainingDayDialogFragment extends DialogFragment {
	
	CalendarEventDto calendarEvent;
  
    public static TrainingDayDialogFragment newInstance(CalendarEventDto calendarEvent) {
    	Bundle init = new Bundle();    	
    	init.putSerializable(MobileConstants.FRAGMENT_INIT_KEY_CALENDAR_EVENT, calendarEvent);
    	
    	TrainingDayDialogFragment fragment = new TrainingDayDialogFragment();     
    	fragment.setArguments(init);
    	
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (null == savedInstanceState) {
        	savedInstanceState = getArguments();         	
        }        	
        calendarEvent = (CalendarEventDto) savedInstanceState.get(MobileConstants.FRAGMENT_INIT_KEY_CALENDAR_EVENT);        	  
         
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_training_day, container, false);   
        
        String calendarEventText = null;
        
        if (calendarEvent != null){
        	calendarEventText = createCalendarEventText();
        }else{
        	calendarEventText = noEventText();
        }
        
       TextView textView = (TextView) view.findViewById(R.id.frag_training_day_text);
       textView.setText(Html.fromHtml(calendarEventText));

        return view;
    }

	private String createCalendarEventText() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<b><u>" + getResources().getString(R.string.frag_training_name) + "</u></b>: " + calendarEvent.getTitle() + "<br />");
		strBuffer.append("<b><u>" + getResources().getString(R.string.frag_training_date) + "</u></b>: " + calendarEvent.getStart() + "<br />");
		strBuffer.append("<b><u>" + getResources().getString(R.string.frag_training_description) + "</u></b>: " + calendarEvent.getTraining().getDescription() + "<br /><br />");
		strBuffer.append("<b><u>" + getResources().getString(R.string.frag_training_training) + "</u></b>:<br /><br />");
		TrainingDto training = calendarEvent.getTraining();
		
		for (ExerciseDto block : training.getListBlock()){
			strBuffer.append("&#8195;<b>" + block.getName() + "</b>:" + block.getDescription() + "<br />");
			
			for(String exercise : block.getListExercise()){
				strBuffer.append("&#8195;&#8195;&#8226; " + exercise + "<br />");
			}
			strBuffer.append("<br />");
		}
		
		return strBuffer.toString();
	}
	
	private String noEventText() {
		return getResources().getString(R.string.frag_training_no_training);
	}

}
