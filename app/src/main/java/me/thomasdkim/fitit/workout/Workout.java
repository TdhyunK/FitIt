package me.thomasdkim.fitit.workout;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.Calendar;

import me.thomasdkim.fitit.R;

public class Workout extends AppCompatActivity {

    ViewPager myView; //ViewPager that holds the sliding date fragments.
    DateFragmentAdapter mDateFragmentAdapter; //DateFragmentAdapter that provides new date fragments to the sliding viewPagers.
    DateFragment[] dateFragmentArray; //Array that holds all of the date fragments.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("The app is being created.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        /* Declare variablees  */
        final Calendar yesterday, today, tomorrow;
        yesterday = Calendar.getInstance();
        today = Calendar.getInstance();
        tomorrow = Calendar.getInstance();
        dateFragmentArray = new DateFragment[3];

        /* Add 1 to the dates since Calendar starts at 0 */
        yesterday.add(Calendar.MONTH, 1);
        today.add(Calendar.MONTH, 1);
        tomorrow.add(Calendar.MONTH, 1);

        yesterday.add(Calendar.DAY_OF_MONTH, -1); //Set the date of yesterday to the day before
        tomorrow.add(Calendar.DAY_OF_MONTH, 1); //Set the date of tomorrow to the day after


        dateFragmentArray[0] = DateFragment.newInstance(yesterday);
        dateFragmentArray[1] = DateFragment.newInstance(today);
        dateFragmentArray[2] = DateFragment.newInstance(tomorrow);


        System.out.println("The date is: " + dateToString(today));

        //Set up the adapter for the viewPager
        myView = (ViewPager) findViewById(R.id.pager);
        mDateFragmentAdapter = new DateFragmentAdapter(getSupportFragmentManager(), dateFragmentArray);
        myView.setAdapter(mDateFragmentAdapter);
        myView.setCurrentItem(1);


        myView.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager.SCROLL_STATE_IDLE){

                    //Handles if the current page is tomorrow
                    if(myView.getCurrentItem() == 2){
                        yesterday.add(Calendar.DAY_OF_MONTH, 1);
                        today.add(Calendar.DAY_OF_MONTH, 1);
                        tomorrow.add(Calendar.DAY_OF_MONTH, 1);

                        dateFragmentArray[0] = DateFragment.newInstance(yesterday);
                        dateFragmentArray[1] = DateFragment.newInstance(today);
                        dateFragmentArray[2] = DateFragment.newInstance(tomorrow);


                    }

                    //Handles if the current pagae is yesterday
                    else if(myView.getCurrentItem() == 0){
                        yesterday.add(Calendar.DAY_OF_MONTH, -1);
                        today.add(Calendar.DAY_OF_MONTH, -1);
                        tomorrow.add(Calendar.DAY_OF_MONTH, -1);

                        dateFragmentArray[0] = DateFragment.newInstance(yesterday);
                        dateFragmentArray[1] = DateFragment.newInstance(today);
                        dateFragmentArray[2] = DateFragment.newInstance(tomorrow);

                    }

                    mDateFragmentAdapter = new DateFragmentAdapter(getSupportFragmentManager(), dateFragmentArray);
                    myView.setAdapter(mDateFragmentAdapter);
                    myView.setCurrentItem(1, false);

                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        if (myView.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            myView.setCurrentItem(myView.getCurrentItem() - 1);
        }
    }

    public static class DateFragment extends Fragment {

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
                    ExerciseCardFrag newExerciseCarad = ExerciseCardFrag.newInstance();
                    ft.add(R.id.exerciseCardList, newExerciseCarad);
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
            args.putString("date", dateToString(cal));
//            args.putInt("viewPlace", 2);
            newFrag.setArguments(args); //Set the argument to the newly created date fragment
            return newFrag;
        }

