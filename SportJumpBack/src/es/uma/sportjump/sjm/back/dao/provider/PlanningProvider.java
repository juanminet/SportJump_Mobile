package es.uma.sportjump.sjm.back.dao.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlanningProvider extends SQLiteOpenHelper{

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + PlanningContract.PlanningEntry.TABLE_NAME + " ( " +
		    PlanningContract.PlanningEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
		    PlanningContract.PlanningEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
		    PlanningContract.PlanningEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
		    PlanningContract.PlanningEntry.COLUMN_NAME_TRAINING + TEXT_TYPE + 
	    " )";
	private static final String SQL_DELETE_ENTRIES =
		    "DROP TABLE IF EXISTS " + PlanningContract.PlanningEntry.TABLE_NAME;

	
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Planning.db";
	
	public PlanningProvider(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);			
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);			
	
	}

}
