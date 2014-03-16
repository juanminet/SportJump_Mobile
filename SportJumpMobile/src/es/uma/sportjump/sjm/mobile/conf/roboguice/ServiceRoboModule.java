package es.uma.sportjump.sjm.mobile.conf.roboguice;

import com.google.inject.Binder;
import com.google.inject.Module;

import es.uma.sportjump.sjm.back.service.UserService;
import es.uma.sportjump.sjm.back.service.impl.UserServiceImpl;

public class ServiceRoboModule implements Module{

	@Override
	public void configure(Binder binder) {
		binder.bind(UserService.class).to(UserServiceImpl.class);
	}

}
