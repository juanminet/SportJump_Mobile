package es.uma.sportjump.sjm.back.dao;

import java.io.IOException;

import es.uma.sportjump.sjm.back.dao.http.service.responses.HttpResponseObject;

public interface PlanningDao {

		public HttpResponseObject getCalendarEvents() throws IOException;
		public HttpResponseObject getCalendarEventsById(Long idUser) throws IOException;
}
