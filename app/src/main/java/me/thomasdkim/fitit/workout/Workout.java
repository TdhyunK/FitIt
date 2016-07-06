package me.thomasdkim.fitit.workout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import me.thomasdkim.fitit.R;

public class Workout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        String currentDate = DateFormat.getLongDateFormat(this).format(new Date());

        TextView textView = new TextView(this);
        textView.setTextSize(50);
        textView.setText(currentDate);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.workoutscreen);
        layout.addView(textView);
    }


    public ArrayList<String> DataeList(){

        Calendar begindate,today,enddate;
        today =  Calendar.getInstance();
        ArrayList<String> dateList = new ArrayList<String>();

        //Initialize begindate and enddate
        begindate = Calendar.getInstance();
        enddate = Calendar.getInstance();

        //Clear begindate and enddate
        begindate.clear();
        enddate.clear();

        //Set begindate and enddate to the current date
        begindate.set(today.YEAR, today.MONTH, today.DAY_OF_MONTH);
        enddate.set(today.YEAR, today.MONTH, today.DAY_OF_MONTH);

        //Make begindate yesterday, enddate tomorrow
        begindate.add(Calendar.DAY_OF_MONTH, -1);
        enddate.add(Calendar.DAY_OF_MONTH, 1);

        //Checks that begindate is less than or equal to tomorrow
        while(begindate.compareTo(enddate) <= 0){
            dateList.add(dateToString(begindate));
            begindate.add(Calendar.DAY_OF_MONTH, 1);
        }


        return dateList;
    }

    public String dateToString(Calendar cal){
        String dateString = new String();
        dateString = cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" +
        "/" + cal.get(Calendar.YEAR);

        return dateString;
    }




}
