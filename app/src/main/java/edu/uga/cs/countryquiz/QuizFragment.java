package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizFragment extends Fragment {

    // Array of Android version code names
    private static final String[] androidVersions = {
            "14",
            "13",
    };

    // Array of Android version highlights/brief descriptions
    private static final String[] androidVersionsInfo = {
            "version info",
    };

    // which Android version to display in the fragment
    private int versionNum;

    public QuizFragment() {
        // Required empty public constructor
    }

    public static QuizFragment newInstance( int versionNum ) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putInt( "versionNum", versionNum );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        //public void onActivityCreated(Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        TextView questionTitle = view.findViewById( R.id.question_text_view );
        RadioButton option_1 = view.findViewById( R.id.answer_option_1 );
        RadioButton option_2 = view.findViewById( R.id.answer_option_2 );
        RadioButton option_3 = view.findViewById( R.id.answer_option_3 );

        // Instantiate the CountryData class
        CountryData countryData = new CountryData(requireContext());
        countryData.open();

        // Get a random country and its continent from the database
        List<Country> countries = countryData.retrieveAllCountries();
        Random random = new Random();
        int randomIndex = random.nextInt(countries.size());
        Country randomCountry = countries.get(randomIndex);

        String countryName = randomCountry.getName();
        String correctContinent = randomCountry.getContinent();

        // Generate incorrect continents
        List<String> allContinents = new ArrayList<>();
        allContinents.add("Asia"); // Assuming Asia is always correct for simplicity
        allContinents.add("Europe");
        allContinents.add("Africa");
        Collections.shuffle(allContinents);
        List<String> incorrectContinents = allContinents.subList(0, 2);

        // Set the question and answer choices
        questionTitle.setText("Which continent is " + countryName + " in?");
        option_1.setText(correctContinent);
        option_2.setText(incorrectContinents.get(0));
        option_3.setText(incorrectContinents.get(1));

        // Close the CountryData object
        countryData.close();
    }

    public static int getNumberOfVersions() {
        return 7;
    }
}
