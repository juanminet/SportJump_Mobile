package es.uma.sportjump.sjm.back.dao.impl;

import java.io.IOException;

import com.google.inject.Inject;

import es.uma.sportjump.sjm.back.constants.DaoConstans;
import es.uma.sportjump.sjm.back.dao.CredentialsDao;
import es.uma.sportjump.sjm.back.dao.PlanningDao;
import es.uma.sportjump.sjm.back.dao.http.service.HttpClientService;
import es.uma.sportjump.sjm.back.dao.http.service.responses.HttpResponseObject;
import es.uma.sportjump.sjm.back.dto.UserPasswordDto;

public class PlanningDaoImpl implements PlanningDao{
	
	@Inject HttpClientService httpClientService;
	
	@Inject CredentialsDao credentialsDao;

	@Override
	public HttpResponseObject getCalendarEvents() throws IOException {		
		
		UserPasswordDto userPasswordDto = credentialsDao.getUserPassord();
		
		String serverUrl = DaoConstans.SECURE_URL_BASE_PATH + DaoConstans.PLANNING_ATHLETE_DATA_PATH + "/username/" + userPasswordDto.getUsername();		
		
		return httpClientService.executeHttpClientGet(serverUrl, userPasswordDto.getUsername(), userPasswordDto.getPassword());
		
	}
	@Override
	public HttpResponseObject getCalendarEventsById(Long idUser) {
		// TODO Auto-generated method stub
		return null;
	}

}
