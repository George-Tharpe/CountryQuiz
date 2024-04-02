package edu.uga.cs.countryquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

public class QuizContainerFragment extends Fragment {

    public static Quiz quiz;
    public CountryData countryData;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_container_quiz, container, false);
        ViewPager2 pager = view.findViewById(R.id.viewpager);

        CountryData countryData = new CountryData(getContext());
        countryData.open();
        quiz = new Quiz(countryData);
        quiz.generateQuestions();

        QuizPagerAdapter adapter = new QuizPagerAdapter(quiz, getChildFragmentManager(), getLifecycle(), getContext());
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setAdapter(adapter);




        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d("answered so far: ", String.valueOf(quiz.getQuestionsAnsweredSoFar()));
                if (quiz.getQuestionsAnsweredSoFar() >= 6) {
                    // Create a new fragment to display quiz results and score
                    QuizResultsFragment quizResultsFragment = new QuizResultsFragment();
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, quizResultsFragment);
                    transaction.addToBackStack(null); // Add the transaction to the back stack
                    transaction.commit();
                }
            }
        });

        return view;
    }

    public static int getScore() {
        return quiz.getCurrentScore();
    }
}