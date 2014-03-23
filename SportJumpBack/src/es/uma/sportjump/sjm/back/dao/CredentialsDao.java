package es.uma.sportjump.sjm.back.dao;

import es.uma.sportjump.sjm.back.dto.UserPasswordDto;


public interface CredentialsDao {	
	public boolean isUserLogged();
	public void addUserPassword(String username, String password);
	public  UserPasswordDto getUserPassord();
	public void deleteUserPassword();
}
