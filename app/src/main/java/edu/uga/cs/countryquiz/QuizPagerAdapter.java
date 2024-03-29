package edu.uga.cs.countryquiz;


import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QuizPagerAdapter extends FragmentStateAdapter {
    public Quiz quiz;
    public CountryData countryData;

    public QuizPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, Context context) {
        super( fragmentManager, lifecycle );
        CountryData countryData = new CountryData(context);
        countryData.open();
        quiz = new Quiz(countryData);
        quiz.generateQuestions();
    }

    @Override
    public Fragment createFragment(int position){
        return QuizFragment.newInstance( position , quiz);
    }

    @Override
    public int getItemCount() {
        return QuizFragment.getNumberOfVersions();
    }
}
