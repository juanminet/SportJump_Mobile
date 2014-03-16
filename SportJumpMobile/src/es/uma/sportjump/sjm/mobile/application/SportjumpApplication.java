package es.uma.sportjump.sjm.mobile.application;

import es.uma.sportjump.sjm.mobile.conf.roboguice.DaoRoboModule;
import es.uma.sportjump.sjm.mobile.conf.roboguice.ServiceRoboModule;
import roboguice.RoboGuice;
import android.app.Application;

public class SportjumpApplication extends Application{

	    @Override
	    public void onCreate() {
	        super.onCreate();

	        DaoRoboModule daoRoboModule = new DaoRoboModule();
	        ServiceRoboModule serviceRoboModule = new ServiceRoboModule();
	        RoboGuice.setBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE,RoboGuice.newDefaultRoboModule(this),
	        		daoRoboModule,
	        		serviceRoboModule
	        );
	    }

}
