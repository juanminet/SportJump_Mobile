package es.uma.sportjump.sjm.back.dao.impl;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import es.uma.sportjump.sjm.back.constants.DaoConstans;
import es.uma.sportjump.sjm.back.dao.CredentialsDao;
import es.uma.sportjump.sjm.back.dto.UserPasswordDto;

@Singleton
public class CredentialsDaoImpl implements CredentialsDao{	
	@Inject Application application;	

	@Override
	public boolean isUserLogged() {
		boolean res = false;
		SharedPreferences preferences = application.getApplicationContext()
				.getSharedPreferences(DaoConstans.APPLICATION_PREFERENCE_NAME,  Context.MODE_PRIVATE);
		
		if (preferences.contains(DaoConstans.APPLICATION_PREFERENCE_KEY_USER_LOGGED) && 
			preferences.getBoolean(DaoConstans.APPLICATION_PREFERENCE_KEY_USER_LOGGED, false)){
			res = true;
		}
		
		return res;	
	}

	@Override
	public void addUserPassword(String username, String password) {
		SharedPreferences preferences = application.getApplicationContext()
				.getSharedPreferences(DaoConstans.APPLICATION_PREFERENCE_NAME,  Context.MODE_PRIVATE);
		
		SharedPreferences.Editor edit = preferences.edit();
		
		edit.putBoolean(DaoConstans.APPLICATION_PREFERENCE_KEY_USER_LOGGED, true);
		edit.putString(DaoConstans.APPLICATION_PREFERENCE_KEY_USERNAME, username);
		edit.putString(DaoConstans.APPLICATION_PREFERENCE_KEY_PASSWORD, password);
		
		edit.commit();		
	}

	@Override
	public UserPasswordDto getUserPassord() {
		SharedPreferences preferences = application.getApplicationContext()
				.getSharedPreferences(DaoConstans.APPLICATION_PREFERENCE_NAME,  Context.MODE_PRIVATE);
		
		UserPasswordDto userPasswordDto = null;
		
		if (preferences.contains(DaoConstans.APPLICATION_PREFERENCE_KEY_USER_LOGGED) && 
			preferences.getBoolean(DaoConstans.APPLICATION_PREFERENCE_KEY_USER_LOGGED, true)){
			
			userPasswordDto = new UserPasswordDto();
			userPasswordDto.setUsername(preferences.getString(DaoConstans.APPLICATION_PREFERENCE_KEY_USERNAME, null));
			userPasswordDto.setPassword(preferences.getString(DaoConstans.APPLICATION_PREFERENCE_KEY_PASSWORD, null));
			
		}
		
		return userPasswordDto;
	}

	@Override
	public void deleteUserPassword() {
		SharedPreferences preferences = application.getApplicationContext()
				.getSharedPreferences(DaoConstans.APPLICATION_PREFERENCE_NAME,  Context.MODE_PRIVATE);
		
		SharedPreferences.Editor edit = preferences.edit();
		
		edit.remove(DaoConstans.APPLICATION_PREFERENCE_KEY_USER_LOGGED);
		edit.remove(DaoConstans.APPLICATION_PREFERENCE_KEY_USERNAME);
		edit.remove(DaoConstans.APPLICATION_PREFERENCE_KEY_PASSWORD);
		
		edit.commit();		
	}

}
