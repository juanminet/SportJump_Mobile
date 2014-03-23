package es.uma.sportjump.sjm.back.service;

import android.os.Handler;

public interface UserService {
	
	public boolean isUserLogged();
	public void loginUser(String username, String password, Handler uiHandler);
	public void logout();

}
