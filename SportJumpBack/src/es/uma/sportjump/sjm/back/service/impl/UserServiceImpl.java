package es.uma.sportjump.sjm.back.service.impl;

import java.io.IOException;

import org.apache.http.HttpStatus;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import es.uma.sportjump.sjm.back.constants.ServiceConstants;
import es.uma.sportjump.sjm.back.dao.CredentialsDao;
import es.uma.sportjump.sjm.back.dao.UserDao;
import es.uma.sportjump.sjm.back.dao.http.service.responses.HttpResponseObject;
import es.uma.sportjump.sjm.back.service.UserService;
import es.uma.sportjump.sjm.back.types.ApplicationStatusType;


@Singleton
public class UserServiceImpl implements UserService {

	@Inject CredentialsDao credentialsDao;
	@Inject UserDao userDao;
	
	@Override
	public boolean isUserLogged() {		
		return credentialsDao.isUserLogged();		
	}
	
	@Override
	public void logout() {
		credentialsDao.deleteUserPassword();		
	}

	@Override
	public void loginUser(String username, String password, Handler uiHandler) {
		UserLoginTask authTask = null;
		authTask = new UserLoginTask(uiHandler);
		authTask.execute(username,password);		
	}
	
	
	
	public class UserLoginTask extends AsyncTask<String, Void, Integer> {
		
		private Handler uiHandler;
		public UserLoginTask(Handler handler){
			this.uiHandler = handler;			
		}	
		

		@Override
		protected Integer doInBackground(String... params) {
			
			String username = params[0];
			String password = params[1];
			
			HttpResponseObject responseObject;
			try {
				responseObject = userDao.login(username, password);				
			} catch (IOException ioException){
				return ApplicationStatusType.STATUS_IO_ERROR.getCode();
			}

			credentialsDao.addUserPassword(username, password);
			
			// TODO: register the new account here.
			return responseObject.getStatus();
		}

	

		@Override
		protected void onPostExecute(final Integer status) {
			Bundle data = new Bundle();
			
			boolean success = false;
			if (HttpStatus.SC_OK == status){
				success = true;
			}
			
			data.putBoolean(ServiceConstants.HANDLER_KEY_SUCCESS, success);
			data.putInt(ServiceConstants.HANDLER_KEY_STATUS, ApplicationStatusType.fromCode(status).getCode());
			
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





}
