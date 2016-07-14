package me.thomasdkim.fitit.workout;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        /* Declare variablees  */
        DateFragment[] dateFragList = new DateFragment[3];
        Calendar yesterday, today, tomorrow;
        yesterday = Calendar.getInstance();
        today = Calendar.getInstance();
        tomorrow = Calendar.getInstance();

        yesterday.add(Calendar.DAY_OF_MONTH, -1); //Set the date of yesterday to the day before
        tomorrow.add(Calendar.DAY_OF_MONTH, 1); //Set the date of tomorrow to the day after


        dateFragList[0] = DateFragment.newInstance(yesterday);
        dateFragList[1] = DateFragment.newInstance(today);
        dateFragList[2] = DateFragment.newInstance(tomorrow);




        myView = (ViewPager) findViewById(R.id.pager);
        mDateFragmentAdapter = new DateFragmentAdapter(getSupportFragmentManager(), dateFragList);
        myView.setAdapter(mDateFragmentAdapter);



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

        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.slide_date_fragment, container, true);
            View newText = rootView.findViewById(R.id.datefrag);

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
        int fragNum = 3;

        public DateFragmentAdapter(FragmentManager fm, DateFragment[] fragList) {
            super(fm);
            this.fragList = fragList;
        }

        @Override
        public Fragment getItem(int position) {

            return fragList[position];
        }

        @Override
        public int getCount() {

            return fragNum;
        }
    }



    public static ArrayList<String> DateList(){

        Calendar begindate,today,enddate;
        today =  Calendar.getInstance();
        ArrayList<String> dateList = new ArrayList<String>();

        //Initialize begindate and enddate
        begindate = Calendar.getInstance();
        enddate = Calendar.getInstance();

        //Clear begindate and enddate
//        begindate.clear();
//        enddate.clear();

        //Set begindate and enddate to the current date
//        begindate.set(today.YEAR, today.MONTH, today.DAY_OF_MONTH);
//        enddate.set(today.YEAR, today.MONTH, today.DAY_OF_MONTH);

        //Make begindate yesterday, enddate tomorrow
        begindate.add(Calendar.DAY_OF_MONTH, -1);
        enddate.add(Calendar.DAY_OF_MONTH, 1);

        //Checks that begindate is less than or e   qual to tomorrow
        while(begindate.compareTo(enddate) <= 0){
            dateList.add(dateToString(begindate));
            begindate.add(Calendar.DAY_OF_MONTH, 1);
        }

        dateList.add(dateToString(today));

        return dateList;
    }

    public static String dateToString(Calendar cal){
        String dateString;
        dateString = cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" +
        + cal.get(Calendar.YEAR);

        return dateString;
    }




}
