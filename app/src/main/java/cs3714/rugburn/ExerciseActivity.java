package cs3714.rugburn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    String name;
    EditText sets,reps,weight;
    ImageView picture;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getParcelable("USER");
            name = bundle.getString("WORKOUT");
        }
        workout = user.getCurrentWorkout();
        picture = (ImageView)findViewById(R.id.exercisePic);

        //TODO: need to get name of exercise from qr scanner or user manual entry
        if (name.equals("Squats")) {
            exercise = new Exercise("Squats");
            picture.setImageResource(R.drawable.squat);
        } else  if (name.equals("Bench Press")) {
            exercise = new Exercise("Bench Press");
            picture.setImageResource(R.drawable.bench);
        } else if (name.equals("Bicep Curls")) {
            exercise = new Exercise("Bicep Curls");
            picture.setImageResource(R.drawable.bicep);
        }

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        exerciseName = (TextView)findViewById(R.id.name);
        exerciseName.setText(exercise.getName());

        sets = (EditText)findViewById(R.id.setSets);
        reps = (EditText)findViewById(R.id.setReps);
        weight = (EditText)findViewById(R.id.setWeight);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == submit.getId()) {
            if (sets.getText().equals("") || sets.getText().length() == 0) {
                Toast.makeText(this, "Please enter the number of sets",
                        Toast.LENGTH_LONG).show();
                return;
            } else {
                exercise.setSets(Integer.parseInt(sets.getText().toString()));
            }
            if (reps.getText().equals("") || reps.getText().length() == 0) {
                Toast.makeText(this, "Please enter the number of reps",
                        Toast.LENGTH_LONG).show();
                return;
            } else {
                exercise.setReps(Integer.parseInt(reps.getText().toString()));
            }
            if (weight.getText().equals("") || weight.getText().length() == 0) {
                Toast.makeText(this, "Please enter the weight",
                        Toast.LENGTH_LONG).show();
                return;
            } else {
                exercise.setWeight(Integer.parseInt(weight.getText().toString()));
            }
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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(this, CurrentWorkoutActivity.class);
                Bundle bundleForBack = new Bundle();
                bundleForBack.putParcelable("USER", user);
                intent.putExtras(bundleForBack);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
