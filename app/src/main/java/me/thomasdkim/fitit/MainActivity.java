package me.thomasdkim.fitit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import me.thomasdkim.fitit.workout.Workout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void letsWorkout(View view){
        Intent intent = new Intent(this, Workout.class);
        Button workout = (Button) findViewById(R.id.letsworkout);
        workout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent workoutIntent = new Intent(MainActivity.this, Workout.class);
                startActivity(workoutIntent);
            }
        });
    }
}
