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
public class CurrentWorkoutActivity extends AppCompatActivity implements View.OnClickListener{

    User user;
    Workout workout;
    WorkoutListAdapter mAdapter;

    Button finish,add;
    ListView list;
    TextView date_text;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_workout);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        Date date = new Date();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getParcelable("USER");
        }

        if (user.getCurrentWorkout() != null &&
                !user.getCurrentWorkout().getFinished()) {
            //don't have to make a new workout object because the current workout was never finished
            workout = user.getCurrentWorkout();
        } else {
            workout = new Workout(date);
            user.addWorkout(workout);
        }

        finish = (Button) findViewById(R.id.finish);
        finish.setOnClickListener(this);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);

        date_text = (TextView) findViewById(R.id.date);
        date_text.setText(date.toString().substring(0, 10));

        if (workout.getExercises().size() == 0) {
            add.setText("Start Workout");
        } else {
            add.setText("Add Exercise");
            System.out.println("WORKOUT IS: " + workout.getExercises().get(0).getName());
        }



        list = (ListView) findViewById(R.id.list);
        mAdapter = new WorkoutListAdapter(this, user.getCurrentWorkout().getExercises());
        list.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == finish.getId()) {
            workout.setFinished(true);
            i = new Intent(this, MainActivity.class);
            Bundle b = new Bundle();
            b.putParcelable("USER", user);
            i.putExtras(b);
            startActivity(i);
        } else if (v.getId() == add.getId()) {

            //TODO won't go straight to exercise activity -- needs pop up dialog w/ qr option

            i = new Intent(this, ExerciseActivity.class);
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
