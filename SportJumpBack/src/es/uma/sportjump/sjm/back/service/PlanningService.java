package es.uma.sportjump.sjm.back.service;

import java.util.Date;

import android.os.Handler;
import es.uma.sportjump.sjm.back.dto.CalendarEventDto;

public interface PlanningService {
	
	public void findAllEvents(Handler uiHandler);
	
	public CalendarEventDto findCalendarEvent(Date date);

}
