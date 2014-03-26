package es.uma.sportjump.sjm.back.responses;

import java.util.ArrayList;

import es.uma.sportjump.sjm.back.dto.CalendarEventDto;

public class CalendarEventResponse extends BaseResponse {

	private static final long serialVersionUID = -2213273205024865824L;
	
	private ArrayList<CalendarEventDto> listCalendarEvents;
	private CalendarEventDto calendarEvent;
	
	public ArrayList<CalendarEventDto> getListCalendarEvents() {
		return listCalendarEvents;
	}
	public void setListCalendarEvents(ArrayList<CalendarEventDto> listCalendarEvents) {
		this.listCalendarEvents = listCalendarEvents;
	}
	public CalendarEventDto getCalendarEvent() {
		return calendarEvent;
	}
	public void setCalendarEvent(CalendarEventDto calendarEvent) {
		this.calendarEvent = calendarEvent;
	}
	
	
}