//        public View startExercise(final View rootView) {
//
//            /* Declare necessary variables -- CardView, LinearLayout, etc. */
//            final RelativeLayout currView = (RelativeLayout) rootView;
////            CardView newCard = new CardView(getActivity());
//            CardView newCard = (CardView) rootView.findViewById(R.id.workout_card);
////            LinearLayout linearLayout1 = new LinearLayout(getActivity());
//            final LinearLayout linearLayout1 = new LinearLayout(getActivity()); //Set layout
//            LinearLayout linearLayout2 = new LinearLayout(getActivity()); //Layout for set, reps, weight
//            EditText exerciseEditText = new EditText(getActivity()); //Exercise name
//            TextView setOne = new TextView(getActivity()); //Set
//            EditText reps = new EditText(getActivity()); //Reps
//            EditText weight = new EditText(getActivity()); //Weight
//            Button addSet = new Button(getActivity(), null, android.R.attr.borderlessButtonStyle); //Add Set button
//            FloatingActionButton addExerciseBtn = (FloatingActionButton) currView.findViewById(R.id.addExercise);
//
//
//
//            /* Set parameters for the CardView */
//            //Set the width and height to of the CardView
//            CardView.LayoutParams cardLayoutParams = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
//
//            //Set the margins for the CardView
//            cardLayoutParams.setMargins(dpToPx(10, getActivity().getApplicationContext()), dpToPx(10, getActivity().getApplicationContext()),
//                    dpToPx(10, getActivity().getApplicationContext()), dpToPx(10, getActivity().getApplicationContext()));
////            newCard.setId(2);
////            newCard.setLayoutParams(cardLayoutParams);
//
//            /* Set the parameters for linearLayout1 */
//            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            linearLayout1.setOrientation(LinearLayout.VERTICAL);
//            linearLayout1.setLayoutParams(linearLayoutParams);
//
//            /* Set the parameters for linearLayout2 */
//            LinearLayout.LayoutParams linearLayoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
//            linearLayout2.setLayoutParams(linearLayoutParams2);
//
//
//            /*Set the parameters for the exerciseEditText*/
//            LinearLayout.LayoutParams exerciseLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            exerciseLayoutParams.setMargins(dpToPx(10, getActivity().getApplicationContext()), 0, 0, 0);
//
//            exerciseEditText.setLayoutParams(exerciseLayoutParams);
//            exerciseEditText.setTextSize(25);
//            exerciseEditText.setBackgroundColor(Color.TRANSPARENT);
//            exerciseEditText.setText("Exercise");
//
//            /*Set the parameters for "Set 1" */
//            LinearLayout.LayoutParams setOneLayoutParams = new LinearLayout.LayoutParams(dpToPx(193, getActivity().getApplicationContext()), ViewGroup.LayoutParams.WRAP_CONTENT);
//            setOneLayoutParams.setMargins(dpToPx(10, getActivity().getApplicationContext()), 0, 0, 0);
//            setOne.setLayoutParams(setOneLayoutParams);
//            setOne.setTextSize(20);
//            setOne.setText("Set 1");
//
//            /*Set the parameters for Reps*/
//            ViewGroup.LayoutParams repLayoutParams = new ViewGroup.LayoutParams(dpToPx(90, getActivity().getApplicationContext()), ViewGroup.LayoutParams.WRAP_CONTENT);
//            reps.setLayoutParams(repLayoutParams);
//            reps.setTextSize(20);
//            reps.setBackgroundColor(Color.TRANSPARENT);
//            reps.setText("0");
//
//            /*Set the parameters for Weight*/
//            ViewGroup.LayoutParams weightLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            weight.setLayoutParams(weightLayoutParams);
//            weight.setTextSize(20);
//            weight.setBackgroundColor(Color.TRANSPARENT);
//            weight.setText("0");
//
//            /*Set the paraemeters for the "add set" button*/
//            LinearLayout.LayoutParams setButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            setButtonParams.setMargins(0, dpToPx(10, getActivity().getApplicationContext()), 0, 0);
//            addSet.setText("Add a set");
//            addSet.setLayoutParams(setButtonParams);
//            addSet.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    newSet(linearLayout1, (getArguments().getInt("viewPlace")));
//                }
//            });
//
//            /*Set the parameters for the "add exercise" button */
//            addExerciseBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    newExercise(currView);
//                }
//            });
//
//            linearLayout2.addView(setOne);
//            linearLayout2.addView(reps);
//            linearLayout2.addView(weight);
//            linearLayout1.addView(exerciseEditText);
//            linearLayout1.addView(linearLayout2);
//            linearLayout1.addView(addSet);
//            newCard.addView(linearLayout1);
////            ((CardView) newCard).addView(linearLayout1);
//
////            ((LinearLayout) currView).addView(newCard);
//
//            return currView;
//
//        }
//
//        //Converst dp to Pixels
//
//
////        public View newSet(View rootView, int viewPlace) {
////
////            /*Declare and initialize variables*/
////            final LinearLayout currView = (LinearLayout) rootView;
////            LinearLayout newSet = new LinearLayout(getActivity());
////            TextView setOne = new TextView(getActivity());
////            EditText reps = new EditText(getActivity());
////            EditText weight = new EditText(getActivity());
////
////            /*Set the parameters for "Set 1" */
////            LinearLayout.LayoutParams setOneLayoutParams = new LinearLayout.LayoutParams(dpToPx(193, getActivity().getApplicationContext()), ViewGroup.LayoutParams.WRAP_CONTENT);
////            setOneLayoutParams.setMargins(dpToPx(10, getActivity().getApplicationContext()), 0, 0, 0);
////            setOne.setLayoutParams(setOneLayoutParams);
////            setOne.setTextSize(20);
////            setOne.setText("Set " + viewPlace);
////
////            /*Set the parameters for Reps*/
////            ViewGroup.LayoutParams repLayoutParams = new ViewGroup.LayoutParams(dpToPx(90, getActivity().getApplicationContext()), ViewGroup.LayoutParams.WRAP_CONTENT);
////            reps.setLayoutParams(repLayoutParams);
////            reps.setTextSize(20);
////            reps.setBackgroundColor(Color.TRANSPARENT);
////            reps.setText("0");
////
////            /*Set the parameters for Weight*/
////            ViewGroup.LayoutParams weightLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
////            weight.setLayoutParams(weightLayoutParams);
////            weight.setTextSize(20);
////            weight.setBackgroundColor(Color.TRANSPARENT);
////            weight.setText("0");
////
////            newSet.addView(setOne);
////            newSet.addView(reps);
////            newSet.addView(weight);
////            getArguments().putInt("viewPlace", viewPlace + 1);
////            currView.addView(newSet, viewPlace);
////
////
////            return currView;
////
////        }
//
//        public View newExercise(View rootView) {
//            final LinearLayout currView = (LinearLayout) rootView.findViewById(R.id.exerciseCardList);
//            final CardView newCard = new CardView(getActivity());
////            CardView newCard = (CardView) rootView.findViewById(R.id.workout_card);
//            final LinearLayout linearLayout1 = new LinearLayout(getActivity());
////            LinearLayout linearLayout1 = (LinearLayout) rootView.findViewById(R.id.newSetLayout);
//            final LinearLayout linearLayout2 = new LinearLayout(getActivity());
//            EditText exerciseEditText = new EditText(getActivity());
//            TextView setOne = new TextView(getActivity());
//            EditText reps = new EditText(getActivity());
//            EditText weight = new EditText(getActivity());
//            Button addSet = new Button(getActivity(), null, android.R.attr.borderlessButtonStyle);
//
//
//
//            /* Set parameters for the CardView */
//            //Set the width and height to of the CardView
//            CardView.LayoutParams cardLayoutParams = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
//
//            //Set the margins for the CardView
//            cardLayoutParams.setMargins(dpToPx(10, getActivity().getApplicationContext()), dpToPx(10, getActivity().getApplicationContext()),
//                    dpToPx(10, getActivity().getApplicationContext()), dpToPx(10, getActivity().getApplicationContext()));
////            newCard.setId(2);
//            newCard.setLayoutParams(cardLayoutParams);
//
//            /* Set the parameters for linaerLayout1 */
//            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            linearLayout1.setOrientation(LinearLayout.VERTICAL);
//            linearLayout1.setLayoutParams(linearLayoutParams);
//
//            /* Set the parameters for linearLayout2 */
//            LinearLayout.LayoutParams linearLayoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
//            linearLayout2.setLayoutParams(linearLayoutParams2);
//
//
//            /*Set the parameters for the exerciseEditText*/
//            LinearLayout.LayoutParams exerciseLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            exerciseLayoutParams.setMargins(dpToPx(10, getActivity().getApplicationContext()), 0, 0, 0);
//
//            exerciseEditText.setLayoutParams(exerciseLayoutParams);
//            exerciseEditText.setTextSize(25);
//            exerciseEditText.setBackgroundColor(Color.TRANSPARENT);
//            exerciseEditText.setText("Exercise");
//
//            /*Set the parameters for "Set 1" */
//            LinearLayout.LayoutParams setOneLayoutParams = new LinearLayout.LayoutParams(dpToPx(220, getActivity().getApplicationContext()), ViewGroup.LayoutParams.WRAP_CONTENT);
//            setOneLayoutParams.setMargins(dpToPx(10, getActivity().getApplicationContext()), 0, 0, 0);
//            setOne.setLayoutParams(setOneLayoutParams);
//            setOne.setTextSize(20);
//            setOne.setText("Set 1");
//
//            /*Set the parameters for Reps*/
//            ViewGroup.LayoutParams repLayoutParams = new ViewGroup.LayoutParams(dpToPx(98, getActivity().getApplicationContext()), ViewGroup.LayoutParams.WRAP_CONTENT);
//            reps.setLayoutParams(repLayoutParams);
//            reps.setTextSize(20);
//            reps.setBackgroundColor(Color.TRANSPARENT);
//            reps.setText("Reps");
//
//            /*Set the parameters for Weight*/
//            ViewGroup.LayoutParams weightLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            weight.setLayoutParams(weightLayoutParams);
//            weight.setTextSize(20);
//            weight.setBackgroundColor(Color.TRANSPARENT);
//            weight.setText("Weight");
//
//            /*Set the paraemeters for the "add set" button*/
//            LinearLayout.LayoutParams setButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            setButtonParams.setMargins(0, dpToPx(10, getActivity().getApplicationContext()), 0, 0);
//            addSet.setText("Add a set");
//            addSet.setLayoutParams(setButtonParams);
//            addSet.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    newSet(linearLayout1, getArguments().getInt("viewPlace"));
//                }
//            });
//
//            linearLayout2.addView(setOne);
//            linearLayout2.addView(reps);
//            linearLayout2.addView(weight);
//            linearLayout1.addView(exerciseEditText);
//            linearLayout1.addView(linearLayout2);
//            newCard.addView(linearLayout1);
//            linearLayout1.addView(addSet);
////            ((CardView) newCard).addView(linearLayout1);
//
//            currView.addView(newCard);
//
//            return currView;
//
//
//        }
    }

    public static class DateFragmentAdapter extends FragmentStatePagerAdapter{

        DateFragment[] fragList;
        int fragNum;

        public DateFragmentAdapter(FragmentManager fm, DateFragment[] fragList) {
            super(fm);
            this.fragNum = 3;
            this.fragList = fragList;
        }

        @Override
        public DateFragment getItem(int position) {

            return this.fragList[position];
        }

        @Override
        public int getCount() {

            return this.fragNum;
        }
    }


    public static class ExerciseCardFrag extends Fragment{

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
            LinearLayout.LayoutParams setOneLayoutParams = new LinearLayout.LayoutParams(dpToPx(193, getActivity().getApplicationContext()), ViewGroup.LayoutParams.WRAP_CONTENT);
            setOneLayoutParams.setMargins(dpToPx(10, getActivity().getApplicationContext()), 0, 0, 0);
            setOne.setLayoutParams(setOneLayoutParams);
            setOne.setTextSize(20);
            setOne.setText("Set " + getArguments().getInt("ViewPlace"));

            /*Set the parameters for Reps*/
            ViewGroup.LayoutParams repLayoutParams = new ViewGroup.LayoutParams(dpToPx(90, getActivity().getApplicationContext()), ViewGroup.LayoutParams.WRAP_CONTENT);
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


    //Create a date string from a calendar object
    public static String dateToString(Calendar cal){
        String dateString;
        dateString = cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" +
        + cal.get(Calendar.YEAR);

        return dateString;
    }

    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;

    }



}
