package edu.uga.cs.countryquiz;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CountryDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "country_quiz.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    // Table names and column names for countries table
    public static final String TABLE_COUNTRIES = "countries";
    public static final String COLUMN_COUNTRY_ID = "country_id";
    public static final String COLUMN_COUNTRY_NAME = "country_name";
    public static final String COLUMN_CONTINENT = "continent";

    // Table names and column names for quizzes table
    public static final String TABLE_QUIZZES = "quizzes";
    public static final String COLUMN_QUIZ_ID = "quiz_id";
    public static final String COLUMN_QUIZ_DATE = "quiz_date";
    public static final String COLUMN_SCORE = "score";
    private static CountryDBHelper helperInstance;

    // Create table statements
    private static final String CREATE_TABLE_COUNTRIES = "CREATE TABLE " + TABLE_COUNTRIES +
            "(" + COLUMN_COUNTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_COUNTRY_NAME + " TEXT," +
            COLUMN_CONTINENT + " TEXT)";

    private static final String CREATE_TABLE_QUIZZES = "CREATE TABLE " + TABLE_QUIZZES +
            "(" + COLUMN_QUIZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_QUIZ_DATE + " TEXT," +
            COLUMN_SCORE + " INTEGER)";

    public CountryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Access method to the single instance of the class.
    // It is synchronized, so that only one thread can executes this method, at a time.
    public static synchronized CountryDBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new CountryDBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }

    // Inside your DatabaseHelper class or a separate helper class
    // AsyncTask to populate countries from CSV
    private static class PopulateCountriesTask extends AsyncTask<Context, Void, Void> {
        @Override
        protected Void doInBackground(Context... contexts) {
            Context context = contexts[0];
            SQLiteDatabase db = CountryDBHelper.getInstance(context).getWritableDatabase();
            try {
                InputStream inputStream = context.getResources().openRawResource(R.raw.country_continent);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] parts = line.split(","); // Assuming CSV is comma-separated
                    String countryName = parts[0].trim(); // Assuming first column is country name
                    String continent = parts[1].trim(); // Assuming second column is continent
                    ContentValues values = new ContentValues();
                    values.put(COLUMN_COUNTRY_NAME, countryName);
                    values.put(COLUMN_CONTINENT, continent);
                    db.insert(TABLE_COUNTRIES, null, values);
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            // Handle post-execution tasks here
        }
    }
    // Method to start the AsyncTask
    public void populateCountriesFromCSVAsync(Context context) {
        new PopulateCountriesTask().execute(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating required tables
        db.execSQL(CREATE_TABLE_COUNTRIES);
        db.execSQL(CREATE_TABLE_QUIZZES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed, and create them again
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZES);

        // Create tables again
        onCreate(db);
    }
}

