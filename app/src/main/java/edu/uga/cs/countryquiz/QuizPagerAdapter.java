package edu.uga.cs.countryquiz;


import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QuizPagerAdapter extends FragmentStateAdapter {
    public Quiz quiz;

    public QuizPagerAdapter(Quiz quiz, FragmentManager fragmentManager, Lifecycle lifecycle, Context context) {
        super( fragmentManager, lifecycle );
        this.quiz = quiz;
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