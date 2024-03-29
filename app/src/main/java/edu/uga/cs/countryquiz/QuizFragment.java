package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizFragment extends Fragment {

    private int score = 0;
    private int totalQuestions = 6;

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

        // Get all countries from the database
        List<Country> countries = countryData.retrieveAllCountries();

        // Select a random country
        Random random = new Random();
        int randomIndex = random.nextInt(countries.size());
        Country randomCountry = countries.get(randomIndex);

        String countryName = randomCountry.getName();
        String correctContinent = randomCountry.getContinent();

        // Remove the selected country from the list to prevent duplicates
        countries.remove(randomIndex);

        // Generate incorrect continents
        List<String> allContinents = new ArrayList<>();
        for (Country country : countries) {
            String continent = country.getContinent();
            if (!allContinents.contains(continent) && !continent.equals(correctContinent)) {
                allContinents.add(continent);
            }
        }
        Collections.shuffle(allContinents);

        // Select two random continents for incorrect options
        List<String> incorrectContinents = new ArrayList<>();
        for (int i = 0; i < 2 && i < allContinents.size(); i++) {
            incorrectContinents.add(allContinents.get(i));
        }

        // Add the correct continent to the list of incorrect continents
        incorrectContinents.add(correctContinent);
        Collections.shuffle(incorrectContinents);

        // Set the question and answer choices
        questionTitle.setText("Which continent is " + countryName + " in?");
        option_1.setText("A: " + incorrectContinents.get(0));
        option_2.setText("B: " + incorrectContinents.get(1));
        option_3.setText("C: " + incorrectContinents.get(2));

        // Set click listeners for answer choices
        option_1.setOnClickListener(v -> checkAnswer(option_1.getText().toString(), correctContinent));
        option_2.setOnClickListener(v -> checkAnswer(option_2.getText().toString(), correctContinent));
        option_3.setOnClickListener(v -> checkAnswer(option_3.getText().toString(), correctContinent));

        // Close the CountryData object
        countryData.close();
    }
    private void checkAnswer(String selectedAnswer, String correctAnswer) {
        if (selectedAnswer.equals(correctAnswer)) {
            score++; // Increase score for correct answer
        }

        if (totalQuestions == QuizContainerFragment.getSwipeCount()) {
            // Show quiz result if all questions have been answered
            showQuizResult();
        }
    }

    private void showQuizResult() {
        // Show the score out of totalQuestions
        Toast.makeText(requireContext(), "Quiz result: " + score + "/" + totalQuestions, Toast.LENGTH_LONG).show();
    }

    public static int getNumberOfVersions() {
        return 6;
    }
}
