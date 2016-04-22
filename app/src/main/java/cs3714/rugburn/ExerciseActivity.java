package cs3714.rugburn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import cs3714.rugburn.CustomObjects.Exercise;
import cs3714.rugburn.CustomObjects.User;
import cs3714.rugburn.CustomObjects.Workout;

/**
 * Created by Jared on 4/22/2016.
 */
public class ExerciseActivity extends AppCompatActivity implements View.OnClickListener{

    User user;
    Workout workout;
    Exercise exercise;

    Button submit, cancel;
    TextView exerciseName;
    EditText sets,reps,weight;
    ImageView picture;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getParcelable("USER");
        }
        workout = user.getCurrentWorkout();


        exercise = new Exercise("");

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        //TODO: Get user-entered values from the EditTexts and supply them to our models
        exerciseName = (TextView)findViewById(R.id.name);
        sets = (EditText)findViewById(R.id.setSets);
        reps = (EditText)findViewById(R.id.setReps);
        weight = (EditText)findViewById(R.id.setWeight);

        picture = (ImageView)findViewById(R.id.exercisePic);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == submit.getId()) {
            workout.addExercise(exercise);
            i = new Intent(this, CurrentWorkoutActivity.class);
            Bundle b = new Bundle();
            b.putParcelable("USER", user);
            i.putExtras(b);
            startActivity(i);
        } else if (v.getId() == cancel.getId()) {
            i = new Intent(this, CurrentWorkoutActivity.class);
            Bundle b = new Bundle();
            b.putParcelable("USER", user);
            i.putExtras(b);
            startActivity(i);
        }
    }
}
