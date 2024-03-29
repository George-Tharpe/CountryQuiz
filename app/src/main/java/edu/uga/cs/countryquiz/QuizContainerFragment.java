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
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

public class QuizContainerFragment extends Fragment {
    public static int swipeCount = 0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_container_quiz, container, false);

        ViewPager2 pager = view.findViewById(R.id.viewpager);
        QuizPagerAdapter adapter = new QuizPagerAdapter(getChildFragmentManager(), getLifecycle());
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setAdapter(adapter);

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Increment swipeCount on each page change
                swipeCount++;
                Log.d("Swipe Count", "Swipe count: " + swipeCount);
            }
        });

        return view;
    }

    public static int getSwipeCount() {
        return swipeCount;
    }
}