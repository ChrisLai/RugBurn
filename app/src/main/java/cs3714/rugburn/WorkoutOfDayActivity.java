package cs3714.rugburn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Date;

import cs3714.rugburn.CustomObjects.Exercise;
import cs3714.rugburn.CustomObjects.User;
import cs3714.rugburn.CustomObjects.Workout;

/**
 * Created by Jared on 4/22/2016.
 */
public class WorkoutOfDayActivity extends AppCompatActivity implements View.OnClickListener{

    User user;

    Button start;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wod);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getParcelable("USER");
        }

        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == start.getId()) {
            i = new Intent(this, Exercise.class);
            if (user.getCurrentWorkout() != null) {
                user.getCurrentWorkout().setFinished(true);
            }
            Date date = new Date();
            Workout workout = new Workout(date);
            user.addWorkout(workout);
            Bundle b = new Bundle();
            b.putParcelable("USER", user);
            i.putExtras(b);
            startActivity(i);
        }
    }
}
