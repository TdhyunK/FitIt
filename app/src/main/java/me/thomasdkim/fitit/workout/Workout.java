package me.thomasdkim.fitit.workout;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.thomasdkim.fitit.R;

public class Workout extends AppCompatActivity {

    ViewPager myView;
    DateFragmentAdapter mDateFragmentAdapter;
    DateFragment[] dateFragmentArray;


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

        yesterday.add(Calendar.MONTH, 1);
        today.add(Calendar.MONTH, 1);
        tomorrow.add(Calendar.MONTH, 1);

        yesterday.add(Calendar.DAY_OF_MONTH, -1); //Set the date of yesterday to the day before
        tomorrow.add(Calendar.DAY_OF_MONTH, 1); //Set the date of tomorrow to the day after


        dateFragmentArray[0] = DateFragment.newInstance(yesterday);
        dateFragmentArray[1] = DateFragment.newInstance(today);
        dateFragmentArray[2] = DateFragment.newInstance(tomorrow);


        System.out.println("The date is: " + dateToString(today));


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


            View rootView = inflater.inflate(R.layout.slide_date_fragment,container, false);
            View newText = (TextView) rootView.findViewById(R.id.datefrag);

            if(getArguments().getString("date") != null) {
                ((TextView)newText).setText(getArguments().getString("date"));
            }

            return rootView;
        }

        public static DateFragment newInstance(Calendar cal) {


            //Create new date fragment object
            DateFragment newFrag = new DateFragment();

            //Create new Bundle to store aarguments
            Bundle args = new Bundle();

            //Set a "date" paramater the the object
            args.putString("date", dateToString(cal));
            newFrag.setArguments(args); //Set the argument to the newly created date fragment
            return newFrag;
        }


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


    //Create a date string from a calendar object
    public static String dateToString(Calendar cal){
        String dateString;
        dateString = cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" +
        + cal.get(Calendar.YEAR);

        return dateString;
    }

    public static void updateDateList(DateFragment[] dateList){

    }

}
