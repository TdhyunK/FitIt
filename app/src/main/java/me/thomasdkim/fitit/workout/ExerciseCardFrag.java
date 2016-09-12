package me.thomasdkim.fitit.workout;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import me.thomasdkim.fitit.R;

/**
 * Created by tdhyunk on 8/21/16.
 */
public class ExerciseCardFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.card_fragment, container, false);

        Button setButton = (Button) rootView.findViewById(R.id.addSet);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newSet(rootView);
            }
        });


        return rootView;
    }

    public static ExerciseCardFrag newInstance() {

        Bundle args = new Bundle();

        ExerciseCardFrag fragment = new ExerciseCardFrag();
        args.putInt("ViewPlace", 2);
        fragment.setArguments(args);
        return fragment;
    }

    public View newSet(View rootView) {

            /*Declare and initialize variables*/
        LinearLayout currView = (LinearLayout) rootView.findViewById(R.id.newSetLayout);
        LinearLayout newSet = new LinearLayout(getActivity());
        TextView setOne = new TextView(getActivity());
        EditText reps = new EditText(getActivity());
        EditText weight = new EditText(getActivity());

            /*Set the parameters for "Set 1" */
        LinearLayout.LayoutParams setOneLayoutParams = new LinearLayout.LayoutParams(Workout.dpToPx(193, getActivity().getApplicationContext()), ViewGroup.LayoutParams.WRAP_CONTENT);
        setOneLayoutParams.setMargins(Workout.dpToPx(10, getActivity().getApplicationContext()), 0, 0, 0);
        setOne.setLayoutParams(setOneLayoutParams);
        setOne.setTextSize(20);
        setOne.setText("Set " + getArguments().getInt("ViewPlace"));

            /*Set the parameters for Reps*/
        ViewGroup.LayoutParams repLayoutParams = new ViewGroup.LayoutParams(Workout.dpToPx(90, getActivity().getApplicationContext()), ViewGroup.LayoutParams.WRAP_CONTENT);
        reps.setLayoutParams(repLayoutParams);
        reps.setTextSize(20);
        reps.setBackgroundColor(Color.TRANSPARENT);
        reps.setText("0");

            /*Set the parameters for Weight*/
        ViewGroup.LayoutParams weightLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        weight.setLayoutParams(weightLayoutParams);
        weight.setTextSize(20);
        weight.setBackgroundColor(Color.TRANSPARENT);
        weight.setText("0");

        newSet.addView(setOne);
        newSet.addView(reps);
        newSet.addView(weight);

        currView.addView(newSet, getArguments().getInt("ViewPlace"));
        getArguments().putInt("ViewPlace", getArguments().getInt("ViewPlace") + 1);
        return currView;

    }


}


