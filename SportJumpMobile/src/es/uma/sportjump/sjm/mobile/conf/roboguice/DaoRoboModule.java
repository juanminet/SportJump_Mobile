package es.uma.sportjump.sjm.mobile.conf.roboguice;

import com.google.inject.Binder;
import com.google.inject.Module;

import es.uma.sportjump.sjm.back.dao.ApplicationPreferencesDao;
import es.uma.sportjump.sjm.back.dao.UserDao;
import es.uma.sportjump.sjm.back.dao.impl.ApplicationPreferencesDaoImpl;
import es.uma.sportjump.sjm.back.dao.impl.UserDaoImpl;

public class DaoRoboModule implements Module{

	@Override
	public void configure(Binder binder) {
		binder.bind(ApplicationPreferencesDao.class).to(ApplicationPreferencesDaoImpl.class);
		binder.bind(UserDao.class).to(UserDaoImpl.class);
	}

}
