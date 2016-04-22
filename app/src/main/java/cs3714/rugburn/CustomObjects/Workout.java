package cs3714.rugburn.CustomObjects;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jared on 4/22/2016.
 */
public class Workout {

    Date date;
    ArrayList<Exercise> exercises;
    Boolean finished;

    public Workout(Date d) {
        date = d;
        exercises = new ArrayList<Exercise>();
        finished = false;
    }

    public void addExercise(Exercise e) {
        exercises.add(e);
    }

    public ArrayList<Exercise> returnExercises() {
        return exercises;
    }

    public boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean b) {
        finished = b;
    }

}
