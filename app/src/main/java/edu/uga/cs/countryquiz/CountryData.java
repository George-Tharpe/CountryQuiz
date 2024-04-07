package edu.uga.cs.countryquiz;
import static java.lang.Long.parseLong;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CountryData {

    public static final String DEBUG_TAG = "CountryData";

    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;
    private static final String[] allColumns = {
            CountryDBHelper.COLUMN_COUNTRY_ID,
            CountryDBHelper.COLUMN_COUNTRY_NAME,
            CountryDBHelper.COLUMN_CONTINENT
    };

    public CountryData(Context context) {
        dbHelper = CountryDBHelper.getInstance(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
        Log.d(DEBUG_TAG, "CountryData: db open");
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
            Log.d(DEBUG_TAG, "CountryData: db closed");
        }
    }

    public boolean isDBOpen() {
        return db.isOpen();
    }

    public List<Country> retrieveAllCountries() {
        ArrayList<Country> countries = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.query(CountryDBHelper.TABLE_COUNTRIES, allColumns,
                    null, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(CountryDBHelper.COLUMN_COUNTRY_ID));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(CountryDBHelper.COLUMN_COUNTRY_NAME));
                    @SuppressLint("Range") String continent = cursor.getString(cursor.getColumnIndex(CountryDBHelper.COLUMN_CONTINENT));

                    Country country = new Country(name, continent);
                    country.setId(id);
                    countries.add(country);
                    //Log.d(DEBUG_TAG, "Retrieved Country: " + country);
                }
            }
            if (cursor != null)
                Log.d(DEBUG_TAG, "Number of records from DB: " + cursor.getCount());
            else
                Log.d(DEBUG_TAG, "Number of records from DB: 0");
        } catch (Exception e) {
            Log.d(DEBUG_TAG, "Exception caught: " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return countries;
    }

    public void saveQuiz(int score) {
        ContentValues values = new ContentValues();
        values.put(CountryDBHelper.COLUMN_QUIZ_DATE, System.currentTimeMillis()); // Assuming you want to save the current date/time
        values.put(CountryDBHelper.COLUMN_SCORE, score);

        long newRowId = db.insert(CountryDBHelper.TABLE_QUIZZES, null, values);
        if (newRowId != -1) {
            Log.d(DEBUG_TAG, "Quiz saved successfully with ID: " + newRowId);
        } else {
            Log.d(DEBUG_TAG, "Failed to save quiz");
        }
    }

    public List<SavedQuizData> getSavedQuizzes() {
        List<SavedQuizData> savedQuizzes = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    CountryDBHelper.TABLE_QUIZZES,
                    new String[]{CountryDBHelper.COLUMN_QUIZ_ID, CountryDBHelper.COLUMN_SCORE, CountryDBHelper.COLUMN_QUIZ_DATE},
                    null, null, null, null, null
            );

            if (cursor != null && cursor.getCount() >= 0) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") long quizId = cursor.getLong(cursor.getColumnIndex(CountryDBHelper.COLUMN_QUIZ_ID));
                    @SuppressLint("Range") int score = cursor.getInt(cursor.getColumnIndex(CountryDBHelper.COLUMN_SCORE));
                    @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(CountryDBHelper.COLUMN_QUIZ_DATE));

                    // Create a new SavedQuizData object with the retrieved data
                    SavedQuizData savedQuizData = new SavedQuizData(quizId, score, parseLong(date));

                    // Add the SavedQuizData object to the list
                    savedQuizzes.add(savedQuizData);
                }
            }
            if (cursor != null) {
                Log.d(DEBUG_TAG, "Number of saved quizzes retrieved from DB: " + cursor.getCount());
            }
        } catch (Exception e) {
            Log.d(DEBUG_TAG, "Exception caught while retrieving saved quizzes: " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return savedQuizzes;
    }


}
