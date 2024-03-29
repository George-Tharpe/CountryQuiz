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


public class QuizFragment extends Fragment {
    private Quiz quiz;
    private int questionIndex;

    public QuizFragment() {
        // Required empty public constructor
    }

    public static QuizFragment newInstance(int position) {
        return new QuizFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the quiz object with a new Quiz instance
        CountryData countryData = new CountryData(requireContext());
        countryData.open();
        quiz = new Quiz(countryData);
        quiz.generateQuestions();
        questionIndex = 0;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get references to UI elements
        TextView questionTitle = view.findViewById(R.id.question_text_view);
        RadioButton option_1 = view.findViewById(R.id.answer_option_1);
        RadioButton option_2 = view.findViewById(R.id.answer_option_2);
        RadioButton option_3 = view.findViewById(R.id.answer_option_3);


        // Check if questions are available
        if (quiz.getQuestions().isEmpty()) {
            // Handle the scenario where there are no questions available
            questionTitle.setText("No questions available");
            option_1.setText("");
            option_2.setText("");
            option_3.setText("");
        } else {
            // Get the current question from the quiz object
            Question currentQuestion = quiz.getQuestions().get(questionIndex);

            // Set the question text
            questionTitle.setText("Which continent is " + currentQuestion.getCountryName() + " in?");

            // Create a list of answer options
            List<String> answerOptions = new ArrayList<>();
            answerOptions.add(currentQuestion.getCorrectContinent());

            // Generate two unique incorrect continents
            List<String> allContinents = quiz.getAllContinents();
            Collections.shuffle(allContinents);
            for (String continent : allContinents) {
                if (!continent.equals(currentQuestion.getCorrectContinent()) && answerOptions.size() < 3) {
                    answerOptions.add(continent);
                }
                if (answerOptions.size() >= 3) {
                    break;
                }
            }

            // Shuffle the answer options
            Collections.shuffle(answerOptions);

            // Set the answer options to the radio buttons
            option_1.setText(answerOptions.get(0));
            option_2.setText(answerOptions.get(1));
            option_3.setText(answerOptions.get(2));
        }
    }
    public static int getNumberOfVersions() {
        return 7;
    }
}
