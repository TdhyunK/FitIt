package me.thomasdkim.fitit.workout;

/**
 * Created by tdhyunk on 8/23/16.
 */
public class Exercise {
    private String exercise;
    private String date;
    private int reps;
    private int sets;

    public String getExercise(){
        return this.exercise;
    }

    public void setExercise(String exercise){
        this.exercise = exercise;
    }

    public String getDate(){
        return this.date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public int getReps(){
        return this.reps;
    }

    public void setReps(int reps){
        this.reps = reps;
    }

    public int getSets(){
        return this.sets;
    }

    public void setSets(int sets){
        this.sets = sets;
    }

}
