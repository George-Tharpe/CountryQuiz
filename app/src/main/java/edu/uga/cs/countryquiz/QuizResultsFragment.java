package edu.uga.cs.countryquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class QuizResultsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz_results, container, false);

        // Display the quiz score
        TextView scoreTextView = view.findViewById(R.id.score_text_view);
        int score = QuizContainerFragment.getSwipeCount(); // Get the score from QuizContainerFragment
        scoreTextView.setText("Quiz Score: " + score + "/6");

        return view;
    }
}

