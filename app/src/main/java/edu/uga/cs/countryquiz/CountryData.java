package edu.uga.cs.countryquiz;
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
                    Log.d(DEBUG_TAG, "Retrieved Country: " + country);
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

    public long storeCountry(Country country) {
        ContentValues values = new ContentValues();
        values.put(CountryDBHelper.COLUMN_COUNTRY_NAME, country.getName());
        values.put(CountryDBHelper.COLUMN_CONTINENT, country.getContinent());

        long id = db.insert(CountryDBHelper.TABLE_COUNTRIES, null, values);
        country.setId(id);
        Log.d(DEBUG_TAG, "Stored new country with id: " + country.getId());

        return id;
    }
}
