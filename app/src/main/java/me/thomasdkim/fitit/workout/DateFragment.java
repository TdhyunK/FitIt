package me.thomasdkim.fitit.workout;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

import me.thomasdkim.fitit.R;

/**
 * Created by tdhyunk on 8/21/16.
 */
public class DateFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.slide_date_fragment, container, false);
        View newText = (TextView) rootView.findViewById(R.id.datefrag);
        FloatingActionButton newExerciseBttn = (FloatingActionButton) rootView.findViewById(R.id.addExercise);

        //Adds date to the card
        if (getArguments().getString("date") != null) {
            ((TextView) newText).setText(getArguments().getString("date")); //Set the date into the card
        }

        ExerciseCardFrag newExercise = ExerciseCardFrag.newInstance();
        final FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(R.id.exerciseCardList, newExercise).commit();

        newExerciseBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction newFt = getChildFragmentManager().beginTransaction();
                ExerciseCardFrag newExerciseCard = ExerciseCardFrag.newInstance();
                newFt.add(R.id.exerciseCardList, newExerciseCard).commit();

            }
        });

        return rootView;
    }

    public static DateFragment newInstance(Calendar cal) {


        //Create new date fragment object
        DateFragment newFrag = new DateFragment();

        //Create new Bundle to store aarguments
        Bundle args = new Bundle();

        //Set a "date" paramater the the object
        args.putString("date", Workout.dateToString(cal));
//            args.putInt("viewPlace", 2);
        newFrag.setArguments(args); //Set the argument to the newly created date fragment
        return newFrag;
    }

}

