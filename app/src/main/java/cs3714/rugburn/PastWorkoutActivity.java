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

/**
 * Created by Jared on 4/22/2016.
 */
public class PastWorkoutActivity extends AppCompatActivity implements View.OnClickListener{

    User user;
    Workout workout;

    Button finish;
    TextView quote, date_text;
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

        workout = user.getLastWorkout();
        if (workout != null) {
            date_text = (TextView) findViewById(R.id.date);
            date_text.setText(workout.getDate().toString().substring(0, 10));

            //TODO: get motivational quotes
            quote = (TextView)findViewById(R.id.motivational);
            list = (ListView)findViewById(R.id.listPrev);
        } else {
            //NO PAST WORKOUT
        }

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
