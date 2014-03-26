package es.uma.sportjump.sjm.back.service.impl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import es.uma.sportjump.sjm.back.constants.ServiceConstants;
import es.uma.sportjump.sjm.back.responses.Response;

public class BaseService {

	void notifyUi(Handler uiHandler, Response response){
		Bundle data = new Bundle();
		data.putSerializable(ServiceConstants.HANDLER_KEY_OBJECT, response);
					
		Message msg = new Message();
		msg.setData(data);
		
		uiHandler.handleMessage(msg);	
	}
	
	void notifyUiCancel(Handler uiHandler){
		Bundle data = new Bundle();
		data.putBoolean(ServiceConstants.HANDLER_KEY_CANCEL, true);
		
		Message msg = new Message();
		msg.setData(data);
		
		uiHandler.handleMessage(msg);
	}
}
