package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

public class QuizFragment extends Fragment {

    // Array of Android version code names
    private static final String[] androidVersions = {
            "14",
            "13",
    };

    // Array of Android version highlights/brief descriptions
    private static final String[] androidVersionsInfo = {
            "version info",
             };

    // which Android version to display in the fragment
    private int versionNum;

    public QuizFragment() {
        // Required empty public constructor
    }

    public static QuizFragment newInstance( int versionNum ) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putInt( "versionNum", versionNum );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if( getArguments() != null ) {
            versionNum = getArguments().getInt( "versionNum" );
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        //public void onActivityCreated(Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        TextView questionTitle = view.findViewById( R.id.question_text_view );
        RadioButton option_1 = view.findViewById( R.id.answer_option_1 );
        RadioButton option_2 = view.findViewById( R.id.answer_option_2 );
        RadioButton option_3 = view.findViewById( R.id.answer_option_3 );

        questionTitle.setText( "Which Continent is (insert country) in?" );
        option_1.setText( "(Insert Continent1)" );
        option_2.setText( "(Insert Continent2)" );
        option_3.setText( "(Insert Continent3)" );
    }

    public static int getNumberOfVersions() {
        return androidVersions.length;
    }
}
