package edu.uga.cs.countryquiz;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CountryDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "country_quiz.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_COUNTRIES = "countries";
    private static final String TABLE_QUIZZES = "quizzes";

    // Common column names
    private static final String KEY_ID = "id";

    // Countries Table - column names
    private static final String KEY_COUNTRY_NAME = "country_name";
    private static final String KEY_CONTINENT = "continent";

    // Quizzes Table - column names
    private static final String KEY_QUIZ_DATE = "quiz_date";
    private static final String KEY_QUIZ_RESULT = "quiz_result";

    // Create table statements
    private static final String CREATE_TABLE_COUNTRIES = "CREATE TABLE " + TABLE_COUNTRIES +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_COUNTRY_NAME + " TEXT," +
            KEY_CONTINENT + " TEXT)";

    private static final String CREATE_TABLE_QUIZZES = "CREATE TABLE " + TABLE_QUIZZES +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_QUIZ_DATE + " TEXT," +
            KEY_QUIZ_RESULT + " INTEGER)";

    public CountryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Inside your DatabaseHelper class or a separate helper class
    public void populateCountriesFromCSV(Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            InputStream inputStream = context.getAssets().open("country_data.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(","); // Assuming CSV is comma-separated
                String countryName = parts[0].trim(); // Assuming first column is country name
                String continent = parts[1].trim(); // Assuming second column is continent
                ContentValues values = new ContentValues();
                values.put(KEY_COUNTRY_NAME, countryName);
                values.put(KEY_CONTINENT, continent);
                db.insert(TABLE_COUNTRIES, null, values);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

