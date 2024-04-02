package edu.uga.cs.countryquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

public class QuizResultsFragment extends Fragment {

    public List<SavedQuizData> savedQuizzes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz_results, container, false);

        // Display the quiz score
        TextView scoreTextView = view.findViewById(R.id.score_text_view);
        int score = QuizContainerFragment.getScore(); // Get the score from QuizContainerFragment
        scoreTextView.setText("Quiz Score: " + score + "/6");

        //Show past Quizzes
        savedQuizzes = QuizContainerFragment.getSavedQuizData();

        // Display the past quiz data and scores
        TextView quizDataTextView = view.findViewById(R.id.quiz_data_text_view);
        StringBuilder stringBuilder = new StringBuilder();
        for (SavedQuizData quizData : savedQuizzes) {
            stringBuilder.append("Quiz Date: ").append(quizData.getDate()).append("\n");
            stringBuilder.append("Score: ").append(quizData.getScore()).append("/6\n\n");
        }
        quizDataTextView.setText(stringBuilder.toString());

        return view;

    }
}
