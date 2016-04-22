package cs3714.rugburn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    TextView exercise,sets,reps,weight,quote;
    ImageView picture;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wod);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getParcelable("USER");
        }

        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(this);

        //TODO: make workout of the day and set these TextViews and the ImageView
        //TODO: also need motivational quotes
        exercise = (TextView)findViewById(R.id.wod);
        sets = (TextView)findViewById(R.id.setNum);
        reps = (TextView)findViewById(R.id.repNum);
        weight = (TextView)findViewById(R.id.weightNum);
        quote = (TextView)findViewById(R.id.motivate);

        picture = (ImageView)findViewById(R.id.wodPic);
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
