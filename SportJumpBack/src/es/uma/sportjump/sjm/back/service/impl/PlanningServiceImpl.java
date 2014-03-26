package es.uma.sportjump.sjm.back.service.impl;

import java.util.ArrayList;
import java.util.Date;

import android.os.AsyncTask;
import android.os.Handler;

import com.google.inject.Inject;

import es.uma.sportjump.sjm.back.dao.PlanningDbDao;
import es.uma.sportjump.sjm.back.dao.PlanningHttpDao;
import es.uma.sportjump.sjm.back.dto.CalendarEventDto;
import es.uma.sportjump.sjm.back.exceptions.SportJumpBackException;
import es.uma.sportjump.sjm.back.responses.CalendarEventResponse;
import es.uma.sportjump.sjm.back.service.PlanningService;
import es.uma.sportjump.sjm.back.types.ApplicationStatusType;

public class PlanningServiceImpl extends BaseService implements PlanningService{

	@Inject PlanningHttpDao planningDao;
	@Inject PlanningDbDao planningDbDao;	

	ArrayList<CalendarEventDto> listEvents;
	
	
	@Override
	public void findAllEvents(Handler uiHandler) {
		PlanningTask authTask = null;
		authTask = new PlanningTask(uiHandler);
		authTask.execute();		
		
	}
	
	
public class PlanningTask extends AsyncTask<String, Void, CalendarEventResponse> {
		
		private Handler uiHandler;
		
		public PlanningTask(Handler handler){
			this.uiHandler = handler;			
		}	
		

		@Override
		protected CalendarEventResponse doInBackground(String... params) {				
			return  planningDao.getCalendarEvents();	
		}

	

		@Override
		protected void onPostExecute(final CalendarEventResponse response) {			
		
			notifyUi(uiHandler, response);
			
			//Store database
			
			if (response != null && response.getStatusType() != null && response.getStatusType() == ApplicationStatusType.STATUS_OK){			
				planningDbDao.setCalendarEvents(response.getListCalendarEvents());
			}
		}

		@Override
		protected void onCancelled() {
			notifyUiCancel(uiHandler);
		}		
	}

	
//try{
// 	Cursor eventCursor = planningDbDao.getCalendarEvents();
//    if(eventCursor != null){
//    	listCalendarEventDto = new ArrayList<CalendarEventDto>();
//    	
//    	eventCursor.moveToFirst();			            
//        while(eventCursor.isAfterLast() == false) {
//            CalendarEventDto calendarEventDto = new CalendarEventDto();
//            
//            String title = eventCursor.getString(eventCursor.getColumnIndex(PlanningContract.PlanningEntry.COLUMN_NAME_TITLE));
//            String start = eventCursor.getString(eventCursor.getColumnIndex(PlanningContract.PlanningEntry.COLUMN_NAME_DATE));
//            String trainingJson = eventCursor.getString(eventCursor.getColumnIndex(PlanningContract.PlanningEntry.COLUMN_NAME_TRAINING));
//            
//            calendarEventDto.setTitle(title);
//            calendarEventDto.setStart(start);
//            
//            TrainingDto training = TrainingDto.objectfromJson(trainingJson);
//            calendarEventDto.setTraining(training);
//        	
//            listCalendarEventDto.add(calendarEventDto);
//            
//            eventCursor.moveToNext();			               
//        }
//    }
//    
//    eventCursor.close();
//} catch(SQLException e)  {
//    e.printStackTrace();
//}
//
//if (listCalendarEventDto != null && listCalendarEventDto.size() > 0){
//	handleResponse(listCalendarEventDto, ApplicationStatusType.STATUS_OK.getCode());
//}


//	@Override
//	public CalendarEventDto findCalendarEvent(Date date) {
//		CalendarEventDto res = null;
//		
//		if (listEvents != null){
//			for(CalendarEventDto event : listEvents){
//				if (BackUtils.isDateEquals(date, event.getDateStart())){
//					res = event;
//				}
//			}
//		}
//		
//		return res;
//	}
	
	@Override
	public CalendarEventDto findCalendarEvent(Date date)  throws SportJumpBackException{
		
		CalendarEventResponse response = planningDbDao.getCalendarEventByDate(date);
		
		if (response.getStatusType() != ApplicationStatusType.STATUS_OK){
			throw new SportJumpBackException("ERROR accessing Sqlite");
		}
		
		return response.getCalendarEvent();
	}

}
