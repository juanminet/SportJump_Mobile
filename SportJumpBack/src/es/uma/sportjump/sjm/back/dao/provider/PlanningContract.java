package es.uma.sportjump.sjm.back.dao.provider;

import android.provider.BaseColumns;

public final class PlanningContract {

	public PlanningContract(){		
	}
	
	public static class PlanningEntry implements BaseColumns{		
		public static final String TABLE_NAME = "planning";
		public static final String COLUMN_NAME_ID = "_id";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_DATE = "date";
		public static final String COLUMN_NAME_TRAINING = "training";
		public static final String COLUMN_NAME_NULLABLE = null;
	}
}
