package es.uma.sportjump.sjm.back.dto;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CalendarEventDto  implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4458503939316998924L;
	
	private Double id;
	private String title;
	private String start;	
	private TrainingDto training;
	
	
	
	public CalendarEventDto() {
		super();		
	}
	

	public void setId(Double id) {
		this.id = id;
	}


	public Double getId() {
		return id;
	}


	public String getStart() {
		return start;
	}


	public void setStart(String start) {
		this.start = start;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public Date getDateStart() {		
		
		String[] fields = start.split("-");
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(fields[2]));
		cal.set(Calendar.MONTH, Integer.valueOf(fields[1]).intValue() - 1);
		cal.set(Calendar.YEAR, Integer.valueOf(fields[0]));				
		
		return cal.getTime();
	}
	
	public TrainingDto getTraining() {
		return training;
	}


	public void setTraining(TrainingDto training) {
		this.training = training;
	}


	public CalendarEventDto objectfromJson(String json) {
		final Gson gson = new Gson();		
		final CalendarEventDto calendarEvent = gson.fromJson(json, CalendarEventDto.class);

		return calendarEvent;
	}

	public ArrayList<CalendarEventDto> listfromJson(String json) {
		final Gson gson = new Gson();
		final Type trainingListType = new TypeToken<List<CalendarEventDto>>() {}.getType();
		final ArrayList<CalendarEventDto> trainingList = gson.fromJson(json, trainingListType);

		return trainingList;
	}
	
	
}
