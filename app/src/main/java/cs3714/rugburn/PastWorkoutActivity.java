package cs3714.rugburn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;

import cs3714.rugburn.CustomObjects.User;
import cs3714.rugburn.CustomObjects.Workout;
import cs3714.rugburn.CustomObjects.WorkoutListAdapter;

/**
 * Created by Jared on 4/22/2016.
 */
public class PastWorkoutActivity extends AppCompatActivity implements View.OnClickListener{

    User user;
    Workout workout;
    WorkoutListAdapter mAdapter;

    Button finish;
    TextView quote, date_text,motivational;
    ListView list;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_workout);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getParcelable("USER");
        }
        motivational = (TextView)findViewById(R.id.motivational);
        workout = user.getCurrentWorkout();
        if (workout != null) {
            if (!workout.getFinished()) {
                workout = user.getLastWorkout();
                if (workout != null) {
                    DisplayPast();
                } else {
                    NoWorkouts();
                }
            } else {
                DisplayPast();
            }
        } else {
            NoWorkouts();
        }

    }

    public void DisplayPast() {
        motivational.setVisibility(View.VISIBLE);
        date_text = (TextView) findViewById(R.id.date);
        date_text.setText(workout.getDate().toString().substring(0, 10));

        list = (ListView) findViewById(R.id.listPrev);
        mAdapter = new WorkoutListAdapter(this, workout.getExercises());
        list.setAdapter(mAdapter);

        //TODO: get motivational quotes
        quote = (TextView)findViewById(R.id.motivational);
    }

    public void NoWorkouts() {
        date_text = (TextView) findViewById(R.id.date);
        date_text.setText("No past workouts");
        motivational.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == finish.getId()) {
            i = new Intent(this, MainActivity.class);
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
                Intent intent = new Intent(this, MainActivity.class);
                Bundle bundleForBack = new Bundle();
                bundleForBack.putParcelable("USER", user);
                intent.putExtras(bundleForBack);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
