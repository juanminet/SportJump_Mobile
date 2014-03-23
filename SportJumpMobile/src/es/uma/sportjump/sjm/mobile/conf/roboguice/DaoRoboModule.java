package es.uma.sportjump.sjm.mobile.conf.roboguice;

import com.google.inject.Binder;
import com.google.inject.Module;

import es.uma.sportjump.sjm.back.dao.CredentialsDao;
import es.uma.sportjump.sjm.back.dao.PlanningDao;
import es.uma.sportjump.sjm.back.dao.UserDao;
import es.uma.sportjump.sjm.back.dao.impl.CredentialsDaoImpl;
import es.uma.sportjump.sjm.back.dao.impl.PlanningDaoImpl;
import es.uma.sportjump.sjm.back.dao.impl.UserDaoImpl;

public class DaoRoboModule implements Module{
	
	public void configure(Binder binder) {
		binder.bind(CredentialsDao.class).to(CredentialsDaoImpl.class);
		binder.bind(UserDao.class).to(UserDaoImpl.class);
		binder.bind(PlanningDao.class).to(PlanningDaoImpl.class);
	}
}
