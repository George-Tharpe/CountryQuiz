package edu.uga.cs.countryquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import java.util.List;

public class PastQuizFragment extends Fragment {

    public List<SavedQuizData> savedQuizzes;

    public CountryData countryData;
    public PastQuizFragment(CountryData countryData) {
        // Required empty public constructor
        this.countryData = countryData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz_past, container, false);

        Button returnHome = view.findViewById(R.id.homeButton);

        if(!(countryData.isDBOpen())){
            countryData.open();
        }

        //Show past Quizzes
        savedQuizzes = countryData.getSavedQuizzes();

        // Display the past quiz data and scores in reverse order
        TextView quizDataTextView = view.findViewById(R.id.quiz_data_text_view);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = savedQuizzes.size() - 1; i >= 0; i--) {
            SavedQuizData quizData = savedQuizzes.get(i);
            stringBuilder.append("Quiz Date: ").append(quizData.getDate()).append("\n");
            stringBuilder.append("Score: ").append(quizData.getScore()).append("/6\n\n");
        }
        quizDataTextView.setText(stringBuilder.toString());

        returnHome.setOnClickListener(v -> {
            // Replace the SplashFragment with the QuizFragment
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SplashFragment())
                    .commit();
        });

        return view;
    }


}
