package edu.uga.cs.countryquiz;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

public class SplashFragment extends Fragment {
    public static CountryData countryData;
    public SplashFragment() {
        // Required empty public constructor
    }

    public static List<SavedQuizData> getSavedQuizData() {
        return countryData.getSavedQuizzes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        // Find the start button
        Button startButton = view.findViewById(R.id.start_button);

        countryData = new CountryData(getContext());
        countryData.open();

        // Set OnClickListener to start the quiz when the button is clicked
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the SplashFragment with the QuizFragment
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new QuizContainerFragment(countryData))
                        .commit();
            }
        });

        Button pastButton = view.findViewById(R.id.past_quizzes_button);

        // Set OnClickListener to start the quiz when the button is clicked
        pastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the SplashFragment with the QuizFragment
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new PastQuizFragment(countryData))
                        .commit();
            }
        });

        return view;
    }

}

