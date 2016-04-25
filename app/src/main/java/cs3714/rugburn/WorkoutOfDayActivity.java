package cs3714.rugburn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

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

    ArrayList<String> motivationalQuotes;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wod);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getParcelable("USER");
        }

        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(this);



        exercise = (TextView)findViewById(R.id.wod);
        sets = (TextView)findViewById(R.id.setNum);
        reps = (TextView)findViewById(R.id.repNum);
        weight = (TextView)findViewById(R.id.weightNum);
        quote = (TextView)findViewById(R.id.motivate);

        motivationalQuotes = new ArrayList<String>(){{
            add("You Can Do It!");
            add("Never Give up!");
            add("Get Big!");
            add("No Pain, No Gain!");
            add("Push Yourself!");

        }};
        quote.setText(getRandomQuote());

        picture = (ImageView)findViewById(R.id.wodPic);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == start.getId()) {
            i = new Intent(this, ExerciseActivity.class);
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

    public String getRandomQuote() {
        Random r = new Random();
        return motivationalQuotes.get(r.nextInt(5));
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
