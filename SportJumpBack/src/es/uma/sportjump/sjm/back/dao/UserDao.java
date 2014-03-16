package es.uma.sportjump.sjm.back.dao;

import java.io.IOException;

import es.uma.sportjump.sjm.back.dao.http.service.responses.HttpResponseObject;

public interface UserDao {

	public HttpResponseObject login(String username, String password) throws IOException;
	public HttpResponseObject getUserData(String username);
}
