package es.uma.sportjump.sjm.back.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpStatus;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.inject.Inject;

import es.uma.sportjump.sjm.back.constants.ServiceConstants;
import es.uma.sportjump.sjm.back.dao.PlanningDao;
import es.uma.sportjump.sjm.back.dao.http.service.responses.HttpResponseObject;
import es.uma.sportjump.sjm.back.dto.CalendarEventDto;
import es.uma.sportjump.sjm.back.service.PlanningService;
import es.uma.sportjump.sjm.back.service.types.ApplicationStatusType;
import es.uma.sportjump.sjm.back.utils.BackUtils;

public class PlanningServiceImpl implements PlanningService{

	@Inject PlanningDao planningDao;
	
	

	ArrayList<CalendarEventDto> listEvents;
	
	
	@Override
	public void findAllEvents(Handler uiHandler) {
		PlanningTask authTask = null;
		authTask = new PlanningTask(uiHandler);
		authTask.execute();		
		
	}
	
	
public class PlanningTask extends AsyncTask<String, Void, HttpResponseObject> {
		
		private Handler uiHandler;
		public PlanningTask(Handler handler){
			this.uiHandler = handler;			
		}	
		

		@Override
		protected HttpResponseObject doInBackground(String... params) {			
			
			HttpResponseObject responseObject = null;
			try {
				responseObject = planningDao.getCalendarEvents();				
			} catch (IOException ioException){
				responseObject = new HttpResponseObject(ApplicationStatusType.STATUS_IO_ERROR.getCode());				
			}		
			
			return responseObject;
		}

	

		@Override
		protected void onPostExecute(final HttpResponseObject responseObject) {
			Bundle data = new Bundle();
			
		
			if (HttpStatus.SC_OK == responseObject.getStatus()){
				data.putBoolean(ServiceConstants.HANDLER_KEY_SUCCESS, false);
				data.putInt(ServiceConstants.HANDLER_KEY_STATUS, ApplicationStatusType.STATUS_OK.getCode());
				
				CalendarEventDto calendarEventDto = new CalendarEventDto();				
				ArrayList<CalendarEventDto> listCalendarEventDto = calendarEventDto.listfromJson(responseObject.getJson());	
				
				

				listEvents = listCalendarEventDto;
				
				data.putSerializable(ServiceConstants.HANDLER_KEY_OBJECT, listCalendarEventDto);				
				
			}else if (ApplicationStatusType.STATUS_IO_ERROR.getCode() == responseObject.getStatus()){
				data.putInt(ServiceConstants.HANDLER_KEY_STATUS, ApplicationStatusType.STATUS_IO_ERROR.getCode());
			}else{				
				data.putInt(ServiceConstants.HANDLER_KEY_STATUS, ApplicationStatusType.fromCode(responseObject.getStatus()).getCode());
			}
						
			Message msg = new Message();
			msg.setData(data);
			
			uiHandler.handleMessage(msg);			
		}

		@Override
		protected void onCancelled() {
			Bundle data = new Bundle();
			data.putBoolean(ServiceConstants.HANDLER_KEY_CANCEL, true);
			
			Message msg = new Message();
			msg.setData(data);
			
			uiHandler.handleMessage(msg);
		}
	}


	@Override
	public CalendarEventDto findCalendarEvent(Date date) {
		CalendarEventDto res = null;
		
		if (listEvents != null){
			for(CalendarEventDto event : listEvents){
				if (BackUtils.isDateEquals(date, event.getDateStart())){
					res = event;
				}
			}
		}
		
		return res;
	}

}
