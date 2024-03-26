package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database and populate countries from CSV asynchronously
        CountryDBHelper dbHelper = CountryDBHelper.getInstance(this);
        CountryData data = new CountryData(this);
        dbHelper.populateCountriesFromCSVAsync(this);

        data.open();

        // Display the SplashFragment initially
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SplashFragment())
                .commit();
    }
}
