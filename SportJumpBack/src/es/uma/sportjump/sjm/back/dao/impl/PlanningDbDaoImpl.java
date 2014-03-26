package es.uma.sportjump.sjm.back.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.inject.Inject;

import es.uma.sportjump.sjm.back.dao.PlanningDbDao;
import es.uma.sportjump.sjm.back.dao.provider.PlanningContract;
import es.uma.sportjump.sjm.back.dao.provider.PlanningProvider;
import es.uma.sportjump.sjm.back.dto.CalendarEventDto;
import es.uma.sportjump.sjm.back.dto.TrainingDto;
import es.uma.sportjump.sjm.back.responses.CalendarEventResponse;
import es.uma.sportjump.sjm.back.types.ApplicationStatusType;

public class PlanningDbDaoImpl implements PlanningDbDao{
	
	@Inject Application application;

	@Override
	public CalendarEventResponse getCalendarEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CalendarEventResponse getCalendarEventsById(Long idUser) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CalendarEventResponse getCalendarEventByDate(Date date) {
		
		ApplicationStatusType status = ApplicationStatusType.STATUS_OK;
		
		PlanningProvider mDbHelper = new PlanningProvider(application);
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		String[] projection = {
			PlanningContract.PlanningEntry._ID,
		    PlanningContract.PlanningEntry.COLUMN_NAME_TITLE,
		    PlanningContract.PlanningEntry.COLUMN_NAME_DATE,
		    PlanningContract.PlanningEntry.COLUMN_NAME_TRAINING
		};
		
		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd", application.getResources().getConfiguration().locale); 
		String selection = PlanningContract.PlanningEntry.COLUMN_NAME_DATE + " LIKE ? ";
		
		String[] selectionArgs ={dt.format(date)};
		// How you want the results sorted in the resulting Cursor
		String sortOrder = PlanningContract.PlanningEntry._ID + " DESC";

		Cursor cursor = null;
		CalendarEventDto calendarEventDto = null;	
		try{
			cursor = db.query(
		
			    PlanningContract.PlanningEntry.TABLE_NAME,  // The table to query
			    projection,                               // The columns to return
			    selection,                                // The columns for the WHERE clause
			    selectionArgs,                            // The values for the WHERE clause
			    null,                                     // don't group the rows
			    null,                                     // don't filter by row groups
			    sortOrder                                 // The sort order
			    );
			
		
			
			cursor.moveToFirst();
	        while(cursor.isAfterLast() == false) {
	             calendarEventDto = new CalendarEventDto();
	            
	            String title = cursor.getString(cursor.getColumnIndex(PlanningContract.PlanningEntry.COLUMN_NAME_TITLE));
	            String start = cursor.getString(cursor.getColumnIndex(PlanningContract.PlanningEntry.COLUMN_NAME_DATE));
	            String trainingJson = cursor.getString(cursor.getColumnIndex(PlanningContract.PlanningEntry.COLUMN_NAME_TRAINING));
	            
	            calendarEventDto.setTitle(title);
	            calendarEventDto.setStart(start);
	            
	            TrainingDto training = TrainingDto.objectfromJson(trainingJson);
	            calendarEventDto.setTraining(training);
	            
	            
	            cursor.moveToNext();			               
	        }        
		}catch(Exception exception){
			status = ApplicationStatusType.STATUS_ERROR;
		}finally{
	        cursor.close();
	        mDbHelper.close();
		}
		
        
        CalendarEventResponse response = new CalendarEventResponse();
        response.setCalendarEvent(calendarEventDto);
        response.setStatusType(status);
        
		return response;
	}

	@Override
	public void setCalendarEvents(ArrayList<CalendarEventDto> listCalendarEvents) {
		
		synchronized (listCalendarEvents) {		
		
			PlanningProvider mDbHelper = new PlanningProvider(application);
			
			
			// Gets the data repository in write mode
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			
			db.delete( PlanningContract.PlanningEntry.TABLE_NAME, null, null);
	
			// Create a new map of values, where column names are the keys
			Gson gson = new Gson();
			
			for (CalendarEventDto event : listCalendarEvents){
				ContentValues values = new ContentValues();
				values.put(PlanningContract.PlanningEntry.COLUMN_NAME_TITLE, event.getTitle());
				values.put(PlanningContract.PlanningEntry.COLUMN_NAME_DATE, event.getStart());		
				values.put(PlanningContract.PlanningEntry.COLUMN_NAME_TRAINING, gson.toJson(event.getTraining()));
	
			
				// Insert the new row, returning the primary key value of the new row
				db.insert(
				         PlanningContract.PlanningEntry.TABLE_NAME,
				         PlanningContract.PlanningEntry.COLUMN_NAME_NULLABLE,
				         values);
				
			}
			
			mDbHelper.close();
		}
	}



	
	
	

}
