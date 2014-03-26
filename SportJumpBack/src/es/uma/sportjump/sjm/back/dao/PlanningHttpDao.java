package es.uma.sportjump.sjm.back.dao;

import es.uma.sportjump.sjm.back.responses.CalendarEventResponse;

public interface PlanningHttpDao {

		public CalendarEventResponse getCalendarEvents();
		public CalendarEventResponse getCalendarEventsById(Long idUser);
}
