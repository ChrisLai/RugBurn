package cs3714.rugburn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Date;

import cs3714.rugburn.CustomObjects.User;
import cs3714.rugburn.CustomObjects.Workout;

/**
 * Created by Jared on 4/22/2016.
 */
public class CurrentWorkoutActivity extends AppCompatActivity implements View.OnClickListener{

    User user;
    Workout workout;

    Button finish;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_workout);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getParcelable("USER");
        }

        if (user.getCurrentWorkout() != null &&
                !user.getCurrentWorkout().getFinished()) {
            //don't have to make a new workout object because the current workout was never finished
        } else {
            //i think this makes a date starting today
            Date date = new Date();
            workout = new Workout(date);
            user.addWorkout(workout);
        }

        finish = (Button) findViewById(R.id.finish);
        finish.setOnClickListener(this);
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
        }
    }
}
