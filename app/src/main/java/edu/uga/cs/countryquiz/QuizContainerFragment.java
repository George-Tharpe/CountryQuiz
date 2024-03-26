package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

public class QuizContainerFragment extends Fragment {

    @Override
    protected void onCreateView( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        ViewPager2 pager = findViewById( R.id.viewpager );
        QuizPagerAdapter avpAdapter = new
                QuizPagerAdapter(
                getSupportFragmentManager(), getLifecycle());
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL );
        pager.setAdapter( avpAdapter );

    }

    private FragmentManager getSupportFragmentManager() {
    }
}
