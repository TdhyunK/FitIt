package me.thomasdkim.fitit.workout;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.support.v4.view.ViewPager.OnPageChangeListener;

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
                if (state == ViewPager.SCROLL_STATE_IDLE) {

                    //Handles if the current page is tomorrow
                    if (myView.getCurrentItem() == 2) {
                        yesterday.add(Calendar.DAY_OF_MONTH, 1);
                        today.add(Calendar.DAY_OF_MONTH, 1);
                        tomorrow.add(Calendar.DAY_OF_MONTH, 1);

                        dateFragmentArray[0] = DateFragment.newInstance(yesterday);
                        dateFragmentArray[1] = DateFragment.newInstance(today);
                        dateFragmentArray[2] = DateFragment.newInstance(tomorrow);


                    }

                    //Handles if the current pagae is yesterday
                    else if (myView.getCurrentItem() == 0) {
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



