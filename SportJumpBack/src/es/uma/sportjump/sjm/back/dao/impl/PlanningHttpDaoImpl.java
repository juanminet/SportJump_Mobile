package es.uma.sportjump.sjm.back.dao.impl;

import java.io.IOException;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

import es.uma.sportjump.sjm.back.constants.DaoConstans;
import es.uma.sportjump.sjm.back.dao.CredentialsDao;
import es.uma.sportjump.sjm.back.dao.PlanningHttpDao;
import es.uma.sportjump.sjm.back.dao.http.service.HttpClientService;
import es.uma.sportjump.sjm.back.dao.http.service.responses.HttpResponseObject;
import es.uma.sportjump.sjm.back.dto.CalendarEventDto;
import es.uma.sportjump.sjm.back.dto.UserPasswordDto;
import es.uma.sportjump.sjm.back.responses.CalendarEventResponse;
import es.uma.sportjump.sjm.back.types.ApplicationStatusType;

public class PlanningHttpDaoImpl implements PlanningHttpDao{
	
	@Inject HttpClientService httpClientService;
	
	@Inject CredentialsDao credentialsDao;

	@Override
	public CalendarEventResponse getCalendarEvents(){		
		
		UserPasswordDto userPasswordDto = credentialsDao.getUserPassord();
		
		String serverUrl = DaoConstans.SECURE_URL_BASE_PATH + DaoConstans.PLANNING_ATHLETE_DATA_PATH + "/username/" + userPasswordDto.getUsername();		
		
		return executeHttpGetCalendarEvents(serverUrl, userPasswordDto.getUsername(), userPasswordDto.getPassword());		
	}	

	@Override
	public CalendarEventResponse getCalendarEventsById(Long idUser) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	
	private CalendarEventResponse executeHttpGetCalendarEvents(	String serverUrl, String username, String password) {
		CalendarEventResponse response  = null;		
		HttpResponseObject httpResponseObject = null;
		try {
			httpResponseObject = httpClientService.executeHttpClientGet(serverUrl, username, password);
		} catch (IOException e) {
			response = new CalendarEventResponse();
			response.setStatusType(ApplicationStatusType.STATUS_IO_ERROR);
		}
		
		if (response == null){
			response = fromHttpResponseObject(httpResponseObject);
		}
		return response;
	}
	
	private CalendarEventResponse fromHttpResponseObject (HttpResponseObject httpResponseObject){
		CalendarEventResponse response = new CalendarEventResponse();
		
		if (httpResponseObject != null && httpResponseObject.getStatus() != -1){
			if (HttpStatus.SC_OK == httpResponseObject.getStatus() && httpResponseObject.getJson() != null){
				response.setListCalendarEvents(CalendarEventDto.listfromJson(httpResponseObject.getJson()));
			}
			response.setStatusType(ApplicationStatusType.fromCode(httpResponseObject.getStatus()));
		}else{
			response.setStatusType(ApplicationStatusType.STATUS_ERROR);
		}
		
		return response;
	}

}
