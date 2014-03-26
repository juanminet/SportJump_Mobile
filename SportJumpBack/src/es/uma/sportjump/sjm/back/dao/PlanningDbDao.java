package es.uma.sportjump.sjm.back.dao;

import java.util.ArrayList;
import java.util.Date;

import es.uma.sportjump.sjm.back.dto.CalendarEventDto;
import es.uma.sportjump.sjm.back.responses.CalendarEventResponse;

public interface PlanningDbDao {

		public CalendarEventResponse getCalendarEvents() ;
		public CalendarEventResponse getCalendarEventsById(Long idUser);
		public CalendarEventResponse getCalendarEventByDate(Date date);
		
		public void setCalendarEvents(ArrayList<CalendarEventDto> listCalendarEvents);
}
