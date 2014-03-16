package es.uma.sportjump.sjm.back.dao.impl;

import java.io.IOException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import es.uma.sportjump.sjm.back.constants.DaoConstans;
import es.uma.sportjump.sjm.back.dao.UserDao;
import es.uma.sportjump.sjm.back.dao.http.service.HttpClientService;
import es.uma.sportjump.sjm.back.dao.http.service.responses.HttpResponseObject;

@Singleton
public class UserDaoImpl implements UserDao {

	@Inject HttpClientService httpClientService;
	
	@Override
	public HttpResponseObject login(String username, String password) throws IOException {
		
		String serverUrl = DaoConstans.SECURE_URL_BASE_PATH + DaoConstans.USER_ATHLETE_DATA_PATH + "/" + username;		
		
		return httpClientService.executeHttpClientGet(serverUrl, username, password);
	}

	@Override
	public HttpResponseObject getUserData(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
