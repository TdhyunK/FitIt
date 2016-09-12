package me.thomasdkim.fitit.workout;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tdhyunk on 8/22/16.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1; //How do I check the DATABASE_VERSION number?
    public static final String DATABASE_NAME = "workout_log.db";
    public static final String TABLE_NAME = "workouts";
    public static final String COL_1 = "exercise";
    public static final String COL_2 = "date";
    public static final String COL_3 = "reps";
    public static final String COL_4 = "set";
    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "(" + COL_1 + " TEXT NOT NULL, " +
            COL_2 + " TEXT NOT NULL," + COL_3 + " TEXT NOT NULL, " + COL_4 + " TEXT NOT NULL" + ")";
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    //Constructor
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + i + " to version " + i1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DELETE_TABLE);
        onCreate(sqLiteDatabase);

    }
}